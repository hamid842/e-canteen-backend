package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MenuDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Menu}.
 */
public interface MenuService {
    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    MenuDTO save(MenuDTO menuDTO);

    /**
     * Updates a menu.
     *
     * @param menuDTO the entity to update.
     * @return the persisted entity.
     */
    MenuDTO update(MenuDTO menuDTO);

    /**
     * Partially updates a menu.
     *
     * @param menuDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MenuDTO> partialUpdate(MenuDTO menuDTO);

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MenuDTO> findAll(Pageable pageable);
    /**
     * Get all the MenuDTO where EveryMenu is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MenuDTO> findAllWhereEveryMenuIsNull();

    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuDTO> findOne(Long id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
