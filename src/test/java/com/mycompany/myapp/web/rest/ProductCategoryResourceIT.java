package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductCategory;
import com.mycompany.myapp.repository.ProductCategoryRepository;
import com.mycompany.myapp.service.dto.ProductCategoryDTO;
import com.mycompany.myapp.service.mapper.ProductCategoryMapper;
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
 * Integration tests for the {@link ProductCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return productCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createUpdatedEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();
        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);
        restProductCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategory.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductCategory.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductCategoryWithExistingId() throws Exception {
        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(updatedProductCategory);

        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategory.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductCategoryWithPatch() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory using partial update
        ProductCategory partialUpdatedProductCategory = new ProductCategory();
        partialUpdatedProductCategory.setId(productCategory.getId());

        partialUpdatedProductCategory.name(UPDATED_NAME).createdDate(UPDATED_CREATED_DATE).createdBy(UPDATED_CREATED_BY);

        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategory))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategory.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductCategoryWithPatch() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory using partial update
        ProductCategory partialUpdatedProductCategory = new ProductCategory();
        partialUpdatedProductCategory.setId(productCategory.getId());

        partialUpdatedProductCategory
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategory))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategory.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, productCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
