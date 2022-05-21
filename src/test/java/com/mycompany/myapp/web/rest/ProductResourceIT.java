package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductCategory;
import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.Rating;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.service.criteria.ProductCriteria;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.service.mapper.ProductMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Rating DEFAULT_GRADE = Rating.POOR;
    private static final Rating UPDATED_GRADE = Rating.FAIR;

    private static final Category DEFAULT_CATEGORY = Category.PIZA;
    private static final Category UPDATED_CATEGORY = Category.SANDWICH;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .barcode(DEFAULT_BARCODE)
            .grade(DEFAULT_GRADE)
            .category(DEFAULT_CATEGORY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .barcode(UPDATED_BARCODE)
            .grade(UPDATED_GRADE)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProduct.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testProduct.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testProduct.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProduct.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name equals to DEFAULT_NAME
        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name not equals to DEFAULT_NAME
        defaultProductShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productList where name not equals to UPDATED_NAME
        defaultProductShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name is not null
        defaultProductShouldBeFound("name.specified=true");

        // Get all the productList where name is null
        defaultProductShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name contains DEFAULT_NAME
        defaultProductShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productList where name contains UPDATED_NAME
        defaultProductShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name does not contain DEFAULT_NAME
        defaultProductShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productList where name does not contain UPDATED_NAME
        defaultProductShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price equals to DEFAULT_PRICE
        defaultProductShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price not equals to DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productList where price not equals to UPDATED_PRICE
        defaultProductShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is not null
        defaultProductShouldBeFound("price.specified=true");

        // Get all the productList where price is null
        defaultProductShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than or equal to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is less than or equal to SMALLER_PRICE
        defaultProductShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productList where price is less than UPDATED_PRICE
        defaultProductShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than SMALLER_PRICE
        defaultProductShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode equals to DEFAULT_BARCODE
        defaultProductShouldBeFound("barcode.equals=" + DEFAULT_BARCODE);

        // Get all the productList where barcode equals to UPDATED_BARCODE
        defaultProductShouldNotBeFound("barcode.equals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode not equals to DEFAULT_BARCODE
        defaultProductShouldNotBeFound("barcode.notEquals=" + DEFAULT_BARCODE);

        // Get all the productList where barcode not equals to UPDATED_BARCODE
        defaultProductShouldBeFound("barcode.notEquals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode in DEFAULT_BARCODE or UPDATED_BARCODE
        defaultProductShouldBeFound("barcode.in=" + DEFAULT_BARCODE + "," + UPDATED_BARCODE);

        // Get all the productList where barcode equals to UPDATED_BARCODE
        defaultProductShouldNotBeFound("barcode.in=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode is not null
        defaultProductShouldBeFound("barcode.specified=true");

        // Get all the productList where barcode is null
        defaultProductShouldNotBeFound("barcode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode contains DEFAULT_BARCODE
        defaultProductShouldBeFound("barcode.contains=" + DEFAULT_BARCODE);

        // Get all the productList where barcode contains UPDATED_BARCODE
        defaultProductShouldNotBeFound("barcode.contains=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    void getAllProductsByBarcodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where barcode does not contain DEFAULT_BARCODE
        defaultProductShouldNotBeFound("barcode.doesNotContain=" + DEFAULT_BARCODE);

        // Get all the productList where barcode does not contain UPDATED_BARCODE
        defaultProductShouldBeFound("barcode.doesNotContain=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    void getAllProductsByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where grade equals to DEFAULT_GRADE
        defaultProductShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the productList where grade equals to UPDATED_GRADE
        defaultProductShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllProductsByGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where grade not equals to DEFAULT_GRADE
        defaultProductShouldNotBeFound("grade.notEquals=" + DEFAULT_GRADE);

        // Get all the productList where grade not equals to UPDATED_GRADE
        defaultProductShouldBeFound("grade.notEquals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllProductsByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultProductShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the productList where grade equals to UPDATED_GRADE
        defaultProductShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllProductsByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where grade is not null
        defaultProductShouldBeFound("grade.specified=true");

        // Get all the productList where grade is null
        defaultProductShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category equals to DEFAULT_CATEGORY
        defaultProductShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the productList where category equals to UPDATED_CATEGORY
        defaultProductShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllProductsByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category not equals to DEFAULT_CATEGORY
        defaultProductShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the productList where category not equals to UPDATED_CATEGORY
        defaultProductShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllProductsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultProductShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the productList where category equals to UPDATED_CATEGORY
        defaultProductShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllProductsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category is not null
        defaultProductShouldBeFound("category.specified=true");

        // Get all the productList where category is null
        defaultProductShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the productList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the productList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the productList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl is not null
        defaultProductShouldBeFound("imageUrl.specified=true");

        // Get all the productList where imageUrl is null
        defaultProductShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl contains DEFAULT_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the productList where imageUrl contains UPDATED_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the productList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the productList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate is not null
        defaultProductShouldBeFound("expiryDate.specified=true");

        // Get all the productList where expiryDate is null
        defaultProductShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultProductShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the productList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultProductShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is not null
        defaultProductShouldBeFound("createdDate.specified=true");

        // Get all the productList where createdDate is null
        defaultProductShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultProductShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultProductShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is less than DEFAULT_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate is less than UPDATED_CREATED_DATE
        defaultProductShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate is greater than SMALLER_CREATED_DATE
        defaultProductShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the productList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate is not null
        defaultProductShouldBeFound("modifiedDate.specified=true");

        // Get all the productList where modifiedDate is null
        defaultProductShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate is greater than or equal to DEFAULT_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.greaterThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate is greater than or equal to UPDATED_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.greaterThanOrEqual=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate is less than or equal to DEFAULT_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.lessThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate is less than or equal to SMALLER_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.lessThanOrEqual=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate is less than DEFAULT_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate is less than UPDATED_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedDate is greater than DEFAULT_MODIFIED_DATE
        defaultProductShouldNotBeFound("modifiedDate.greaterThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the productList where modifiedDate is greater than SMALLER_MODIFIED_DATE
        defaultProductShouldBeFound("modifiedDate.greaterThan=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy is not null
        defaultProductShouldBeFound("createdBy.specified=true");

        // Get all the productList where createdBy is null
        defaultProductShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy contains DEFAULT_CREATED_BY
        defaultProductShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy contains UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultProductShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the productList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProductShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy not equals to DEFAULT_MODIFIED_BY
        defaultProductShouldNotBeFound("modifiedBy.notEquals=" + DEFAULT_MODIFIED_BY);

        // Get all the productList where modifiedBy not equals to UPDATED_MODIFIED_BY
        defaultProductShouldBeFound("modifiedBy.notEquals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultProductShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the productList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProductShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy is not null
        defaultProductShouldBeFound("modifiedBy.specified=true");

        // Get all the productList where modifiedBy is null
        defaultProductShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy contains DEFAULT_MODIFIED_BY
        defaultProductShouldBeFound("modifiedBy.contains=" + DEFAULT_MODIFIED_BY);

        // Get all the productList where modifiedBy contains UPDATED_MODIFIED_BY
        defaultProductShouldNotBeFound("modifiedBy.contains=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where modifiedBy does not contain DEFAULT_MODIFIED_BY
        defaultProductShouldNotBeFound("modifiedBy.doesNotContain=" + DEFAULT_MODIFIED_BY);

        // Get all the productList where modifiedBy does not contain UPDATED_MODIFIED_BY
        defaultProductShouldBeFound("modifiedBy.doesNotContain=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByEveryProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductCategory everyProductCategory;
        if (TestUtil.findAll(em, ProductCategory.class).isEmpty()) {
            everyProductCategory = ProductCategoryResourceIT.createEntity(em);
            em.persist(everyProductCategory);
            em.flush();
        } else {
            everyProductCategory = TestUtil.findAll(em, ProductCategory.class).get(0);
        }
        em.persist(everyProductCategory);
        em.flush();
        product.setEveryProductCategory(everyProductCategory);
        productRepository.saveAndFlush(product);
        Long everyProductCategoryId = everyProductCategory.getId();

        // Get all the productList where everyProductCategory equals to everyProductCategoryId
        defaultProductShouldBeFound("everyProductCategoryId.equals=" + everyProductCategoryId);

        // Get all the productList where everyProductCategory equals to (everyProductCategoryId + 1)
        defaultProductShouldNotBeFound("everyProductCategoryId.equals=" + (everyProductCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductItemIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        UserOrder productItem;
        if (TestUtil.findAll(em, UserOrder.class).isEmpty()) {
            productItem = UserOrderResourceIT.createEntity(em);
            em.persist(productItem);
            em.flush();
        } else {
            productItem = TestUtil.findAll(em, UserOrder.class).get(0);
        }
        em.persist(productItem);
        em.flush();
        product.setProductItem(productItem);
        productRepository.saveAndFlush(product);
        Long productItemId = productItem.getId();

        // Get all the productList where productItem equals to productItemId
        defaultProductShouldBeFound("productItemId.equals=" + productItemId);

        // Get all the productList where productItem equals to (productItemId + 1)
        defaultProductShouldNotBeFound("productItemId.equals=" + (productItemId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductCategory product;
        if (TestUtil.findAll(em, ProductCategory.class).isEmpty()) {
            product = ProductCategoryResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, ProductCategory.class).get(0);
        }
        em.persist(product);
        em.flush();
        product.setProduct(product);
        productRepository.saveAndFlush(product);
        Long productId = product.getId();

        // Get all the productList where product equals to productId
        defaultProductShouldBeFound("productId.equals=" + productId);

        // Get all the productList where product equals to (productId + 1)
        defaultProductShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductItemsListIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        CanteenUser productItemsList;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            productItemsList = CanteenUserResourceIT.createEntity(em);
            em.persist(productItemsList);
            em.flush();
        } else {
            productItemsList = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(productItemsList);
        em.flush();
        product.setProductItemsList(productItemsList);
        productRepository.saveAndFlush(product);
        Long productItemsListId = productItemsList.getId();

        // Get all the productList where productItemsList equals to productItemsListId
        defaultProductShouldBeFound("productItemsListId.equals=" + productItemsListId);

        // Get all the productList where productItemsList equals to (productItemsListId + 1)
        defaultProductShouldNotBeFound("productItemsListId.equals=" + (productItemsListId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Menu products;
        if (TestUtil.findAll(em, Menu.class).isEmpty()) {
            products = MenuResourceIT.createEntity(em);
            em.persist(products);
            em.flush();
        } else {
            products = TestUtil.findAll(em, Menu.class).get(0);
        }
        em.persist(products);
        em.flush();
        product.setProducts(products);
        productRepository.saveAndFlush(product);
        Long productsId = products.getId();

        // Get all the productList where products equals to productsId
        defaultProductShouldBeFound("productsId.equals=" + productsId);

        // Get all the productList where products equals to (productsId + 1)
        defaultProductShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .barcode(UPDATED_BARCODE)
            .grade(UPDATED_GRADE)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProduct.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testProduct.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct.category(UPDATED_CATEGORY).imageUrl(UPDATED_IMAGE_URL).modifiedBy(UPDATED_MODIFIED_BY);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProduct.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testProduct.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .barcode(UPDATED_BARCODE)
            .grade(UPDATED_GRADE)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProduct.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testProduct.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
