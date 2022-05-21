package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.service.dto.UserAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAccount} and its DTO {@link UserAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, UserAccount> {}
