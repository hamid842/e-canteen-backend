package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ActivationCode;
import com.mycompany.myapp.repository.ActivationCodeRepository;
import com.mycompany.myapp.service.ActivationCodeService;
import com.mycompany.myapp.service.dto.ActivationCodeDTO;
import com.mycompany.myapp.service.mapper.ActivationCodeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActivationCode}.
 */
@Service
@Transactional
public class ActivationCodeServiceImpl implements ActivationCodeService {

    private final Logger log = LoggerFactory.getLogger(ActivationCodeServiceImpl.class);

    private final ActivationCodeRepository activationCodeRepository;

    private final ActivationCodeMapper activationCodeMapper;

    public ActivationCodeServiceImpl(ActivationCodeRepository activationCodeRepository, ActivationCodeMapper activationCodeMapper) {
        this.activationCodeRepository = activationCodeRepository;
        this.activationCodeMapper = activationCodeMapper;
    }

    @Override
    public ActivationCodeDTO save(ActivationCodeDTO activationCodeDTO) {
        log.debug("Request to save ActivationCode : {}", activationCodeDTO);
        ActivationCode activationCode = activationCodeMapper.toEntity(activationCodeDTO);
        activationCode = activationCodeRepository.save(activationCode);
        return activationCodeMapper.toDto(activationCode);
    }

    @Override
    public ActivationCodeDTO update(ActivationCodeDTO activationCodeDTO) {
        log.debug("Request to save ActivationCode : {}", activationCodeDTO);
        ActivationCode activationCode = activationCodeMapper.toEntity(activationCodeDTO);
        activationCode = activationCodeRepository.save(activationCode);
        return activationCodeMapper.toDto(activationCode);
    }

    @Override
    public Optional<ActivationCodeDTO> partialUpdate(ActivationCodeDTO activationCodeDTO) {
        log.debug("Request to partially update ActivationCode : {}", activationCodeDTO);

        return activationCodeRepository
            .findById(activationCodeDTO.getId())
            .map(existingActivationCode -> {
                activationCodeMapper.partialUpdate(existingActivationCode, activationCodeDTO);

                return existingActivationCode;
            })
            .map(activationCodeRepository::save)
            .map(activationCodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivationCodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivationCodes");
        return activationCodeRepository.findAll(pageable).map(activationCodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActivationCodeDTO> findOne(Long id) {
        log.debug("Request to get ActivationCode : {}", id);
        return activationCodeRepository.findById(id).map(activationCodeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivationCode : {}", id);
        activationCodeRepository.deleteById(id);
    }
}
