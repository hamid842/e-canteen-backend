package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "transactions", source = "transactions", qualifiedByName = "canteenUserId")
    TransactionDTO toDto(Transaction s);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);
}
