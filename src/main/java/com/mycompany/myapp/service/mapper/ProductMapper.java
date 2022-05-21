package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductCategory;
import com.mycompany.myapp.domain.UserOrder;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.dto.MenuDTO;
import com.mycompany.myapp.service.dto.ProductCategoryDTO;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.service.dto.UserOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "everyProductCategory", source = "everyProductCategory", qualifiedByName = "productCategoryId")
    @Mapping(target = "productItem", source = "productItem", qualifiedByName = "userOrderId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productCategoryId")
    @Mapping(target = "productItemsList", source = "productItemsList", qualifiedByName = "canteenUserId")
    @Mapping(target = "products", source = "products", qualifiedByName = "menuId")
    ProductDTO toDto(Product s);

    @Named("productCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductCategoryDTO toDtoProductCategoryId(ProductCategory productCategory);

    @Named("userOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserOrderDTO toDtoUserOrderId(UserOrder userOrder);

    @Named("canteenUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CanteenUserDTO toDtoCanteenUserId(CanteenUser canteenUser);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);
}
