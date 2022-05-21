package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.domain.enumeration.ROLE;
import com.mycompany.myapp.repository.SchoolRepository;
import com.mycompany.myapp.service.criteria.SchoolCriteria;
import com.mycompany.myapp.service.dto.SchoolDTO;
import com.mycompany.myapp.service.mapper.SchoolMapper;
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
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchoolResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_KK_USE_ID = "AAAAAAAAAA";
    private static final String UPDATED_KK_USE_ID = "BBBBBBBBBB";

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

    private static final ROLE DEFAULT_ROLE = ROLE.ROLE_SCHOOL;
    private static final ROLE UPDATED_ROLE = ROLE.ROLE_STUDENT;

    private static final String ENTITY_API_URL = "/api/schools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolMockMvc;

    private School school;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .kkUseId(DEFAULT_KK_USE_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .role(DEFAULT_ROLE);
        return school;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .kkUseId(UPDATED_KK_USE_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .role(UPDATED_ROLE);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();
        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);
        restSchoolMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSchool.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSchool.getKkUseId()).isEqualTo(DEFAULT_KK_USE_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSchool.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testSchool.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSchool.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSchool.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createSchoolWithExistingId() throws Exception {
        // Create the School with an existing ID
        school.setId(1L);
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].kkUseId").value(hasItem(DEFAULT_KK_USE_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL_ID, school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.kkUseId").value(DEFAULT_KK_USE_ID))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    void getSchoolsByIdFiltering() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        Long id = school.getId();

        defaultSchoolShouldBeFound("id.equals=" + id);
        defaultSchoolShouldNotBeFound("id.notEquals=" + id);

        defaultSchoolShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchoolShouldNotBeFound("id.greaterThan=" + id);

        defaultSchoolShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchoolShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSchoolsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name equals to DEFAULT_NAME
        defaultSchoolShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the schoolList where name equals to UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchoolsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name not equals to DEFAULT_NAME
        defaultSchoolShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the schoolList where name not equals to UPDATED_NAME
        defaultSchoolShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchoolsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSchoolShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the schoolList where name equals to UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchoolsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name is not null
        defaultSchoolShouldBeFound("name.specified=true");

        // Get all the schoolList where name is null
        defaultSchoolShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByNameContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name contains DEFAULT_NAME
        defaultSchoolShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the schoolList where name contains UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchoolsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name does not contain DEFAULT_NAME
        defaultSchoolShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the schoolList where name does not contain UPDATED_NAME
        defaultSchoolShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email equals to DEFAULT_EMAIL
        defaultSchoolShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the schoolList where email equals to UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email not equals to DEFAULT_EMAIL
        defaultSchoolShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the schoolList where email not equals to UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the schoolList where email equals to UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email is not null
        defaultSchoolShouldBeFound("email.specified=true");

        // Get all the schoolList where email is null
        defaultSchoolShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email contains DEFAULT_EMAIL
        defaultSchoolShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the schoolList where email contains UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSchoolsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email does not contain DEFAULT_EMAIL
        defaultSchoolShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the schoolList where email does not contain UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultSchoolShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the schoolList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSchoolShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultSchoolShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the schoolList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultSchoolShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultSchoolShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the schoolList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSchoolShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber is not null
        defaultSchoolShouldBeFound("phoneNumber.specified=true");

        // Get all the schoolList where phoneNumber is null
        defaultSchoolShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultSchoolShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the schoolList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultSchoolShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSchoolsByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultSchoolShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the schoolList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultSchoolShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address equals to DEFAULT_ADDRESS
        defaultSchoolShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the schoolList where address equals to UPDATED_ADDRESS
        defaultSchoolShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address not equals to DEFAULT_ADDRESS
        defaultSchoolShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the schoolList where address not equals to UPDATED_ADDRESS
        defaultSchoolShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultSchoolShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the schoolList where address equals to UPDATED_ADDRESS
        defaultSchoolShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address is not null
        defaultSchoolShouldBeFound("address.specified=true");

        // Get all the schoolList where address is null
        defaultSchoolShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address contains DEFAULT_ADDRESS
        defaultSchoolShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the schoolList where address contains UPDATED_ADDRESS
        defaultSchoolShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSchoolsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where address does not contain DEFAULT_ADDRESS
        defaultSchoolShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the schoolList where address does not contain UPDATED_ADDRESS
        defaultSchoolShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId equals to DEFAULT_KK_USE_ID
        defaultSchoolShouldBeFound("kkUseId.equals=" + DEFAULT_KK_USE_ID);

        // Get all the schoolList where kkUseId equals to UPDATED_KK_USE_ID
        defaultSchoolShouldNotBeFound("kkUseId.equals=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId not equals to DEFAULT_KK_USE_ID
        defaultSchoolShouldNotBeFound("kkUseId.notEquals=" + DEFAULT_KK_USE_ID);

        // Get all the schoolList where kkUseId not equals to UPDATED_KK_USE_ID
        defaultSchoolShouldBeFound("kkUseId.notEquals=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId in DEFAULT_KK_USE_ID or UPDATED_KK_USE_ID
        defaultSchoolShouldBeFound("kkUseId.in=" + DEFAULT_KK_USE_ID + "," + UPDATED_KK_USE_ID);

        // Get all the schoolList where kkUseId equals to UPDATED_KK_USE_ID
        defaultSchoolShouldNotBeFound("kkUseId.in=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId is not null
        defaultSchoolShouldBeFound("kkUseId.specified=true");

        // Get all the schoolList where kkUseId is null
        defaultSchoolShouldNotBeFound("kkUseId.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId contains DEFAULT_KK_USE_ID
        defaultSchoolShouldBeFound("kkUseId.contains=" + DEFAULT_KK_USE_ID);

        // Get all the schoolList where kkUseId contains UPDATED_KK_USE_ID
        defaultSchoolShouldNotBeFound("kkUseId.contains=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllSchoolsByKkUseIdNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where kkUseId does not contain DEFAULT_KK_USE_ID
        defaultSchoolShouldNotBeFound("kkUseId.doesNotContain=" + DEFAULT_KK_USE_ID);

        // Get all the schoolList where kkUseId does not contain UPDATED_KK_USE_ID
        defaultSchoolShouldBeFound("kkUseId.doesNotContain=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate equals to UPDATED_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate not equals to UPDATED_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the schoolList where createdDate equals to UPDATED_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate is not null
        defaultSchoolShouldBeFound("createdDate.specified=true");

        // Get all the schoolList where createdDate is null
        defaultSchoolShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate is less than DEFAULT_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate is less than UPDATED_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultSchoolShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the schoolList where createdDate is greater than SMALLER_CREATED_DATE
        defaultSchoolShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate is not null
        defaultSchoolShouldBeFound("modifiedDate.specified=true");

        // Get all the schoolList where modifiedDate is null
        defaultSchoolShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate is greater than or equal to DEFAULT_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.greaterThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate is greater than or equal to UPDATED_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.greaterThanOrEqual=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate is less than or equal to DEFAULT_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.lessThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate is less than or equal to SMALLER_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.lessThanOrEqual=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate is less than DEFAULT_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate is less than UPDATED_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedDate is greater than DEFAULT_MODIFIED_DATE
        defaultSchoolShouldNotBeFound("modifiedDate.greaterThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the schoolList where modifiedDate is greater than SMALLER_MODIFIED_DATE
        defaultSchoolShouldBeFound("modifiedDate.greaterThan=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy equals to DEFAULT_CREATED_BY
        defaultSchoolShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the schoolList where createdBy equals to UPDATED_CREATED_BY
        defaultSchoolShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy not equals to DEFAULT_CREATED_BY
        defaultSchoolShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the schoolList where createdBy not equals to UPDATED_CREATED_BY
        defaultSchoolShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSchoolShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the schoolList where createdBy equals to UPDATED_CREATED_BY
        defaultSchoolShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy is not null
        defaultSchoolShouldBeFound("createdBy.specified=true");

        // Get all the schoolList where createdBy is null
        defaultSchoolShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy contains DEFAULT_CREATED_BY
        defaultSchoolShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the schoolList where createdBy contains UPDATED_CREATED_BY
        defaultSchoolShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSchoolShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the schoolList where createdBy does not contain UPDATED_CREATED_BY
        defaultSchoolShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSchoolShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the schoolList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSchoolShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy not equals to DEFAULT_MODIFIED_BY
        defaultSchoolShouldNotBeFound("modifiedBy.notEquals=" + DEFAULT_MODIFIED_BY);

        // Get all the schoolList where modifiedBy not equals to UPDATED_MODIFIED_BY
        defaultSchoolShouldBeFound("modifiedBy.notEquals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSchoolShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the schoolList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSchoolShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy is not null
        defaultSchoolShouldBeFound("modifiedBy.specified=true");

        // Get all the schoolList where modifiedBy is null
        defaultSchoolShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy contains DEFAULT_MODIFIED_BY
        defaultSchoolShouldBeFound("modifiedBy.contains=" + DEFAULT_MODIFIED_BY);

        // Get all the schoolList where modifiedBy contains UPDATED_MODIFIED_BY
        defaultSchoolShouldNotBeFound("modifiedBy.contains=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where modifiedBy does not contain DEFAULT_MODIFIED_BY
        defaultSchoolShouldNotBeFound("modifiedBy.doesNotContain=" + DEFAULT_MODIFIED_BY);

        // Get all the schoolList where modifiedBy does not contain UPDATED_MODIFIED_BY
        defaultSchoolShouldBeFound("modifiedBy.doesNotContain=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchoolsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where role equals to DEFAULT_ROLE
        defaultSchoolShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the schoolList where role equals to UPDATED_ROLE
        defaultSchoolShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllSchoolsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where role not equals to DEFAULT_ROLE
        defaultSchoolShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the schoolList where role not equals to UPDATED_ROLE
        defaultSchoolShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllSchoolsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultSchoolShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the schoolList where role equals to UPDATED_ROLE
        defaultSchoolShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllSchoolsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where role is not null
        defaultSchoolShouldBeFound("role.specified=true");

        // Get all the schoolList where role is null
        defaultSchoolShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllSchoolsByEverySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        CanteenUser everySchool;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            everySchool = CanteenUserResourceIT.createEntity(em);
            em.persist(everySchool);
            em.flush();
        } else {
            everySchool = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(everySchool);
        em.flush();
        school.setEverySchool(everySchool);
        schoolRepository.saveAndFlush(school);
        Long everySchoolId = everySchool.getId();

        // Get all the schoolList where everySchool equals to everySchoolId
        defaultSchoolShouldBeFound("everySchoolId.equals=" + everySchoolId);

        // Get all the schoolList where everySchool equals to (everySchoolId + 1)
        defaultSchoolShouldNotBeFound("everySchoolId.equals=" + (everySchoolId + 1));
    }

    @Test
    @Transactional
    void getAllSchoolsByStudentSchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        CanteenUser studentSchool;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            studentSchool = CanteenUserResourceIT.createEntity(em);
            em.persist(studentSchool);
            em.flush();
        } else {
            studentSchool = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(studentSchool);
        em.flush();
        school.addStudentSchool(studentSchool);
        schoolRepository.saveAndFlush(school);
        Long studentSchoolId = studentSchool.getId();

        // Get all the schoolList where studentSchool equals to studentSchoolId
        defaultSchoolShouldBeFound("studentSchoolId.equals=" + studentSchoolId);

        // Get all the schoolList where studentSchool equals to (studentSchoolId + 1)
        defaultSchoolShouldNotBeFound("studentSchoolId.equals=" + (studentSchoolId + 1));
    }

    @Test
    @Transactional
    void getAllSchoolsByEachSchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        CanteenUser eachSchool;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            eachSchool = CanteenUserResourceIT.createEntity(em);
            em.persist(eachSchool);
            em.flush();
        } else {
            eachSchool = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(eachSchool);
        em.flush();
        school.addEachSchool(eachSchool);
        schoolRepository.saveAndFlush(school);
        Long eachSchoolId = eachSchool.getId();

        // Get all the schoolList where eachSchool equals to eachSchoolId
        defaultSchoolShouldBeFound("eachSchoolId.equals=" + eachSchoolId);

        // Get all the schoolList where eachSchool equals to (eachSchoolId + 1)
        defaultSchoolShouldNotBeFound("eachSchoolId.equals=" + (eachSchoolId + 1));
    }

    @Test
    @Transactional
    void getAllSchoolsBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        CanteenUser school;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            school = CanteenUserResourceIT.createEntity(em);
            em.persist(school);
            em.flush();
        } else {
            school = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(school);
        em.flush();
        school.setSchool(school);
        schoolRepository.saveAndFlush(school);
        Long schoolId = school.getId();

        // Get all the schoolList where school equals to schoolId
        defaultSchoolShouldBeFound("schoolId.equals=" + schoolId);

        // Get all the schoolList where school equals to (schoolId + 1)
        defaultSchoolShouldNotBeFound("schoolId.equals=" + (schoolId + 1));
    }

    @Test
    @Transactional
    void getAllSchoolsBySchoolsIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        CanteenUser schools;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            schools = CanteenUserResourceIT.createEntity(em);
            em.persist(schools);
            em.flush();
        } else {
            schools = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(schools);
        em.flush();
        school.setSchools(schools);
        schoolRepository.saveAndFlush(school);
        Long schoolsId = schools.getId();

        // Get all the schoolList where schools equals to schoolsId
        defaultSchoolShouldBeFound("schoolsId.equals=" + schoolsId);

        // Get all the schoolList where schools equals to (schoolsId + 1)
        defaultSchoolShouldNotBeFound("schoolsId.equals=" + (schoolsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchoolShouldBeFound(String filter) throws Exception {
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].kkUseId").value(hasItem(DEFAULT_KK_USE_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));

        // Check, that the count call also returns 1
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchoolShouldNotBeFound(String filter) throws Exception {
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .kkUseId(UPDATED_KK_USE_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .role(UPDATED_ROLE);
        SchoolDTO schoolDTO = schoolMapper.toDto(updatedSchool);

        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSchool.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchool.getKkUseId()).isEqualTo(UPDATED_KK_USE_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSchool.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testSchool.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSchool.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSchool.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool.name(UPDATED_NAME).email(UPDATED_EMAIL).address(UPDATED_ADDRESS).createdDate(UPDATED_CREATED_DATE);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSchool.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchool.getKkUseId()).isEqualTo(DEFAULT_KK_USE_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSchool.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testSchool.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSchool.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSchool.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .kkUseId(UPDATED_KK_USE_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .role(UPDATED_ROLE);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSchool.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchool.getKkUseId()).isEqualTo(UPDATED_KK_USE_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSchool.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testSchool.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSchool.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSchool.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schoolDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, school.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
