package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "everyProductCategory", "productItem", "product", "productItemsList", "products" }, allowSetters = true)
    private Set<Product> productCategoryItems = new HashSet<>();

    @JsonIgnoreProperties(value = { "everyProductCategory", "productItem", "product", "productItemsList", "products" }, allowSetters = true)
    @OneToOne(mappedBy = "everyProductCategory")
    private Product everyProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProductCategory name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public ProductCategory code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public ProductCategory createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public ProductCategory modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProductCategory createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public ProductCategory modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<Product> getProductCategoryItems() {
        return this.productCategoryItems;
    }

    public void setProductCategoryItems(Set<Product> products) {
        if (this.productCategoryItems != null) {
            this.productCategoryItems.forEach(i -> i.setProduct(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProduct(this));
        }
        this.productCategoryItems = products;
    }

    public ProductCategory productCategoryItems(Set<Product> products) {
        this.setProductCategoryItems(products);
        return this;
    }

    public ProductCategory addProductCategoryItem(Product product) {
        this.productCategoryItems.add(product);
        product.setProduct(this);
        return this;
    }

    public ProductCategory removeProductCategoryItem(Product product) {
        this.productCategoryItems.remove(product);
        product.setProduct(null);
        return this;
    }

    public Product getEveryProduct() {
        return this.everyProduct;
    }

    public void setEveryProduct(Product product) {
        if (this.everyProduct != null) {
            this.everyProduct.setEveryProductCategory(null);
        }
        if (product != null) {
            product.setEveryProductCategory(this);
        }
        this.everyProduct = product;
    }

    public ProductCategory everyProduct(Product product) {
        this.setEveryProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
