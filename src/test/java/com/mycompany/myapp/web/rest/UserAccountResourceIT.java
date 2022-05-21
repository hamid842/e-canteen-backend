package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.dto.UserAccountDTO;
import com.mycompany.myapp.service.mapper.UserAccountMapper;
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
 * Integration tests for the {@link UserAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WALLET_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WALLET_BALANCE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/user-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .walletBalance(DEFAULT_WALLET_BALANCE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return userAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createUpdatedEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .walletBalance(UPDATED_WALLET_BALANCE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return userAccount;
    }

    @BeforeEach
    public void initTest() {
        userAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();
        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);
        restUserAccountMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testUserAccount.getWalletBalance()).isEqualByComparingTo(DEFAULT_WALLET_BALANCE);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserAccount.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserAccountWithExistingId() throws Exception {
        // Create the UserAccount with an existing ID
        userAccount.setId(1L);
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get all the userAccountList
        restUserAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].walletBalance").value(hasItem(sameNumber(DEFAULT_WALLET_BALANCE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get the userAccount
        restUserAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.walletBalance").value(sameNumber(DEFAULT_WALLET_BALANCE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        UserAccount updatedUserAccount = userAccountRepository.findById(userAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserAccount are not directly saved in db
        em.detach(updatedUserAccount);
        updatedUserAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .walletBalance(UPDATED_WALLET_BALANCE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(updatedUserAccount);

        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAccountDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testUserAccount.getWalletBalance()).isEqualByComparingTo(UPDATED_WALLET_BALANCE);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAccountDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAccountWithPatch() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount using partial update
        UserAccount partialUpdatedUserAccount = new UserAccount();
        partialUpdatedUserAccount.setId(userAccount.getId());

        partialUpdatedUserAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .walletBalance(UPDATED_WALLET_BALANCE)
            .createdDate(UPDATED_CREATED_DATE);

        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccount.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testUserAccount.getWalletBalance()).isEqualByComparingTo(UPDATED_WALLET_BALANCE);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserAccountWithPatch() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount using partial update
        UserAccount partialUpdatedUserAccount = new UserAccount();
        partialUpdatedUserAccount.setId(userAccount.getId());

        partialUpdatedUserAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .walletBalance(UPDATED_WALLET_BALANCE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccount.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testUserAccount.getWalletBalance()).isEqualByComparingTo(UPDATED_WALLET_BALANCE);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAccountDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Delete the userAccount
        restUserAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAccount.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
