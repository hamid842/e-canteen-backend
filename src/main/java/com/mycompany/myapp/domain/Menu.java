package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

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

    @OneToMany(mappedBy = "products")
    @JsonIgnoreProperties(value = { "everyProductCategory", "productItem", "product", "productItemsList", "products" }, allowSetters = true)
    private Set<Product> menus = new HashSet<>();

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
    @OneToOne(mappedBy = "everyWorkerAtSchool")
    private CanteenUser everyMenu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Menu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Menu name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Menu code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Menu createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Menu modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Product> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Product> products) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setProducts(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProducts(this));
        }
        this.menus = products;
    }

    public Menu menus(Set<Product> products) {
        this.setMenus(products);
        return this;
    }

    public Menu addMenu(Product product) {
        this.menus.add(product);
        product.setProducts(this);
        return this;
    }

    public Menu removeMenu(Product product) {
        this.menus.remove(product);
        product.setProducts(null);
        return this;
    }

    public CanteenUser getEveryMenu() {
        return this.everyMenu;
    }

    public void setEveryMenu(CanteenUser canteenUser) {
        if (this.everyMenu != null) {
            this.everyMenu.setEveryWorkerAtSchool(null);
        }
        if (canteenUser != null) {
            canteenUser.setEveryWorkerAtSchool(this);
        }
        this.everyMenu = canteenUser;
    }

    public Menu everyMenu(CanteenUser canteenUser) {
        this.setEveryMenu(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
