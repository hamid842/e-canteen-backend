package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.NotificationHistoryRepository;
import com.mycompany.myapp.service.NotificationHistoryService;
import com.mycompany.myapp.service.dto.NotificationHistoryDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NotificationHistory}.
 */
@RestController
@RequestMapping("/api")
public class NotificationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(NotificationHistoryResource.class);

    private static final String ENTITY_NAME = "notificationHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationHistoryService notificationHistoryService;

    private final NotificationHistoryRepository notificationHistoryRepository;

    public NotificationHistoryResource(
        NotificationHistoryService notificationHistoryService,
        NotificationHistoryRepository notificationHistoryRepository
    ) {
        this.notificationHistoryService = notificationHistoryService;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    /**
     * {@code POST  /notification-histories} : Create a new notificationHistory.
     *
     * @param notificationHistoryDTO the notificationHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationHistoryDTO, or with status {@code 400 (Bad Request)} if the notificationHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-histories")
    public ResponseEntity<NotificationHistoryDTO> createNotificationHistory(@RequestBody NotificationHistoryDTO notificationHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save NotificationHistory : {}", notificationHistoryDTO);
        if (notificationHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificationHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationHistoryDTO result = notificationHistoryService.save(notificationHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/notification-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-histories/:id} : Updates an existing notificationHistory.
     *
     * @param id the id of the notificationHistoryDTO to save.
     * @param notificationHistoryDTO the notificationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the notificationHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-histories/{id}")
    public ResponseEntity<NotificationHistoryDTO> updateNotificationHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationHistoryDTO notificationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationHistory : {}, {}", id, notificationHistoryDTO);
        if (notificationHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotificationHistoryDTO result = notificationHistoryService.update(notificationHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-histories/:id} : Partial updates given fields of an existing notificationHistory, field will ignore if it is null
     *
     * @param id the id of the notificationHistoryDTO to save.
     * @param notificationHistoryDTO the notificationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the notificationHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notificationHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationHistoryDTO> partialUpdateNotificationHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationHistoryDTO notificationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationHistory partially : {}, {}", id, notificationHistoryDTO);
        if (notificationHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationHistoryDTO> result = notificationHistoryService.partialUpdate(notificationHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-histories} : get all the notificationHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationHistories in body.
     */
    @GetMapping("/notification-histories")
    public ResponseEntity<List<NotificationHistoryDTO>> getAllNotificationHistories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of NotificationHistories");
        Page<NotificationHistoryDTO> page = notificationHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-histories/:id} : get the "id" notificationHistory.
     *
     * @param id the id of the notificationHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-histories/{id}")
    public ResponseEntity<NotificationHistoryDTO> getNotificationHistory(@PathVariable Long id) {
        log.debug("REST request to get NotificationHistory : {}", id);
        Optional<NotificationHistoryDTO> notificationHistoryDTO = notificationHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationHistoryDTO);
    }

    /**
     * {@code DELETE  /notification-histories/:id} : delete the "id" notificationHistory.
     *
     * @param id the id of the notificationHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-histories/{id}")
    public ResponseEntity<Void> deleteNotificationHistory(@PathVariable Long id) {
        log.debug("REST request to delete NotificationHistory : {}", id);
        notificationHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
