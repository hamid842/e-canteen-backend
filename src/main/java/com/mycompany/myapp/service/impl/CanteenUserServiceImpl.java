package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.repository.CanteenUserRepository;
import com.mycompany.myapp.service.CanteenUserService;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.mapper.CanteenUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CanteenUser}.
 */
@Service
@Transactional
public class CanteenUserServiceImpl implements CanteenUserService {

    private final Logger log = LoggerFactory.getLogger(CanteenUserServiceImpl.class);

    private final CanteenUserRepository canteenUserRepository;

    private final CanteenUserMapper canteenUserMapper;

    public CanteenUserServiceImpl(CanteenUserRepository canteenUserRepository, CanteenUserMapper canteenUserMapper) {
        this.canteenUserRepository = canteenUserRepository;
        this.canteenUserMapper = canteenUserMapper;
    }

    @Override
    public CanteenUserDTO save(CanteenUserDTO canteenUserDTO) {
        log.debug("Request to save CanteenUser : {}", canteenUserDTO);
        CanteenUser canteenUser = canteenUserMapper.toEntity(canteenUserDTO);
        canteenUser = canteenUserRepository.save(canteenUser);
        return canteenUserMapper.toDto(canteenUser);
    }

    @Override
    public CanteenUserDTO update(CanteenUserDTO canteenUserDTO) {
        log.debug("Request to save CanteenUser : {}", canteenUserDTO);
        CanteenUser canteenUser = canteenUserMapper.toEntity(canteenUserDTO);
        canteenUser = canteenUserRepository.save(canteenUser);
        return canteenUserMapper.toDto(canteenUser);
    }

    @Override
    public Optional<CanteenUserDTO> partialUpdate(CanteenUserDTO canteenUserDTO) {
        log.debug("Request to partially update CanteenUser : {}", canteenUserDTO);

        return canteenUserRepository
            .findById(canteenUserDTO.getId())
            .map(existingCanteenUser -> {
                canteenUserMapper.partialUpdate(existingCanteenUser, canteenUserDTO);

                return existingCanteenUser;
            })
            .map(canteenUserRepository::save)
            .map(canteenUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CanteenUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CanteenUsers");
        return canteenUserRepository.findAll(pageable).map(canteenUserMapper::toDto);
    }

    /**
     *  Get all the canteenUsers where EveryWorker is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CanteenUserDTO> findAllWhereEveryWorkerIsNull() {
        log.debug("Request to get all canteenUsers where EveryWorker is null");
        return StreamSupport
            .stream(canteenUserRepository.findAll().spliterator(), false)
            .filter(canteenUser -> canteenUser.getEveryWorker() == null)
            .map(canteenUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CanteenUserDTO> findOne(Long id) {
        log.debug("Request to get CanteenUser : {}", id);
        return canteenUserRepository.findById(id).map(canteenUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CanteenUser : {}", id);
        canteenUserRepository.deleteById(id);
    }
}
