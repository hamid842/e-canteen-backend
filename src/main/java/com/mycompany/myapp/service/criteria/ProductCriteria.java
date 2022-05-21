package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.Rating;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Product} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Rating
     */
    public static class RatingFilter extends Filter<Rating> {

        public RatingFilter() {}

        public RatingFilter(RatingFilter filter) {
            super(filter);
        }

        @Override
        public RatingFilter copy() {
            return new RatingFilter(this);
        }
    }

    /**
     * Class for filtering Category
     */
    public static class CategoryFilter extends Filter<Category> {

        public CategoryFilter() {}

        public CategoryFilter(CategoryFilter filter) {
            super(filter);
        }

        @Override
        public CategoryFilter copy() {
            return new CategoryFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BigDecimalFilter price;

    private StringFilter barcode;

    private RatingFilter grade;

    private CategoryFilter category;

    private StringFilter imageUrl;

    private ZonedDateTimeFilter expiryDate;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter modifiedDate;

    private StringFilter createdBy;

    private StringFilter modifiedBy;

    private LongFilter everyProductCategoryId;

    private LongFilter productItemId;

    private LongFilter productId;

    private LongFilter productItemsListId;

    private LongFilter productsId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.barcode = other.barcode == null ? null : other.barcode.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.everyProductCategoryId = other.everyProductCategoryId == null ? null : other.everyProductCategoryId.copy();
        this.productItemId = other.productItemId == null ? null : other.productItemId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productItemsListId = other.productItemsListId == null ? null : other.productItemsListId.copy();
        this.productsId = other.productsId == null ? null : other.productsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public StringFilter getBarcode() {
        return barcode;
    }

    public StringFilter barcode() {
        if (barcode == null) {
            barcode = new StringFilter();
        }
        return barcode;
    }

    public void setBarcode(StringFilter barcode) {
        this.barcode = barcode;
    }

    public RatingFilter getGrade() {
        return grade;
    }

    public RatingFilter grade() {
        if (grade == null) {
            grade = new RatingFilter();
        }
        return grade;
    }

    public void setGrade(RatingFilter grade) {
        this.grade = grade;
    }

    public CategoryFilter getCategory() {
        return category;
    }

    public CategoryFilter category() {
        if (category == null) {
            category = new CategoryFilter();
        }
        return category;
    }

    public void setCategory(CategoryFilter category) {
        this.category = category;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTimeFilter getExpiryDate() {
        return expiryDate;
    }

    public ZonedDateTimeFilter expiryDate() {
        if (expiryDate == null) {
            expiryDate = new ZonedDateTimeFilter();
        }
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTimeFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public ZonedDateTimeFilter createdDate() {
        if (createdDate == null) {
            createdDate = new ZonedDateTimeFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getModifiedDate() {
        return modifiedDate;
    }

    public ZonedDateTimeFilter modifiedDate() {
        if (modifiedDate == null) {
            modifiedDate = new ZonedDateTimeFilter();
        }
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTimeFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public StringFilter modifiedBy() {
        if (modifiedBy == null) {
            modifiedBy = new StringFilter();
        }
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LongFilter getEveryProductCategoryId() {
        return everyProductCategoryId;
    }

    public LongFilter everyProductCategoryId() {
        if (everyProductCategoryId == null) {
            everyProductCategoryId = new LongFilter();
        }
        return everyProductCategoryId;
    }

    public void setEveryProductCategoryId(LongFilter everyProductCategoryId) {
        this.everyProductCategoryId = everyProductCategoryId;
    }

    public LongFilter getProductItemId() {
        return productItemId;
    }

    public LongFilter productItemId() {
        if (productItemId == null) {
            productItemId = new LongFilter();
        }
        return productItemId;
    }

    public void setProductItemId(LongFilter productItemId) {
        this.productItemId = productItemId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductItemsListId() {
        return productItemsListId;
    }

    public LongFilter productItemsListId() {
        if (productItemsListId == null) {
            productItemsListId = new LongFilter();
        }
        return productItemsListId;
    }

    public void setProductItemsListId(LongFilter productItemsListId) {
        this.productItemsListId = productItemsListId;
    }

    public LongFilter getProductsId() {
        return productsId;
    }

    public LongFilter productsId() {
        if (productsId == null) {
            productsId = new LongFilter();
        }
        return productsId;
    }

    public void setProductsId(LongFilter productsId) {
        this.productsId = productsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(price, that.price) &&
            Objects.equals(barcode, that.barcode) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(category, that.category) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(everyProductCategoryId, that.everyProductCategoryId) &&
            Objects.equals(productItemId, that.productItemId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productItemsListId, that.productItemsListId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            price,
            barcode,
            grade,
            category,
            imageUrl,
            expiryDate,
            createdDate,
            modifiedDate,
            createdBy,
            modifiedBy,
            everyProductCategoryId,
            productItemId,
            productId,
            productItemsListId,
            productsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (barcode != null ? "barcode=" + barcode + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
            (everyProductCategoryId != null ? "everyProductCategoryId=" + everyProductCategoryId + ", " : "") +
            (productItemId != null ? "productItemId=" + productItemId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (productItemsListId != null ? "productItemsListId=" + productItemsListId + ", " : "") +
            (productsId != null ? "productsId=" + productsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
