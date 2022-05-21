package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.NotificationHistory;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.NotificationHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationHistory} and its DTO {@link NotificationHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationHistoryMapper extends EntityMapper<NotificationHistoryDTO, NotificationHistory> {
    @Mapping(target = "parentNotificationHistory", source = "parentNotificationHistory", qualifiedByName = "canteenUserId")
    NotificationHistoryDTO toDto(NotificationHistory s);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);
}
