package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CanteenUserRepository;
import com.mycompany.myapp.service.CanteenUserQueryService;
import com.mycompany.myapp.service.CanteenUserService;
import com.mycompany.myapp.service.criteria.CanteenUserCriteria;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CanteenUser}.
 */
@RestController
@RequestMapping("/api")
public class CanteenUserResource {

    private final Logger log = LoggerFactory.getLogger(CanteenUserResource.class);

    private static final String ENTITY_NAME = "canteenUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CanteenUserService canteenUserService;

    private final CanteenUserRepository canteenUserRepository;

    private final CanteenUserQueryService canteenUserQueryService;

    public CanteenUserResource(
        CanteenUserService canteenUserService,
        CanteenUserRepository canteenUserRepository,
        CanteenUserQueryService canteenUserQueryService
    ) {
        this.canteenUserService = canteenUserService;
        this.canteenUserRepository = canteenUserRepository;
        this.canteenUserQueryService = canteenUserQueryService;
    }

    /**
     * {@code POST  /canteen-users} : Create a new canteenUser.
     *
     * @param canteenUserDTO the canteenUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new canteenUserDTO, or with status {@code 400 (Bad Request)} if the canteenUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/canteen-users")
    public ResponseEntity<CanteenUserDTO> createCanteenUser(@RequestBody CanteenUserDTO canteenUserDTO) throws URISyntaxException {
        log.debug("REST request to save CanteenUser : {}", canteenUserDTO);
        if (canteenUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new canteenUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CanteenUserDTO result = canteenUserService.save(canteenUserDTO);
        return ResponseEntity
            .created(new URI("/api/canteen-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /canteen-users/:id} : Updates an existing canteenUser.
     *
     * @param id the id of the canteenUserDTO to save.
     * @param canteenUserDTO the canteenUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated canteenUserDTO,
     * or with status {@code 400 (Bad Request)} if the canteenUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the canteenUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/canteen-users/{id}")
    public ResponseEntity<CanteenUserDTO> updateCanteenUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CanteenUserDTO canteenUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CanteenUser : {}, {}", id, canteenUserDTO);
        if (canteenUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, canteenUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!canteenUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CanteenUserDTO result = canteenUserService.update(canteenUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, canteenUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /canteen-users/:id} : Partial updates given fields of an existing canteenUser, field will ignore if it is null
     *
     * @param id the id of the canteenUserDTO to save.
     * @param canteenUserDTO the canteenUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated canteenUserDTO,
     * or with status {@code 400 (Bad Request)} if the canteenUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the canteenUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the canteenUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/canteen-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CanteenUserDTO> partialUpdateCanteenUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CanteenUserDTO canteenUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CanteenUser partially : {}, {}", id, canteenUserDTO);
        if (canteenUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, canteenUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!canteenUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CanteenUserDTO> result = canteenUserService.partialUpdate(canteenUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, canteenUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /canteen-users} : get all the canteenUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of canteenUsers in body.
     */
    @GetMapping("/canteen-users")
    public ResponseEntity<List<CanteenUserDTO>> getAllCanteenUsers(
        CanteenUserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CanteenUsers by criteria: {}", criteria);
        Page<CanteenUserDTO> page = canteenUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /canteen-users/count} : count all the canteenUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/canteen-users/count")
    public ResponseEntity<Long> countCanteenUsers(CanteenUserCriteria criteria) {
        log.debug("REST request to count CanteenUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(canteenUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /canteen-users/:id} : get the "id" canteenUser.
     *
     * @param id the id of the canteenUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the canteenUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/canteen-users/{id}")
    public ResponseEntity<CanteenUserDTO> getCanteenUser(@PathVariable Long id) {
        log.debug("REST request to get CanteenUser : {}", id);
        Optional<CanteenUserDTO> canteenUserDTO = canteenUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(canteenUserDTO);
    }

    /**
     * {@code DELETE  /canteen-users/:id} : delete the "id" canteenUser.
     *
     * @param id the id of the canteenUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/canteen-users/{id}")
    public ResponseEntity<Void> deleteCanteenUser(@PathVariable Long id) {
        log.debug("REST request to delete CanteenUser : {}", id);
        canteenUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
