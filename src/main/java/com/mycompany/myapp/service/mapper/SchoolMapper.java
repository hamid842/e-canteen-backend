package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.SchoolDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link School} and its DTO {@link SchoolDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {
    @Mapping(target = "everySchool", source = "everySchool", qualifiedByName = "canteenUserId")
    @Mapping(target = "school", source = "school", qualifiedByName = "canteenUserId")
    @Mapping(target = "schools", source = "schools", qualifiedByName = "canteenUserId")
    SchoolDTO toDto(School s);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);
}
