package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.NotificationHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.NotificationHistory}.
 */
public interface NotificationHistoryService {
    /**
     * Save a notificationHistory.
     *
     * @param notificationHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    NotificationHistoryDTO save(NotificationHistoryDTO notificationHistoryDTO);

    /**
     * Updates a notificationHistory.
     *
     * @param notificationHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    NotificationHistoryDTO update(NotificationHistoryDTO notificationHistoryDTO);

    /**
     * Partially updates a notificationHistory.
     *
     * @param notificationHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotificationHistoryDTO> partialUpdate(NotificationHistoryDTO notificationHistoryDTO);

    /**
     * Get all the notificationHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotificationHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" notificationHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificationHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" notificationHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
