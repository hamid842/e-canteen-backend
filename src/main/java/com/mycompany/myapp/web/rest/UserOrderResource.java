package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.UserOrderRepository;
import com.mycompany.myapp.service.UserOrderService;
import com.mycompany.myapp.service.dto.UserOrderDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserOrder}.
 */
@RestController
@RequestMapping("/api")
public class UserOrderResource {

    private final Logger log = LoggerFactory.getLogger(UserOrderResource.class);

    private static final String ENTITY_NAME = "userOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserOrderService userOrderService;

    private final UserOrderRepository userOrderRepository;

    public UserOrderResource(UserOrderService userOrderService, UserOrderRepository userOrderRepository) {
        this.userOrderService = userOrderService;
        this.userOrderRepository = userOrderRepository;
    }

    /**
     * {@code POST  /user-orders} : Create a new userOrder.
     *
     * @param userOrderDTO the userOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userOrderDTO, or with status {@code 400 (Bad Request)} if the userOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-orders")
    public ResponseEntity<UserOrderDTO> createUserOrder(@RequestBody UserOrderDTO userOrderDTO) throws URISyntaxException {
        log.debug("REST request to save UserOrder : {}", userOrderDTO);
        if (userOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new userOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserOrderDTO result = userOrderService.save(userOrderDTO);
        return ResponseEntity
            .created(new URI("/api/user-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-orders/:id} : Updates an existing userOrder.
     *
     * @param id the id of the userOrderDTO to save.
     * @param userOrderDTO the userOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userOrderDTO,
     * or with status {@code 400 (Bad Request)} if the userOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-orders/{id}")
    public ResponseEntity<UserOrderDTO> updateUserOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserOrderDTO userOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserOrder : {}, {}", id, userOrderDTO);
        if (userOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserOrderDTO result = userOrderService.update(userOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-orders/:id} : Partial updates given fields of an existing userOrder, field will ignore if it is null
     *
     * @param id the id of the userOrderDTO to save.
     * @param userOrderDTO the userOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userOrderDTO,
     * or with status {@code 400 (Bad Request)} if the userOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserOrderDTO> partialUpdateUserOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserOrderDTO userOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserOrder partially : {}, {}", id, userOrderDTO);
        if (userOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserOrderDTO> result = userOrderService.partialUpdate(userOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-orders} : get all the userOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userOrders in body.
     */
    @GetMapping("/user-orders")
    public ResponseEntity<List<UserOrderDTO>> getAllUserOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserOrders");
        Page<UserOrderDTO> page = userOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-orders/:id} : get the "id" userOrder.
     *
     * @param id the id of the userOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-orders/{id}")
    public ResponseEntity<UserOrderDTO> getUserOrder(@PathVariable Long id) {
        log.debug("REST request to get UserOrder : {}", id);
        Optional<UserOrderDTO> userOrderDTO = userOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userOrderDTO);
    }

    /**
     * {@code DELETE  /user-orders/:id} : delete the "id" userOrder.
     *
     * @param id the id of the userOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-orders/{id}")
    public ResponseEntity<Void> deleteUserOrder(@PathVariable Long id) {
        log.debug("REST request to delete UserOrder : {}", id);
        userOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
