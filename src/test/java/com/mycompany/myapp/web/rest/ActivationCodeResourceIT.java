package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ActivationCode;
import com.mycompany.myapp.repository.ActivationCodeRepository;
import com.mycompany.myapp.service.dto.ActivationCodeDTO;
import com.mycompany.myapp.service.mapper.ActivationCodeMapper;
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
 * Integration tests for the {@link ActivationCodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivationCodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY_TIME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activation-codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivationCodeRepository activationCodeRepository;

    @Autowired
    private ActivationCodeMapper activationCodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivationCodeMockMvc;

    private ActivationCode activationCode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivationCode createEntity(EntityManager em) {
        ActivationCode activationCode = new ActivationCode()
            .code(DEFAULT_CODE)
            .expiryTime(DEFAULT_EXPIRY_TIME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return activationCode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivationCode createUpdatedEntity(EntityManager em) {
        ActivationCode activationCode = new ActivationCode()
            .code(UPDATED_CODE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        return activationCode;
    }

    @BeforeEach
    public void initTest() {
        activationCode = createEntity(em);
    }

    @Test
    @Transactional
    void createActivationCode() throws Exception {
        int databaseSizeBeforeCreate = activationCodeRepository.findAll().size();
        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);
        restActivationCodeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeCreate + 1);
        ActivationCode testActivationCode = activationCodeList.get(activationCodeList.size() - 1);
        assertThat(testActivationCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActivationCode.getExpiryTime()).isEqualTo(DEFAULT_EXPIRY_TIME);
        assertThat(testActivationCode.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testActivationCode.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createActivationCodeWithExistingId() throws Exception {
        // Create the ActivationCode with an existing ID
        activationCode.setId(1L);
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        int databaseSizeBeforeCreate = activationCodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivationCodeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivationCodes() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        // Get all the activationCodeList
        restActivationCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activationCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].expiryTime").value(hasItem(DEFAULT_EXPIRY_TIME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getActivationCode() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        // Get the activationCode
        restActivationCodeMockMvc
            .perform(get(ENTITY_API_URL_ID, activationCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activationCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.expiryTime").value(DEFAULT_EXPIRY_TIME))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingActivationCode() throws Exception {
        // Get the activationCode
        restActivationCodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivationCode() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();

        // Update the activationCode
        ActivationCode updatedActivationCode = activationCodeRepository.findById(activationCode.getId()).get();
        // Disconnect from session so that the updates on updatedActivationCode are not directly saved in db
        em.detach(updatedActivationCode);
        updatedActivationCode
            .code(UPDATED_CODE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(updatedActivationCode);

        restActivationCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activationCodeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
        ActivationCode testActivationCode = activationCodeList.get(activationCodeList.size() - 1);
        assertThat(testActivationCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActivationCode.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testActivationCode.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActivationCode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activationCodeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivationCodeWithPatch() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();

        // Update the activationCode using partial update
        ActivationCode partialUpdatedActivationCode = new ActivationCode();
        partialUpdatedActivationCode.setId(activationCode.getId());

        partialUpdatedActivationCode.expiryTime(UPDATED_EXPIRY_TIME);

        restActivationCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivationCode.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivationCode))
            )
            .andExpect(status().isOk());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
        ActivationCode testActivationCode = activationCodeList.get(activationCodeList.size() - 1);
        assertThat(testActivationCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActivationCode.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testActivationCode.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testActivationCode.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateActivationCodeWithPatch() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();

        // Update the activationCode using partial update
        ActivationCode partialUpdatedActivationCode = new ActivationCode();
        partialUpdatedActivationCode.setId(activationCode.getId());

        partialUpdatedActivationCode
            .code(UPDATED_CODE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restActivationCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivationCode.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivationCode))
            )
            .andExpect(status().isOk());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
        ActivationCode testActivationCode = activationCodeList.get(activationCodeList.size() - 1);
        assertThat(testActivationCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActivationCode.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testActivationCode.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActivationCode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activationCodeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivationCode() throws Exception {
        int databaseSizeBeforeUpdate = activationCodeRepository.findAll().size();
        activationCode.setId(count.incrementAndGet());

        // Create the ActivationCode
        ActivationCodeDTO activationCodeDTO = activationCodeMapper.toDto(activationCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivationCodeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activationCodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivationCode in the database
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivationCode() throws Exception {
        // Initialize the database
        activationCodeRepository.saveAndFlush(activationCode);

        int databaseSizeBeforeDelete = activationCodeRepository.findAll().size();

        // Delete the activationCode
        restActivationCodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, activationCode.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivationCode> activationCodeList = activationCodeRepository.findAll();
        assertThat(activationCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
