package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.repository.UserOrderRepository;
import com.mycompany.myapp.service.dto.UserOrderDTO;
import com.mycompany.myapp.service.mapper.UserOrderMapper;
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
 * Integration tests for the {@link UserOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserOrderResourceIT {

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ORDRER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORDRER_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/user-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOrderMockMvc;

    private UserOrder userOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOrder createEntity(EntityManager em) {
        UserOrder userOrder = new UserOrder()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .ordrerCode(DEFAULT_ORDRER_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return userOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOrder createUpdatedEntity(EntityManager em) {
        UserOrder userOrder = new UserOrder()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .ordrerCode(UPDATED_ORDRER_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return userOrder;
    }

    @BeforeEach
    public void initTest() {
        userOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createUserOrder() throws Exception {
        int databaseSizeBeforeCreate = userOrderRepository.findAll().size();
        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);
        restUserOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeCreate + 1);
        UserOrder testUserOrder = userOrderList.get(userOrderList.size() - 1);
        assertThat(testUserOrder.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testUserOrder.getOrdrerCode()).isEqualTo(DEFAULT_ORDRER_CODE);
        assertThat(testUserOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserOrder.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserOrderWithExistingId() throws Exception {
        // Create the UserOrder with an existing ID
        userOrder.setId(1L);
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        int databaseSizeBeforeCreate = userOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserOrders() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        // Get all the userOrderList
        restUserOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].ordrerCode").value(hasItem(DEFAULT_ORDRER_CODE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getUserOrder() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        // Get the userOrder
        restUserOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, userOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.ordrerCode").value(DEFAULT_ORDRER_CODE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingUserOrder() throws Exception {
        // Get the userOrder
        restUserOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserOrder() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();

        // Update the userOrder
        UserOrder updatedUserOrder = userOrderRepository.findById(userOrder.getId()).get();
        // Disconnect from session so that the updates on updatedUserOrder are not directly saved in db
        em.detach(updatedUserOrder);
        updatedUserOrder
            .orderNumber(UPDATED_ORDER_NUMBER)
            .ordrerCode(UPDATED_ORDRER_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(updatedUserOrder);

        restUserOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
        UserOrder testUserOrder = userOrderList.get(userOrderList.size() - 1);
        assertThat(testUserOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testUserOrder.getOrdrerCode()).isEqualTo(UPDATED_ORDRER_CODE);
        assertThat(testUserOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserOrder.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserOrderWithPatch() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();

        // Update the userOrder using partial update
        UserOrder partialUpdatedUserOrder = new UserOrder();
        partialUpdatedUserOrder.setId(userOrder.getId());

        partialUpdatedUserOrder.orderNumber(UPDATED_ORDER_NUMBER).modifiedDate(UPDATED_MODIFIED_DATE);

        restUserOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserOrder))
            )
            .andExpect(status().isOk());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
        UserOrder testUserOrder = userOrderList.get(userOrderList.size() - 1);
        assertThat(testUserOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testUserOrder.getOrdrerCode()).isEqualTo(DEFAULT_ORDRER_CODE);
        assertThat(testUserOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserOrder.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserOrderWithPatch() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();

        // Update the userOrder using partial update
        UserOrder partialUpdatedUserOrder = new UserOrder();
        partialUpdatedUserOrder.setId(userOrder.getId());

        partialUpdatedUserOrder
            .orderNumber(UPDATED_ORDER_NUMBER)
            .ordrerCode(UPDATED_ORDRER_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restUserOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserOrder))
            )
            .andExpect(status().isOk());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
        UserOrder testUserOrder = userOrderList.get(userOrderList.size() - 1);
        assertThat(testUserOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testUserOrder.getOrdrerCode()).isEqualTo(UPDATED_ORDRER_CODE);
        assertThat(testUserOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserOrder.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userOrderDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserOrder() throws Exception {
        int databaseSizeBeforeUpdate = userOrderRepository.findAll().size();
        userOrder.setId(count.incrementAndGet());

        // Create the UserOrder
        UserOrderDTO userOrderDTO = userOrderMapper.toDto(userOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserOrder in the database
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserOrder() throws Exception {
        // Initialize the database
        userOrderRepository.saveAndFlush(userOrder);

        int databaseSizeBeforeDelete = userOrderRepository.findAll().size();

        // Delete the userOrder
        restUserOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, userOrder.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserOrder> userOrderList = userOrderRepository.findAll();
        assertThat(userOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
