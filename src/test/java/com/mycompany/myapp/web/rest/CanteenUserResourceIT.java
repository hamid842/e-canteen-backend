package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ActivationCode;
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.domain.NotificationHistory;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.domain.enumeration.ROLE;
import com.mycompany.myapp.repository.CanteenUserRepository;
import com.mycompany.myapp.service.criteria.CanteenUserCriteria;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.mapper.CanteenUserMapper;
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
 * Integration tests for the {@link CanteenUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CanteenUserResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final Boolean DEFAULT_PHONE_VERIFIED = false;
    private static final Boolean UPDATED_PHONE_VERIFIED = true;

    private static final Boolean DEFAULT_EMAIL_VERIFIED = false;
    private static final Boolean UPDATED_EMAIL_VERIFIED = true;

    private static final String DEFAULT_KK_USE_ID = "AAAAAAAAAA";
    private static final String UPDATED_KK_USE_ID = "BBBBBBBBBB";

    private static final ROLE DEFAULT_ROLE = ROLE.ROLE_SCHOOL;
    private static final ROLE UPDATED_ROLE = ROLE.ROLE_STUDENT;

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

    private static final String ENTITY_API_URL = "/api/canteen-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CanteenUserRepository canteenUserRepository;

    @Autowired
    private CanteenUserMapper canteenUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCanteenUserMockMvc;

    private CanteenUser canteenUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CanteenUser createEntity(EntityManager em) {
        CanteenUser canteenUser = new CanteenUser()
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .imageUrl(DEFAULT_IMAGE_URL)
            .isEnabled(DEFAULT_IS_ENABLED)
            .phoneVerified(DEFAULT_PHONE_VERIFIED)
            .emailVerified(DEFAULT_EMAIL_VERIFIED)
            .kkUseId(DEFAULT_KK_USE_ID)
            .role(DEFAULT_ROLE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return canteenUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CanteenUser createUpdatedEntity(EntityManager em) {
        CanteenUser canteenUser = new CanteenUser()
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .imageUrl(UPDATED_IMAGE_URL)
            .isEnabled(UPDATED_IS_ENABLED)
            .phoneVerified(UPDATED_PHONE_VERIFIED)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .kkUseId(UPDATED_KK_USE_ID)
            .role(UPDATED_ROLE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return canteenUser;
    }

    @BeforeEach
    public void initTest() {
        canteenUser = createEntity(em);
    }

    @Test
    @Transactional
    void createCanteenUser() throws Exception {
        int databaseSizeBeforeCreate = canteenUserRepository.findAll().size();
        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);
        restCanteenUserMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeCreate + 1);
        CanteenUser testCanteenUser = canteenUserList.get(canteenUserList.size() - 1);
        assertThat(testCanteenUser.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testCanteenUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCanteenUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCanteenUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCanteenUser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCanteenUser.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testCanteenUser.getPhoneVerified()).isEqualTo(DEFAULT_PHONE_VERIFIED);
        assertThat(testCanteenUser.getEmailVerified()).isEqualTo(DEFAULT_EMAIL_VERIFIED);
        assertThat(testCanteenUser.getKkUseId()).isEqualTo(DEFAULT_KK_USE_ID);
        assertThat(testCanteenUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCanteenUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCanteenUser.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testCanteenUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCanteenUser.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCanteenUserWithExistingId() throws Exception {
        // Create the CanteenUser with an existing ID
        canteenUser.setId(1L);
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        int databaseSizeBeforeCreate = canteenUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCanteenUserMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCanteenUsers() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(canteenUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneVerified").value(hasItem(DEFAULT_PHONE_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].kkUseId").value(hasItem(DEFAULT_KK_USE_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCanteenUser() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get the canteenUser
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL_ID, canteenUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(canteenUser.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.phoneVerified").value(DEFAULT_PHONE_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.emailVerified").value(DEFAULT_EMAIL_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.kkUseId").value(DEFAULT_KK_USE_ID))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getCanteenUsersByIdFiltering() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        Long id = canteenUser.getId();

        defaultCanteenUserShouldBeFound("id.equals=" + id);
        defaultCanteenUserShouldNotBeFound("id.notEquals=" + id);

        defaultCanteenUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCanteenUserShouldNotBeFound("id.greaterThan=" + id);

        defaultCanteenUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCanteenUserShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName equals to DEFAULT_FULL_NAME
        defaultCanteenUserShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the canteenUserList where fullName equals to UPDATED_FULL_NAME
        defaultCanteenUserShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName not equals to DEFAULT_FULL_NAME
        defaultCanteenUserShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the canteenUserList where fullName not equals to UPDATED_FULL_NAME
        defaultCanteenUserShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultCanteenUserShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the canteenUserList where fullName equals to UPDATED_FULL_NAME
        defaultCanteenUserShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName is not null
        defaultCanteenUserShouldBeFound("fullName.specified=true");

        // Get all the canteenUserList where fullName is null
        defaultCanteenUserShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName contains DEFAULT_FULL_NAME
        defaultCanteenUserShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the canteenUserList where fullName contains UPDATED_FULL_NAME
        defaultCanteenUserShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where fullName does not contain DEFAULT_FULL_NAME
        defaultCanteenUserShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the canteenUserList where fullName does not contain UPDATED_FULL_NAME
        defaultCanteenUserShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email equals to DEFAULT_EMAIL
        defaultCanteenUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the canteenUserList where email equals to UPDATED_EMAIL
        defaultCanteenUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email not equals to DEFAULT_EMAIL
        defaultCanteenUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the canteenUserList where email not equals to UPDATED_EMAIL
        defaultCanteenUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCanteenUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the canteenUserList where email equals to UPDATED_EMAIL
        defaultCanteenUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email is not null
        defaultCanteenUserShouldBeFound("email.specified=true");

        // Get all the canteenUserList where email is null
        defaultCanteenUserShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email contains DEFAULT_EMAIL
        defaultCanteenUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the canteenUserList where email contains UPDATED_EMAIL
        defaultCanteenUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where email does not contain DEFAULT_EMAIL
        defaultCanteenUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the canteenUserList where email does not contain UPDATED_EMAIL
        defaultCanteenUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultCanteenUserShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the canteenUserList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultCanteenUserShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the canteenUserList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the canteenUserList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber is not null
        defaultCanteenUserShouldBeFound("phoneNumber.specified=true");

        // Get all the canteenUserList where phoneNumber is null
        defaultCanteenUserShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultCanteenUserShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the canteenUserList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultCanteenUserShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the canteenUserList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultCanteenUserShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address equals to DEFAULT_ADDRESS
        defaultCanteenUserShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the canteenUserList where address equals to UPDATED_ADDRESS
        defaultCanteenUserShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address not equals to DEFAULT_ADDRESS
        defaultCanteenUserShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the canteenUserList where address not equals to UPDATED_ADDRESS
        defaultCanteenUserShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCanteenUserShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the canteenUserList where address equals to UPDATED_ADDRESS
        defaultCanteenUserShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address is not null
        defaultCanteenUserShouldBeFound("address.specified=true");

        // Get all the canteenUserList where address is null
        defaultCanteenUserShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address contains DEFAULT_ADDRESS
        defaultCanteenUserShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the canteenUserList where address contains UPDATED_ADDRESS
        defaultCanteenUserShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where address does not contain DEFAULT_ADDRESS
        defaultCanteenUserShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the canteenUserList where address does not contain UPDATED_ADDRESS
        defaultCanteenUserShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultCanteenUserShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the canteenUserList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCanteenUserShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultCanteenUserShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the canteenUserList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultCanteenUserShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultCanteenUserShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the canteenUserList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCanteenUserShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl is not null
        defaultCanteenUserShouldBeFound("imageUrl.specified=true");

        // Get all the canteenUserList where imageUrl is null
        defaultCanteenUserShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl contains DEFAULT_IMAGE_URL
        defaultCanteenUserShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the canteenUserList where imageUrl contains UPDATED_IMAGE_URL
        defaultCanteenUserShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultCanteenUserShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the canteenUserList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultCanteenUserShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByIsEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where isEnabled equals to DEFAULT_IS_ENABLED
        defaultCanteenUserShouldBeFound("isEnabled.equals=" + DEFAULT_IS_ENABLED);

        // Get all the canteenUserList where isEnabled equals to UPDATED_IS_ENABLED
        defaultCanteenUserShouldNotBeFound("isEnabled.equals=" + UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByIsEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where isEnabled not equals to DEFAULT_IS_ENABLED
        defaultCanteenUserShouldNotBeFound("isEnabled.notEquals=" + DEFAULT_IS_ENABLED);

        // Get all the canteenUserList where isEnabled not equals to UPDATED_IS_ENABLED
        defaultCanteenUserShouldBeFound("isEnabled.notEquals=" + UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByIsEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where isEnabled in DEFAULT_IS_ENABLED or UPDATED_IS_ENABLED
        defaultCanteenUserShouldBeFound("isEnabled.in=" + DEFAULT_IS_ENABLED + "," + UPDATED_IS_ENABLED);

        // Get all the canteenUserList where isEnabled equals to UPDATED_IS_ENABLED
        defaultCanteenUserShouldNotBeFound("isEnabled.in=" + UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByIsEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where isEnabled is not null
        defaultCanteenUserShouldBeFound("isEnabled.specified=true");

        // Get all the canteenUserList where isEnabled is null
        defaultCanteenUserShouldNotBeFound("isEnabled.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneVerified equals to DEFAULT_PHONE_VERIFIED
        defaultCanteenUserShouldBeFound("phoneVerified.equals=" + DEFAULT_PHONE_VERIFIED);

        // Get all the canteenUserList where phoneVerified equals to UPDATED_PHONE_VERIFIED
        defaultCanteenUserShouldNotBeFound("phoneVerified.equals=" + UPDATED_PHONE_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneVerifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneVerified not equals to DEFAULT_PHONE_VERIFIED
        defaultCanteenUserShouldNotBeFound("phoneVerified.notEquals=" + DEFAULT_PHONE_VERIFIED);

        // Get all the canteenUserList where phoneVerified not equals to UPDATED_PHONE_VERIFIED
        defaultCanteenUserShouldBeFound("phoneVerified.notEquals=" + UPDATED_PHONE_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneVerified in DEFAULT_PHONE_VERIFIED or UPDATED_PHONE_VERIFIED
        defaultCanteenUserShouldBeFound("phoneVerified.in=" + DEFAULT_PHONE_VERIFIED + "," + UPDATED_PHONE_VERIFIED);

        // Get all the canteenUserList where phoneVerified equals to UPDATED_PHONE_VERIFIED
        defaultCanteenUserShouldNotBeFound("phoneVerified.in=" + UPDATED_PHONE_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByPhoneVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where phoneVerified is not null
        defaultCanteenUserShouldBeFound("phoneVerified.specified=true");

        // Get all the canteenUserList where phoneVerified is null
        defaultCanteenUserShouldNotBeFound("phoneVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where emailVerified equals to DEFAULT_EMAIL_VERIFIED
        defaultCanteenUserShouldBeFound("emailVerified.equals=" + DEFAULT_EMAIL_VERIFIED);

        // Get all the canteenUserList where emailVerified equals to UPDATED_EMAIL_VERIFIED
        defaultCanteenUserShouldNotBeFound("emailVerified.equals=" + UPDATED_EMAIL_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailVerifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where emailVerified not equals to DEFAULT_EMAIL_VERIFIED
        defaultCanteenUserShouldNotBeFound("emailVerified.notEquals=" + DEFAULT_EMAIL_VERIFIED);

        // Get all the canteenUserList where emailVerified not equals to UPDATED_EMAIL_VERIFIED
        defaultCanteenUserShouldBeFound("emailVerified.notEquals=" + UPDATED_EMAIL_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where emailVerified in DEFAULT_EMAIL_VERIFIED or UPDATED_EMAIL_VERIFIED
        defaultCanteenUserShouldBeFound("emailVerified.in=" + DEFAULT_EMAIL_VERIFIED + "," + UPDATED_EMAIL_VERIFIED);

        // Get all the canteenUserList where emailVerified equals to UPDATED_EMAIL_VERIFIED
        defaultCanteenUserShouldNotBeFound("emailVerified.in=" + UPDATED_EMAIL_VERIFIED);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEmailVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where emailVerified is not null
        defaultCanteenUserShouldBeFound("emailVerified.specified=true");

        // Get all the canteenUserList where emailVerified is null
        defaultCanteenUserShouldNotBeFound("emailVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId equals to DEFAULT_KK_USE_ID
        defaultCanteenUserShouldBeFound("kkUseId.equals=" + DEFAULT_KK_USE_ID);

        // Get all the canteenUserList where kkUseId equals to UPDATED_KK_USE_ID
        defaultCanteenUserShouldNotBeFound("kkUseId.equals=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId not equals to DEFAULT_KK_USE_ID
        defaultCanteenUserShouldNotBeFound("kkUseId.notEquals=" + DEFAULT_KK_USE_ID);

        // Get all the canteenUserList where kkUseId not equals to UPDATED_KK_USE_ID
        defaultCanteenUserShouldBeFound("kkUseId.notEquals=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId in DEFAULT_KK_USE_ID or UPDATED_KK_USE_ID
        defaultCanteenUserShouldBeFound("kkUseId.in=" + DEFAULT_KK_USE_ID + "," + UPDATED_KK_USE_ID);

        // Get all the canteenUserList where kkUseId equals to UPDATED_KK_USE_ID
        defaultCanteenUserShouldNotBeFound("kkUseId.in=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId is not null
        defaultCanteenUserShouldBeFound("kkUseId.specified=true");

        // Get all the canteenUserList where kkUseId is null
        defaultCanteenUserShouldNotBeFound("kkUseId.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId contains DEFAULT_KK_USE_ID
        defaultCanteenUserShouldBeFound("kkUseId.contains=" + DEFAULT_KK_USE_ID);

        // Get all the canteenUserList where kkUseId contains UPDATED_KK_USE_ID
        defaultCanteenUserShouldNotBeFound("kkUseId.contains=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByKkUseIdNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where kkUseId does not contain DEFAULT_KK_USE_ID
        defaultCanteenUserShouldNotBeFound("kkUseId.doesNotContain=" + DEFAULT_KK_USE_ID);

        // Get all the canteenUserList where kkUseId does not contain UPDATED_KK_USE_ID
        defaultCanteenUserShouldBeFound("kkUseId.doesNotContain=" + UPDATED_KK_USE_ID);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where role equals to DEFAULT_ROLE
        defaultCanteenUserShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the canteenUserList where role equals to UPDATED_ROLE
        defaultCanteenUserShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where role not equals to DEFAULT_ROLE
        defaultCanteenUserShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the canteenUserList where role not equals to UPDATED_ROLE
        defaultCanteenUserShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultCanteenUserShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the canteenUserList where role equals to UPDATED_ROLE
        defaultCanteenUserShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where role is not null
        defaultCanteenUserShouldBeFound("role.specified=true");

        // Get all the canteenUserList where role is null
        defaultCanteenUserShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate equals to UPDATED_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate not equals to UPDATED_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the canteenUserList where createdDate equals to UPDATED_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate is not null
        defaultCanteenUserShouldBeFound("createdDate.specified=true");

        // Get all the canteenUserList where createdDate is null
        defaultCanteenUserShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate is less than DEFAULT_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate is less than UPDATED_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultCanteenUserShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the canteenUserList where createdDate is greater than SMALLER_CREATED_DATE
        defaultCanteenUserShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate is not null
        defaultCanteenUserShouldBeFound("modifiedDate.specified=true");

        // Get all the canteenUserList where modifiedDate is null
        defaultCanteenUserShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate is greater than or equal to DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.greaterThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate is greater than or equal to UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.greaterThanOrEqual=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate is less than or equal to DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.lessThanOrEqual=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate is less than or equal to SMALLER_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.lessThanOrEqual=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate is less than DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate is less than UPDATED_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedDate is greater than DEFAULT_MODIFIED_DATE
        defaultCanteenUserShouldNotBeFound("modifiedDate.greaterThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the canteenUserList where modifiedDate is greater than SMALLER_MODIFIED_DATE
        defaultCanteenUserShouldBeFound("modifiedDate.greaterThan=" + SMALLER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy equals to DEFAULT_CREATED_BY
        defaultCanteenUserShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the canteenUserList where createdBy equals to UPDATED_CREATED_BY
        defaultCanteenUserShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy not equals to DEFAULT_CREATED_BY
        defaultCanteenUserShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the canteenUserList where createdBy not equals to UPDATED_CREATED_BY
        defaultCanteenUserShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCanteenUserShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the canteenUserList where createdBy equals to UPDATED_CREATED_BY
        defaultCanteenUserShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy is not null
        defaultCanteenUserShouldBeFound("createdBy.specified=true");

        // Get all the canteenUserList where createdBy is null
        defaultCanteenUserShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy contains DEFAULT_CREATED_BY
        defaultCanteenUserShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the canteenUserList where createdBy contains UPDATED_CREATED_BY
        defaultCanteenUserShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where createdBy does not contain DEFAULT_CREATED_BY
        defaultCanteenUserShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the canteenUserList where createdBy does not contain UPDATED_CREATED_BY
        defaultCanteenUserShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultCanteenUserShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the canteenUserList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCanteenUserShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy not equals to DEFAULT_MODIFIED_BY
        defaultCanteenUserShouldNotBeFound("modifiedBy.notEquals=" + DEFAULT_MODIFIED_BY);

        // Get all the canteenUserList where modifiedBy not equals to UPDATED_MODIFIED_BY
        defaultCanteenUserShouldBeFound("modifiedBy.notEquals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultCanteenUserShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the canteenUserList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCanteenUserShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy is not null
        defaultCanteenUserShouldBeFound("modifiedBy.specified=true");

        // Get all the canteenUserList where modifiedBy is null
        defaultCanteenUserShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy contains DEFAULT_MODIFIED_BY
        defaultCanteenUserShouldBeFound("modifiedBy.contains=" + DEFAULT_MODIFIED_BY);

        // Get all the canteenUserList where modifiedBy contains UPDATED_MODIFIED_BY
        defaultCanteenUserShouldNotBeFound("modifiedBy.contains=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        // Get all the canteenUserList where modifiedBy does not contain DEFAULT_MODIFIED_BY
        defaultCanteenUserShouldNotBeFound("modifiedBy.doesNotContain=" + DEFAULT_MODIFIED_BY);

        // Get all the canteenUserList where modifiedBy does not contain UPDATED_MODIFIED_BY
        defaultCanteenUserShouldBeFound("modifiedBy.doesNotContain=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEveryWorkerAtSchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        Menu everyWorkerAtSchool;
        if (TestUtil.findAll(em, Menu.class).isEmpty()) {
            everyWorkerAtSchool = MenuResourceIT.createEntity(em);
            em.persist(everyWorkerAtSchool);
            em.flush();
        } else {
            everyWorkerAtSchool = TestUtil.findAll(em, Menu.class).get(0);
        }
        em.persist(everyWorkerAtSchool);
        em.flush();
        canteenUser.setEveryWorkerAtSchool(everyWorkerAtSchool);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long everyWorkerAtSchoolId = everyWorkerAtSchool.getId();

        // Get all the canteenUserList where everyWorkerAtSchool equals to everyWorkerAtSchoolId
        defaultCanteenUserShouldBeFound("everyWorkerAtSchoolId.equals=" + everyWorkerAtSchoolId);

        // Get all the canteenUserList where everyWorkerAtSchool equals to (everyWorkerAtSchoolId + 1)
        defaultCanteenUserShouldNotBeFound("everyWorkerAtSchoolId.equals=" + (everyWorkerAtSchoolId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEveryParentIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        UserAccount everyParent;
        if (TestUtil.findAll(em, UserAccount.class).isEmpty()) {
            everyParent = UserAccountResourceIT.createEntity(em);
            em.persist(everyParent);
            em.flush();
        } else {
            everyParent = TestUtil.findAll(em, UserAccount.class).get(0);
        }
        em.persist(everyParent);
        em.flush();
        canteenUser.setEveryParent(everyParent);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long everyParentId = everyParent.getId();

        // Get all the canteenUserList where everyParent equals to everyParentId
        defaultCanteenUserShouldBeFound("everyParentId.equals=" + everyParentId);

        // Get all the canteenUserList where everyParent equals to (everyParentId + 1)
        defaultCanteenUserShouldNotBeFound("everyParentId.equals=" + (everyParentId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByParentOfChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        CanteenUser parentOfChildren;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            parentOfChildren = CanteenUserResourceIT.createEntity(em);
            em.persist(parentOfChildren);
            em.flush();
        } else {
            parentOfChildren = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(parentOfChildren);
        em.flush();
        canteenUser.addParentOfChildren(parentOfChildren);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long parentOfChildrenId = parentOfChildren.getId();

        // Get all the canteenUserList where parentOfChildren equals to parentOfChildrenId
        defaultCanteenUserShouldBeFound("parentOfChildrenId.equals=" + parentOfChildrenId);

        // Get all the canteenUserList where parentOfChildren equals to (parentOfChildrenId + 1)
        defaultCanteenUserShouldNotBeFound("parentOfChildrenId.equals=" + (parentOfChildrenId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEachParentIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        School eachParent;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            eachParent = SchoolResourceIT.createEntity(em);
            em.persist(eachParent);
            em.flush();
        } else {
            eachParent = TestUtil.findAll(em, School.class).get(0);
        }
        em.persist(eachParent);
        em.flush();
        canteenUser.addEachParent(eachParent);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long eachParentId = eachParent.getId();

        // Get all the canteenUserList where eachParent equals to eachParentId
        defaultCanteenUserShouldBeFound("eachParentId.equals=" + eachParentId);

        // Get all the canteenUserList where eachParent equals to (eachParentId + 1)
        defaultCanteenUserShouldNotBeFound("eachParentId.equals=" + (eachParentId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEachStusentParentIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        CanteenUser eachStusentParent;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            eachStusentParent = CanteenUserResourceIT.createEntity(em);
            em.persist(eachStusentParent);
            em.flush();
        } else {
            eachStusentParent = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(eachStusentParent);
        em.flush();
        canteenUser.addEachStusentParent(eachStusentParent);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long eachStusentParentId = eachStusentParent.getId();

        // Get all the canteenUserList where eachStusentParent equals to eachStusentParentId
        defaultCanteenUserShouldBeFound("eachStusentParentId.equals=" + eachStusentParentId);

        // Get all the canteenUserList where eachStusentParent equals to (eachStusentParentId + 1)
        defaultCanteenUserShouldNotBeFound("eachStusentParentId.equals=" + (eachStusentParentId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEachWorkerIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        Product eachWorker;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            eachWorker = ProductResourceIT.createEntity(em);
            em.persist(eachWorker);
            em.flush();
        } else {
            eachWorker = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(eachWorker);
        em.flush();
        canteenUser.addEachWorker(eachWorker);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long eachWorkerId = eachWorker.getId();

        // Get all the canteenUserList where eachWorker equals to eachWorkerId
        defaultCanteenUserShouldBeFound("eachWorkerId.equals=" + eachWorkerId);

        // Get all the canteenUserList where eachWorker equals to (eachWorkerId + 1)
        defaultCanteenUserShouldNotBeFound("eachWorkerId.equals=" + (eachWorkerId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByParentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        ActivationCode parentCode;
        if (TestUtil.findAll(em, ActivationCode.class).isEmpty()) {
            parentCode = ActivationCodeResourceIT.createEntity(em);
            em.persist(parentCode);
            em.flush();
        } else {
            parentCode = TestUtil.findAll(em, ActivationCode.class).get(0);
        }
        em.persist(parentCode);
        em.flush();
        canteenUser.addParentCode(parentCode);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long parentCodeId = parentCode.getId();

        // Get all the canteenUserList where parentCode equals to parentCodeId
        defaultCanteenUserShouldBeFound("parentCodeId.equals=" + parentCodeId);

        // Get all the canteenUserList where parentCode equals to (parentCodeId + 1)
        defaultCanteenUserShouldNotBeFound("parentCodeId.equals=" + (parentCodeId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByWorkerCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        ActivationCode workerCode;
        if (TestUtil.findAll(em, ActivationCode.class).isEmpty()) {
            workerCode = ActivationCodeResourceIT.createEntity(em);
            em.persist(workerCode);
            em.flush();
        } else {
            workerCode = TestUtil.findAll(em, ActivationCode.class).get(0);
        }
        em.persist(workerCode);
        em.flush();
        canteenUser.addWorkerCode(workerCode);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long workerCodeId = workerCode.getId();

        // Get all the canteenUserList where workerCode equals to workerCodeId
        defaultCanteenUserShouldBeFound("workerCodeId.equals=" + workerCodeId);

        // Get all the canteenUserList where workerCode equals to (workerCodeId + 1)
        defaultCanteenUserShouldNotBeFound("workerCodeId.equals=" + (workerCodeId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByParentNotifcationsIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        NotificationHistory parentNotifcations;
        if (TestUtil.findAll(em, NotificationHistory.class).isEmpty()) {
            parentNotifcations = NotificationHistoryResourceIT.createEntity(em);
            em.persist(parentNotifcations);
            em.flush();
        } else {
            parentNotifcations = TestUtil.findAll(em, NotificationHistory.class).get(0);
        }
        em.persist(parentNotifcations);
        em.flush();
        canteenUser.addParentNotifcations(parentNotifcations);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long parentNotifcationsId = parentNotifcations.getId();

        // Get all the canteenUserList where parentNotifcations equals to parentNotifcationsId
        defaultCanteenUserShouldBeFound("parentNotifcationsId.equals=" + parentNotifcationsId);

        // Get all the canteenUserList where parentNotifcations equals to (parentNotifcationsId + 1)
        defaultCanteenUserShouldNotBeFound("parentNotifcationsId.equals=" + (parentNotifcationsId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEachStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        UserOrder eachStudent;
        if (TestUtil.findAll(em, UserOrder.class).isEmpty()) {
            eachStudent = UserOrderResourceIT.createEntity(em);
            em.persist(eachStudent);
            em.flush();
        } else {
            eachStudent = TestUtil.findAll(em, UserOrder.class).get(0);
        }
        em.persist(eachStudent);
        em.flush();
        canteenUser.addEachStudent(eachStudent);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long eachStudentId = eachStudent.getId();

        // Get all the canteenUserList where eachStudent equals to eachStudentId
        defaultCanteenUserShouldBeFound("eachStudentId.equals=" + eachStudentId);

        // Get all the canteenUserList where eachStudent equals to (eachStudentId + 1)
        defaultCanteenUserShouldNotBeFound("eachStudentId.equals=" + (eachStudentId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByParentTransactionsIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        Transaction parentTransactions;
        if (TestUtil.findAll(em, Transaction.class).isEmpty()) {
            parentTransactions = TransactionResourceIT.createEntity(em);
            em.persist(parentTransactions);
            em.flush();
        } else {
            parentTransactions = TestUtil.findAll(em, Transaction.class).get(0);
        }
        em.persist(parentTransactions);
        em.flush();
        canteenUser.addParentTransactions(parentTransactions);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long parentTransactionsId = parentTransactions.getId();

        // Get all the canteenUserList where parentTransactions equals to parentTransactionsId
        defaultCanteenUserShouldBeFound("parentTransactionsId.equals=" + parentTransactionsId);

        // Get all the canteenUserList where parentTransactions equals to (parentTransactionsId + 1)
        defaultCanteenUserShouldNotBeFound("parentTransactionsId.equals=" + (parentTransactionsId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByWorkerAtSchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        School workerAtSchool;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            workerAtSchool = SchoolResourceIT.createEntity(em);
            em.persist(workerAtSchool);
            em.flush();
        } else {
            workerAtSchool = TestUtil.findAll(em, School.class).get(0);
        }
        em.persist(workerAtSchool);
        em.flush();
        canteenUser.addWorkerAtSchool(workerAtSchool);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long workerAtSchoolId = workerAtSchool.getId();

        // Get all the canteenUserList where workerAtSchool equals to workerAtSchoolId
        defaultCanteenUserShouldBeFound("workerAtSchoolId.equals=" + workerAtSchoolId);

        // Get all the canteenUserList where workerAtSchool equals to (workerAtSchoolId + 1)
        defaultCanteenUserShouldNotBeFound("workerAtSchoolId.equals=" + (workerAtSchoolId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByEveryWorkerIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        School everyWorker;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            everyWorker = SchoolResourceIT.createEntity(em);
            em.persist(everyWorker);
            em.flush();
        } else {
            everyWorker = TestUtil.findAll(em, School.class).get(0);
        }
        em.persist(everyWorker);
        em.flush();
        canteenUser.setEveryWorker(everyWorker);
        everyWorker.setEverySchool(canteenUser);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long everyWorkerId = everyWorker.getId();

        // Get all the canteenUserList where everyWorker equals to everyWorkerId
        defaultCanteenUserShouldBeFound("everyWorkerId.equals=" + everyWorkerId);

        // Get all the canteenUserList where everyWorker equals to (everyWorkerId + 1)
        defaultCanteenUserShouldNotBeFound("everyWorkerId.equals=" + (everyWorkerId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        CanteenUser children;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            children = CanteenUserResourceIT.createEntity(em);
            em.persist(children);
            em.flush();
        } else {
            children = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(children);
        em.flush();
        canteenUser.setChildren(children);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long childrenId = children.getId();

        // Get all the canteenUserList where children equals to childrenId
        defaultCanteenUserShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the canteenUserList where children equals to (childrenId + 1)
        defaultCanteenUserShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByParentsIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        School parents;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            parents = SchoolResourceIT.createEntity(em);
            em.persist(parents);
            em.flush();
        } else {
            parents = TestUtil.findAll(em, School.class).get(0);
        }
        em.persist(parents);
        em.flush();
        canteenUser.setParents(parents);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long parentsId = parents.getId();

        // Get all the canteenUserList where parents equals to parentsId
        defaultCanteenUserShouldBeFound("parentsId.equals=" + parentsId);

        // Get all the canteenUserList where parents equals to (parentsId + 1)
        defaultCanteenUserShouldNotBeFound("parentsId.equals=" + (parentsId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByStudentsIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        School students;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            students = SchoolResourceIT.createEntity(em);
            em.persist(students);
            em.flush();
        } else {
            students = TestUtil.findAll(em, School.class).get(0);
        }
        em.persist(students);
        em.flush();
        canteenUser.setStudents(students);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long studentsId = students.getId();

        // Get all the canteenUserList where students equals to studentsId
        defaultCanteenUserShouldBeFound("studentsId.equals=" + studentsId);

        // Get all the canteenUserList where students equals to (studentsId + 1)
        defaultCanteenUserShouldNotBeFound("studentsId.equals=" + (studentsId + 1));
    }

    @Test
    @Transactional
    void getAllCanteenUsersByWorkersIsEqualToSomething() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);
        CanteenUser workers;
        if (TestUtil.findAll(em, CanteenUser.class).isEmpty()) {
            workers = CanteenUserResourceIT.createEntity(em);
            em.persist(workers);
            em.flush();
        } else {
            workers = TestUtil.findAll(em, CanteenUser.class).get(0);
        }
        em.persist(workers);
        em.flush();
        canteenUser.setWorkers(workers);
        canteenUserRepository.saveAndFlush(canteenUser);
        Long workersId = workers.getId();

        // Get all the canteenUserList where workers equals to workersId
        defaultCanteenUserShouldBeFound("workersId.equals=" + workersId);

        // Get all the canteenUserList where workers equals to (workersId + 1)
        defaultCanteenUserShouldNotBeFound("workersId.equals=" + (workersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCanteenUserShouldBeFound(String filter) throws Exception {
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(canteenUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneVerified").value(hasItem(DEFAULT_PHONE_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].kkUseId").value(hasItem(DEFAULT_KK_USE_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCanteenUserShouldNotBeFound(String filter) throws Exception {
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCanteenUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCanteenUser() throws Exception {
        // Get the canteenUser
        restCanteenUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCanteenUser() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();

        // Update the canteenUser
        CanteenUser updatedCanteenUser = canteenUserRepository.findById(canteenUser.getId()).get();
        // Disconnect from session so that the updates on updatedCanteenUser are not directly saved in db
        em.detach(updatedCanteenUser);
        updatedCanteenUser
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .imageUrl(UPDATED_IMAGE_URL)
            .isEnabled(UPDATED_IS_ENABLED)
            .phoneVerified(UPDATED_PHONE_VERIFIED)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .kkUseId(UPDATED_KK_USE_ID)
            .role(UPDATED_ROLE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(updatedCanteenUser);

        restCanteenUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, canteenUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
        CanteenUser testCanteenUser = canteenUserList.get(canteenUserList.size() - 1);
        assertThat(testCanteenUser.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCanteenUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCanteenUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCanteenUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCanteenUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCanteenUser.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testCanteenUser.getPhoneVerified()).isEqualTo(UPDATED_PHONE_VERIFIED);
        assertThat(testCanteenUser.getEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testCanteenUser.getKkUseId()).isEqualTo(UPDATED_KK_USE_ID);
        assertThat(testCanteenUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCanteenUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCanteenUser.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testCanteenUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCanteenUser.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, canteenUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCanteenUserWithPatch() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();

        // Update the canteenUser using partial update
        CanteenUser partialUpdatedCanteenUser = new CanteenUser();
        partialUpdatedCanteenUser.setId(canteenUser.getId());

        partialUpdatedCanteenUser
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .imageUrl(UPDATED_IMAGE_URL)
            .phoneVerified(UPDATED_PHONE_VERIFIED)
            .kkUseId(UPDATED_KK_USE_ID)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restCanteenUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCanteenUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCanteenUser))
            )
            .andExpect(status().isOk());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
        CanteenUser testCanteenUser = canteenUserList.get(canteenUserList.size() - 1);
        assertThat(testCanteenUser.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCanteenUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCanteenUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCanteenUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCanteenUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCanteenUser.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testCanteenUser.getPhoneVerified()).isEqualTo(UPDATED_PHONE_VERIFIED);
        assertThat(testCanteenUser.getEmailVerified()).isEqualTo(DEFAULT_EMAIL_VERIFIED);
        assertThat(testCanteenUser.getKkUseId()).isEqualTo(UPDATED_KK_USE_ID);
        assertThat(testCanteenUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCanteenUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCanteenUser.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testCanteenUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCanteenUser.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCanteenUserWithPatch() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();

        // Update the canteenUser using partial update
        CanteenUser partialUpdatedCanteenUser = new CanteenUser();
        partialUpdatedCanteenUser.setId(canteenUser.getId());

        partialUpdatedCanteenUser
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .imageUrl(UPDATED_IMAGE_URL)
            .isEnabled(UPDATED_IS_ENABLED)
            .phoneVerified(UPDATED_PHONE_VERIFIED)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .kkUseId(UPDATED_KK_USE_ID)
            .role(UPDATED_ROLE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restCanteenUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCanteenUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCanteenUser))
            )
            .andExpect(status().isOk());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
        CanteenUser testCanteenUser = canteenUserList.get(canteenUserList.size() - 1);
        assertThat(testCanteenUser.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCanteenUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCanteenUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCanteenUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCanteenUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCanteenUser.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testCanteenUser.getPhoneVerified()).isEqualTo(UPDATED_PHONE_VERIFIED);
        assertThat(testCanteenUser.getEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testCanteenUser.getKkUseId()).isEqualTo(UPDATED_KK_USE_ID);
        assertThat(testCanteenUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCanteenUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCanteenUser.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testCanteenUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCanteenUser.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, canteenUserDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCanteenUser() throws Exception {
        int databaseSizeBeforeUpdate = canteenUserRepository.findAll().size();
        canteenUser.setId(count.incrementAndGet());

        // Create the CanteenUser
        CanteenUserDTO canteenUserDTO = canteenUserMapper.toDto(canteenUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCanteenUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(canteenUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CanteenUser in the database
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCanteenUser() throws Exception {
        // Initialize the database
        canteenUserRepository.saveAndFlush(canteenUser);

        int databaseSizeBeforeDelete = canteenUserRepository.findAll().size();

        // Delete the canteenUser
        restCanteenUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, canteenUser.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CanteenUser> canteenUserList = canteenUserRepository.findAll();
        assertThat(canteenUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
