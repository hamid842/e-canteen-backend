package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NotificationHistory;
import com.mycompany.myapp.domain.enumeration.NotificationMethod;
import com.mycompany.myapp.domain.enumeration.NotificationStatus;
import com.mycompany.myapp.repository.NotificationHistoryRepository;
import com.mycompany.myapp.service.dto.NotificationHistoryDTO;
import com.mycompany.myapp.service.mapper.NotificationHistoryMapper;
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
 * Integration tests for the {@link NotificationHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationHistoryResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final NotificationStatus DEFAULT_STATUS = NotificationStatus.SENT;
    private static final NotificationStatus UPDATED_STATUS = NotificationStatus.DELIVERED;

    private static final NotificationMethod DEFAULT_METHOD = NotificationMethod.EMAIL;
    private static final NotificationMethod UPDATED_METHOD = NotificationMethod.NOTIFICATION;

    private static final String ENTITY_API_URL = "/api/notification-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    private NotificationHistoryMapper notificationHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationHistoryMockMvc;

    private NotificationHistory notificationHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationHistory createEntity(EntityManager em) {
        NotificationHistory notificationHistory = new NotificationHistory()
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .method(DEFAULT_METHOD);
        return notificationHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationHistory createUpdatedEntity(EntityManager em) {
        NotificationHistory notificationHistory = new NotificationHistory()
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .method(UPDATED_METHOD);
        return notificationHistory;
    }

    @BeforeEach
    public void initTest() {
        notificationHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationHistory() throws Exception {
        int databaseSizeBeforeCreate = notificationHistoryRepository.findAll().size();
        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);
        restNotificationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNotificationHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotificationHistory.getMethod()).isEqualTo(DEFAULT_METHOD);
    }

    @Test
    @Transactional
    void createNotificationHistoryWithExistingId() throws Exception {
        // Create the NotificationHistory with an existing ID
        notificationHistory.setId(1L);
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        int databaseSizeBeforeCreate = notificationHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotificationHistories() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        // Get all the notificationHistoryList
        restNotificationHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())));
    }

    @Test
    @Transactional
    void getNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        // Get the notificationHistory
        restNotificationHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNotificationHistory() throws Exception {
        // Get the notificationHistory
        restNotificationHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();

        // Update the notificationHistory
        NotificationHistory updatedNotificationHistory = notificationHistoryRepository.findById(notificationHistory.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationHistory are not directly saved in db
        em.detach(updatedNotificationHistory);
        updatedNotificationHistory.date(UPDATED_DATE).status(UPDATED_STATUS).method(UPDATED_METHOD);
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(updatedNotificationHistory);

        restNotificationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNotificationHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotificationHistory.getMethod()).isEqualTo(UPDATED_METHOD);
    }

    @Test
    @Transactional
    void putNonExistingNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationHistoryWithPatch() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();

        // Update the notificationHistory using partial update
        NotificationHistory partialUpdatedNotificationHistory = new NotificationHistory();
        partialUpdatedNotificationHistory.setId(notificationHistory.getId());

        partialUpdatedNotificationHistory.status(UPDATED_STATUS);

        restNotificationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationHistory))
            )
            .andExpect(status().isOk());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNotificationHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotificationHistory.getMethod()).isEqualTo(DEFAULT_METHOD);
    }

    @Test
    @Transactional
    void fullUpdateNotificationHistoryWithPatch() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();

        // Update the notificationHistory using partial update
        NotificationHistory partialUpdatedNotificationHistory = new NotificationHistory();
        partialUpdatedNotificationHistory.setId(notificationHistory.getId());

        partialUpdatedNotificationHistory.date(UPDATED_DATE).status(UPDATED_STATUS).method(UPDATED_METHOD);

        restNotificationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationHistory))
            )
            .andExpect(status().isOk());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
        NotificationHistory testNotificationHistory = notificationHistoryList.get(notificationHistoryList.size() - 1);
        assertThat(testNotificationHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNotificationHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotificationHistory.getMethod()).isEqualTo(UPDATED_METHOD);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationHistoryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationHistory() throws Exception {
        int databaseSizeBeforeUpdate = notificationHistoryRepository.findAll().size();
        notificationHistory.setId(count.incrementAndGet());

        // Create the NotificationHistory
        NotificationHistoryDTO notificationHistoryDTO = notificationHistoryMapper.toDto(notificationHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationHistory in the database
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationHistory() throws Exception {
        // Initialize the database
        notificationHistoryRepository.saveAndFlush(notificationHistory);

        int databaseSizeBeforeDelete = notificationHistoryRepository.findAll().size();

        // Delete the notificationHistory
        restNotificationHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationHistory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationHistory> notificationHistoryList = notificationHistoryRepository.findAll();
        assertThat(notificationHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
