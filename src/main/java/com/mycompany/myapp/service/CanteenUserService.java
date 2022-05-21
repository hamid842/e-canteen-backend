package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CanteenUserDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CanteenUser}.
 */
public interface CanteenUserService {
    /**
     * Save a canteenUser.
     *
     * @param canteenUserDTO the entity to save.
     * @return the persisted entity.
     */
    CanteenUserDTO save(CanteenUserDTO canteenUserDTO);

    /**
     * Updates a canteenUser.
     *
     * @param canteenUserDTO the entity to update.
     * @return the persisted entity.
     */
    CanteenUserDTO update(CanteenUserDTO canteenUserDTO);

    /**
     * Partially updates a canteenUser.
     *
     * @param canteenUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CanteenUserDTO> partialUpdate(CanteenUserDTO canteenUserDTO);

    /**
     * Get all the canteenUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CanteenUserDTO> findAll(Pageable pageable);
    /**
     * Get all the CanteenUserDTO where EveryWorker is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CanteenUserDTO> findAllWhereEveryWorkerIsNull();

    /**
     * Get the "id" canteenUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CanteenUserDTO> findOne(Long id);

    /**
     * Delete the "id" canteenUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
