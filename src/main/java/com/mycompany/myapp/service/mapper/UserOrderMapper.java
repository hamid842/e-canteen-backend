package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.UserOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserOrder} and its DTO {@link UserOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserOrderMapper extends EntityMapper<UserOrderDTO, UserOrder> {
    @Mapping(target = "orders", source = "orders", qualifiedByName = "canteenUserId")
    UserOrderDTO toDto(UserOrder s);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);
}
