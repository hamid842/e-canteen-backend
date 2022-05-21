package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ActivationCodeRepository;
import com.mycompany.myapp.service.ActivationCodeService;
import com.mycompany.myapp.service.dto.ActivationCodeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ActivationCode}.
 */
@RestController
@RequestMapping("/api")
public class ActivationCodeResource {

    private final Logger log = LoggerFactory.getLogger(ActivationCodeResource.class);

    private static final String ENTITY_NAME = "activationCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivationCodeService activationCodeService;

    private final ActivationCodeRepository activationCodeRepository;

    public ActivationCodeResource(ActivationCodeService activationCodeService, ActivationCodeRepository activationCodeRepository) {
        this.activationCodeService = activationCodeService;
        this.activationCodeRepository = activationCodeRepository;
    }

    /**
     * {@code POST  /activation-codes} : Create a new activationCode.
     *
     * @param activationCodeDTO the activationCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activationCodeDTO, or with status {@code 400 (Bad Request)} if the activationCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activation-codes")
    public ResponseEntity<ActivationCodeDTO> createActivationCode(@RequestBody ActivationCodeDTO activationCodeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ActivationCode : {}", activationCodeDTO);
        if (activationCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new activationCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivationCodeDTO result = activationCodeService.save(activationCodeDTO);
        return ResponseEntity
            .created(new URI("/api/activation-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activation-codes/:id} : Updates an existing activationCode.
     *
     * @param id the id of the activationCodeDTO to save.
     * @param activationCodeDTO the activationCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activationCodeDTO,
     * or with status {@code 400 (Bad Request)} if the activationCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activationCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activation-codes/{id}")
    public ResponseEntity<ActivationCodeDTO> updateActivationCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivationCodeDTO activationCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ActivationCode : {}, {}", id, activationCodeDTO);
        if (activationCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activationCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activationCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivationCodeDTO result = activationCodeService.update(activationCodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activationCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activation-codes/:id} : Partial updates given fields of an existing activationCode, field will ignore if it is null
     *
     * @param id the id of the activationCodeDTO to save.
     * @param activationCodeDTO the activationCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activationCodeDTO,
     * or with status {@code 400 (Bad Request)} if the activationCodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the activationCodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the activationCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activation-codes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivationCodeDTO> partialUpdateActivationCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivationCodeDTO activationCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivationCode partially : {}, {}", id, activationCodeDTO);
        if (activationCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activationCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activationCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivationCodeDTO> result = activationCodeService.partialUpdate(activationCodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activationCodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /activation-codes} : get all the activationCodes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activationCodes in body.
     */
    @GetMapping("/activation-codes")
    public ResponseEntity<List<ActivationCodeDTO>> getAllActivationCodes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ActivationCodes");
        Page<ActivationCodeDTO> page = activationCodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activation-codes/:id} : get the "id" activationCode.
     *
     * @param id the id of the activationCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activationCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activation-codes/{id}")
    public ResponseEntity<ActivationCodeDTO> getActivationCode(@PathVariable Long id) {
        log.debug("REST request to get ActivationCode : {}", id);
        Optional<ActivationCodeDTO> activationCodeDTO = activationCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activationCodeDTO);
    }

    /**
     * {@code DELETE  /activation-codes/:id} : delete the "id" activationCode.
     *
     * @param id the id of the activationCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activation-codes/{id}")
    public ResponseEntity<Void> deleteActivationCode(@PathVariable Long id) {
        log.debug("REST request to delete ActivationCode : {}", id);
        activationCodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
