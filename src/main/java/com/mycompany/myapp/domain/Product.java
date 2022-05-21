package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.Rating;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "barcode")
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Rating grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @JsonIgnoreProperties(value = { "productCategoryItems", "everyProduct" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductCategory everyProductCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userOrderLists", "orders" }, allowSetters = true)
    private UserOrder productItem;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productCategoryItems", "everyProduct" }, allowSetters = true)
    private ProductCategory product;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "everyWorkerAtSchool",
            "everyParent",
            "parentOfChildren",
            "eachParents",
            "eachStusentParents",
            "eachWorkers",
            "parentCodes",
            "workerCodes",
            "parentNotifcations",
            "eachStudents",
            "parentTransactions",
            "workerAtSchools",
            "everyWorker",
            "children",
            "parents",
            "students",
            "workers",
        },
        allowSetters = true
    )
    private CanteenUser productItemsList;

    @ManyToOne
    @JsonIgnoreProperties(value = { "menus", "everyMenu" }, allowSetters = true)
    private Menu products;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Product price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public Product barcode(String barcode) {
        this.setBarcode(barcode);
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Rating getGrade() {
        return this.grade;
    }

    public Product grade(Rating grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(Rating grade) {
        this.grade = grade;
    }

    public Category getCategory() {
        return this.category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Product imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public Product expiryDate(ZonedDateTime expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Product createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Product modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Product createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Product modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ProductCategory getEveryProductCategory() {
        return this.everyProductCategory;
    }

    public void setEveryProductCategory(ProductCategory productCategory) {
        this.everyProductCategory = productCategory;
    }

    public Product everyProductCategory(ProductCategory productCategory) {
        this.setEveryProductCategory(productCategory);
        return this;
    }

    public UserOrder getProductItem() {
        return this.productItem;
    }

    public void setProductItem(UserOrder userOrder) {
        this.productItem = userOrder;
    }

    public Product productItem(UserOrder userOrder) {
        this.setProductItem(userOrder);
        return this;
    }

    public ProductCategory getProduct() {
        return this.product;
    }

    public void setProduct(ProductCategory productCategory) {
        this.product = productCategory;
    }

    public Product product(ProductCategory productCategory) {
        this.setProduct(productCategory);
        return this;
    }

    public CanteenUser getProductItemsList() {
        return this.productItemsList;
    }

    public void setProductItemsList(CanteenUser canteenUser) {
        this.productItemsList = canteenUser;
    }

    public Product productItemsList(CanteenUser canteenUser) {
        this.setProductItemsList(canteenUser);
        return this;
    }

    public Menu getProducts() {
        return this.products;
    }

    public void setProducts(Menu menu) {
        this.products = menu;
    }

    public Product products(Menu menu) {
        this.setProducts(menu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
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
            "}";
    }
}
