package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ActivationCode;
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.service.dto.ActivationCodeDTO;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivationCode} and its DTO {@link ActivationCodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActivationCodeMapper extends EntityMapper<ActivationCodeDTO, ActivationCode> {
    @Mapping(target = "parentActivationCode", source = "parentActivationCode", qualifiedByName = "canteenUserId")
    @Mapping(target = "workerActivationCode", source = "workerActivationCode", qualifiedByName = "canteenUserId")
    ActivationCodeDTO toDto(ActivationCode s);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);
}
