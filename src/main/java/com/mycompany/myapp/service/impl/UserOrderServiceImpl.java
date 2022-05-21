package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.repository.UserOrderRepository;
import com.mycompany.myapp.service.UserOrderService;
import com.mycompany.myapp.service.dto.UserOrderDTO;
import com.mycompany.myapp.service.mapper.UserOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserOrder}.
 */
@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {

    private final Logger log = LoggerFactory.getLogger(UserOrderServiceImpl.class);

    private final UserOrderRepository userOrderRepository;

    private final UserOrderMapper userOrderMapper;

    public UserOrderServiceImpl(UserOrderRepository userOrderRepository, UserOrderMapper userOrderMapper) {
        this.userOrderRepository = userOrderRepository;
        this.userOrderMapper = userOrderMapper;
    }

    @Override
    public UserOrderDTO save(UserOrderDTO userOrderDTO) {
        log.debug("Request to save UserOrder : {}", userOrderDTO);
        UserOrder userOrder = userOrderMapper.toEntity(userOrderDTO);
        userOrder = userOrderRepository.save(userOrder);
        return userOrderMapper.toDto(userOrder);
    }

    @Override
    public UserOrderDTO update(UserOrderDTO userOrderDTO) {
        log.debug("Request to save UserOrder : {}", userOrderDTO);
        UserOrder userOrder = userOrderMapper.toEntity(userOrderDTO);
        userOrder = userOrderRepository.save(userOrder);
        return userOrderMapper.toDto(userOrder);
    }

    @Override
    public Optional<UserOrderDTO> partialUpdate(UserOrderDTO userOrderDTO) {
        log.debug("Request to partially update UserOrder : {}", userOrderDTO);

        return userOrderRepository
            .findById(userOrderDTO.getId())
            .map(existingUserOrder -> {
                userOrderMapper.partialUpdate(existingUserOrder, userOrderDTO);

                return existingUserOrder;
            })
            .map(userOrderRepository::save)
            .map(userOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserOrders");
        return userOrderRepository.findAll(pageable).map(userOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserOrderDTO> findOne(Long id) {
        log.debug("Request to get UserOrder : {}", id);
        return userOrderRepository.findById(id).map(userOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserOrder : {}", id);
        userOrderRepository.deleteById(id);
    }
}
