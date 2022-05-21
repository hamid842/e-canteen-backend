package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A UserOrder.
 */
@Entity
@Table(name = "user_order")
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "ordrer_code")
    private String ordrerCode;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @OneToMany(mappedBy = "productItem")
    @JsonIgnoreProperties(value = { "everyProductCategory", "productItem", "product", "productItemsList", "products" }, allowSetters = true)
    private Set<Product> userOrderLists = new HashSet<>();

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
    private CanteenUser orders;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public UserOrder orderNumber(String orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrdrerCode() {
        return this.ordrerCode;
    }

    public UserOrder ordrerCode(String ordrerCode) {
        this.setOrdrerCode(ordrerCode);
        return this;
    }

    public void setOrdrerCode(String ordrerCode) {
        this.ordrerCode = ordrerCode;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public UserOrder createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public UserOrder modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Product> getUserOrderLists() {
        return this.userOrderLists;
    }

    public void setUserOrderLists(Set<Product> products) {
        if (this.userOrderLists != null) {
            this.userOrderLists.forEach(i -> i.setProductItem(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProductItem(this));
        }
        this.userOrderLists = products;
    }

    public UserOrder userOrderLists(Set<Product> products) {
        this.setUserOrderLists(products);
        return this;
    }

    public UserOrder addUserOrderList(Product product) {
        this.userOrderLists.add(product);
        product.setProductItem(this);
        return this;
    }

    public UserOrder removeUserOrderList(Product product) {
        this.userOrderLists.remove(product);
        product.setProductItem(null);
        return this;
    }

    public CanteenUser getOrders() {
        return this.orders;
    }

    public void setOrders(CanteenUser canteenUser) {
        this.orders = canteenUser;
    }

    public UserOrder orders(CanteenUser canteenUser) {
        this.setOrders(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrder)) {
            return false;
        }
        return id != null && id.equals(((UserOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOrder{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", ordrerCode='" + getOrdrerCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
