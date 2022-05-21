package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ActivationCodeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ActivationCode}.
 */
public interface ActivationCodeService {
    /**
     * Save a activationCode.
     *
     * @param activationCodeDTO the entity to save.
     * @return the persisted entity.
     */
    ActivationCodeDTO save(ActivationCodeDTO activationCodeDTO);

    /**
     * Updates a activationCode.
     *
     * @param activationCodeDTO the entity to update.
     * @return the persisted entity.
     */
    ActivationCodeDTO update(ActivationCodeDTO activationCodeDTO);

    /**
     * Partially updates a activationCode.
     *
     * @param activationCodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ActivationCodeDTO> partialUpdate(ActivationCodeDTO activationCodeDTO);

    /**
     * Get all the activationCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivationCodeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" activationCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivationCodeDTO> findOne(Long id);

    /**
     * Delete the "id" activationCode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
