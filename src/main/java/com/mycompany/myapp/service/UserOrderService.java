package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UserOrderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.UserOrder}.
 */
public interface UserOrderService {
    /**
     * Save a userOrder.
     *
     * @param userOrderDTO the entity to save.
     * @return the persisted entity.
     */
    UserOrderDTO save(UserOrderDTO userOrderDTO);

    /**
     * Updates a userOrder.
     *
     * @param userOrderDTO the entity to update.
     * @return the persisted entity.
     */
    UserOrderDTO update(UserOrderDTO userOrderDTO);

    /**
     * Partially updates a userOrder.
     *
     * @param userOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserOrderDTO> partialUpdate(UserOrderDTO userOrderDTO);

    /**
     * Get all the userOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserOrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserOrderDTO> findOne(Long id);

    /**
     * Delete the "id" userOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
