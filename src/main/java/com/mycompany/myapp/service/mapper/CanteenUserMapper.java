package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.MenuDTO;
import com.mycompany.myapp.service.dto.SchoolDTO;
import com.mycompany.myapp.service.dto.UserAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CanteenUser} and its DTO {@link CanteenUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface CanteenUserMapper extends EntityMapper<CanteenUserDTO, CanteenUser> {
    @Mapping(target = "everyWorkerAtSchool", source = "everyWorkerAtSchool", qualifiedByName = "menuId")
    @Mapping(target = "everyParent", source = "everyParent", qualifiedByName = "userAccountId")
    @Mapping(target = "children", source = "children", qualifiedByName = "canteenUserId")
    @Mapping(target = "parents", source = "parents", qualifiedByName = "schoolId")
    @Mapping(target = "students", source = "students", qualifiedByName = "schoolId")
    @Mapping(target = "workers", source = "workers", qualifiedByName = "canteenUserId")
    CanteenUserDTO toDto(CanteenUser s);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);

    @Named("userAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserAccountDTO toDtoUserAccountId(UserAccount userAccount);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);

    @Named("schoolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SchoolDTO toDtoSchoolId(School school);
}
