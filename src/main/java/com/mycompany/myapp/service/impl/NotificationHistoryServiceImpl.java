package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.NotificationHistory;
import com.mycompany.myapp.repository.NotificationHistoryRepository;
import com.mycompany.myapp.service.NotificationHistoryService;
import com.mycompany.myapp.service.dto.NotificationHistoryDTO;
import com.mycompany.myapp.service.mapper.NotificationHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationHistory}.
 */
@Service
@Transactional
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final Logger log = LoggerFactory.getLogger(NotificationHistoryServiceImpl.class);

    private final NotificationHistoryRepository notificationHistoryRepository;

    private final NotificationHistoryMapper notificationHistoryMapper;

    public NotificationHistoryServiceImpl(
        NotificationHistoryRepository notificationHistoryRepository,
        NotificationHistoryMapper notificationHistoryMapper
    ) {
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.notificationHistoryMapper = notificationHistoryMapper;
    }

    @Override
    public NotificationHistoryDTO save(NotificationHistoryDTO notificationHistoryDTO) {
        log.debug("Request to save NotificationHistory : {}", notificationHistoryDTO);
        NotificationHistory notificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDTO);
        notificationHistory = notificationHistoryRepository.save(notificationHistory);
        return notificationHistoryMapper.toDto(notificationHistory);
    }

    @Override
    public NotificationHistoryDTO update(NotificationHistoryDTO notificationHistoryDTO) {
        log.debug("Request to save NotificationHistory : {}", notificationHistoryDTO);
        NotificationHistory notificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDTO);
        notificationHistory = notificationHistoryRepository.save(notificationHistory);
        return notificationHistoryMapper.toDto(notificationHistory);
    }

    @Override
    public Optional<NotificationHistoryDTO> partialUpdate(NotificationHistoryDTO notificationHistoryDTO) {
        log.debug("Request to partially update NotificationHistory : {}", notificationHistoryDTO);

        return notificationHistoryRepository
            .findById(notificationHistoryDTO.getId())
            .map(existingNotificationHistory -> {
                notificationHistoryMapper.partialUpdate(existingNotificationHistory, notificationHistoryDTO);

                return existingNotificationHistory;
            })
            .map(notificationHistoryRepository::save)
            .map(notificationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationHistories");
        return notificationHistoryRepository.findAll(pageable).map(notificationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationHistoryDTO> findOne(Long id) {
        log.debug("Request to get NotificationHistory : {}", id);
        return notificationHistoryRepository.findById(id).map(notificationHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificationHistory : {}", id);
        notificationHistoryRepository.deleteById(id);
    }
}
