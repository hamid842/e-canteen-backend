package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.service.dto.MenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {}
