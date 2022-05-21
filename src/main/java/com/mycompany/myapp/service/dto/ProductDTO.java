package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.Rating;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private String barcode;

    private Rating grade;

    private Category category;

    private String imageUrl;

    private ZonedDateTime expiryDate;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private ProductCategoryDTO everyProductCategory;

    private UserOrderDTO productItem;

    private ProductCategoryDTO product;

    private CanteenUserDTO productItemsList;

    private MenuDTO products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Rating getGrade() {
        return grade;
    }

    public void setGrade(Rating grade) {
        this.grade = grade;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ProductCategoryDTO getEveryProductCategory() {
        return everyProductCategory;
    }

    public void setEveryProductCategory(ProductCategoryDTO everyProductCategory) {
        this.everyProductCategory = everyProductCategory;
    }

    public UserOrderDTO getProductItem() {
        return productItem;
    }

    public void setProductItem(UserOrderDTO productItem) {
        this.productItem = productItem;
    }

    public ProductCategoryDTO getProduct() {
        return product;
    }

    public void setProduct(ProductCategoryDTO product) {
        this.product = product;
    }

    public CanteenUserDTO getProductItemsList() {
        return productItemsList;
    }

    public void setProductItemsList(CanteenUserDTO productItemsList) {
        this.productItemsList = productItemsList;
    }

    public MenuDTO getProducts() {
        return products;
    }

    public void setProducts(MenuDTO products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", barcode='" + getBarcode() + "'" +
            ", grade='" + getGrade() + "'" +
            ", category='" + getCategory() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", everyProductCategory=" + getEveryProductCategory() +
            ", productItem=" + getProductItem() +
            ", product=" + getProduct() +
            ", productItemsList=" + getProductItemsList() +
            ", products=" + getProducts() +
            "}";
    }
}
