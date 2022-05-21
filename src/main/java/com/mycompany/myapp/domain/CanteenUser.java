package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ROLE;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CanteenUser.
 */
@Entity
@Table(name = "canteen_user")
public class CanteenUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "phone_verified")
    private Boolean phoneVerified;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "kk_use_id")
    private String kkUseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ROLE role;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @JsonIgnoreProperties(value = { "menus", "everyMenu" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Menu everyWorkerAtSchool;

    @JsonIgnoreProperties(value = { "everyAccount" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UserAccount everyParent;

    @OneToMany(mappedBy = "children")
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
    private Set<CanteenUser> parentOfChildren = new HashSet<>();

    @OneToMany(mappedBy = "school")
    @JsonIgnoreProperties(value = { "everySchool", "studentSchools", "eachSchools", "school", "schools" }, allowSetters = true)
    private Set<School> eachParents = new HashSet<>();

    @OneToMany(mappedBy = "workers")
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
    private Set<CanteenUser> eachStusentParents = new HashSet<>();

    @OneToMany(mappedBy = "productItemsList")
    @JsonIgnoreProperties(value = { "everyProductCategory", "productItem", "product", "productItemsList", "products" }, allowSetters = true)
    private Set<Product> eachWorkers = new HashSet<>();

    @OneToMany(mappedBy = "parentActivationCode")
    @JsonIgnoreProperties(value = { "parentActivationCode", "workerActivationCode" }, allowSetters = true)
    private Set<ActivationCode> parentCodes = new HashSet<>();

    @OneToMany(mappedBy = "workerActivationCode")
    @JsonIgnoreProperties(value = { "parentActivationCode", "workerActivationCode" }, allowSetters = true)
    private Set<ActivationCode> workerCodes = new HashSet<>();

    @OneToMany(mappedBy = "parentNotificationHistory")
    @JsonIgnoreProperties(value = { "parentNotificationHistory" }, allowSetters = true)
    private Set<NotificationHistory> parentNotifcations = new HashSet<>();

    @OneToMany(mappedBy = "orders")
    @JsonIgnoreProperties(value = { "userOrderLists", "orders" }, allowSetters = true)
    private Set<UserOrder> eachStudents = new HashSet<>();

    @OneToMany(mappedBy = "transactions")
    @JsonIgnoreProperties(value = { "transactions" }, allowSetters = true)
    private Set<Transaction> parentTransactions = new HashSet<>();

    @OneToMany(mappedBy = "schools")
    @JsonIgnoreProperties(value = { "everySchool", "studentSchools", "eachSchools", "school", "schools" }, allowSetters = true)
    private Set<School> workerAtSchools = new HashSet<>();

    @JsonIgnoreProperties(value = { "everySchool", "studentSchools", "eachSchools", "school", "schools" }, allowSetters = true)
    @OneToOne(mappedBy = "everySchool")
    private School everyWorker;

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
    private CanteenUser children;

    @ManyToOne
    @JsonIgnoreProperties(value = { "everySchool", "studentSchools", "eachSchools", "school", "schools" }, allowSetters = true)
    private School parents;

    @ManyToOne
    @JsonIgnoreProperties(value = { "everySchool", "studentSchools", "eachSchools", "school", "schools" }, allowSetters = true)
    private School students;

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
    private CanteenUser workers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CanteenUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public CanteenUser fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public CanteenUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public CanteenUser phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public CanteenUser address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public CanteenUser imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public CanteenUser isEnabled(Boolean isEnabled) {
        this.setIsEnabled(isEnabled);
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getPhoneVerified() {
        return this.phoneVerified;
    }

    public CanteenUser phoneVerified(Boolean phoneVerified) {
        this.setPhoneVerified(phoneVerified);
        return this;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Boolean getEmailVerified() {
        return this.emailVerified;
    }

    public CanteenUser emailVerified(Boolean emailVerified) {
        this.setEmailVerified(emailVerified);
        return this;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getKkUseId() {
        return this.kkUseId;
    }

    public CanteenUser kkUseId(String kkUseId) {
        this.setKkUseId(kkUseId);
        return this;
    }

    public void setKkUseId(String kkUseId) {
        this.kkUseId = kkUseId;
    }

    public ROLE getRole() {
        return this.role;
    }

    public CanteenUser role(ROLE role) {
        this.setRole(role);
        return this;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public CanteenUser createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public CanteenUser modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CanteenUser createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public CanteenUser modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Menu getEveryWorkerAtSchool() {
        return this.everyWorkerAtSchool;
    }

    public void setEveryWorkerAtSchool(Menu menu) {
        this.everyWorkerAtSchool = menu;
    }

    public CanteenUser everyWorkerAtSchool(Menu menu) {
        this.setEveryWorkerAtSchool(menu);
        return this;
    }

    public UserAccount getEveryParent() {
        return this.everyParent;
    }

    public void setEveryParent(UserAccount userAccount) {
        this.everyParent = userAccount;
    }

    public CanteenUser everyParent(UserAccount userAccount) {
        this.setEveryParent(userAccount);
        return this;
    }

    public Set<CanteenUser> getParentOfChildren() {
        return this.parentOfChildren;
    }

    public void setParentOfChildren(Set<CanteenUser> canteenUsers) {
        if (this.parentOfChildren != null) {
            this.parentOfChildren.forEach(i -> i.setChildren(null));
        }
        if (canteenUsers != null) {
            canteenUsers.forEach(i -> i.setChildren(this));
        }
        this.parentOfChildren = canteenUsers;
    }

    public CanteenUser parentOfChildren(Set<CanteenUser> canteenUsers) {
        this.setParentOfChildren(canteenUsers);
        return this;
    }

    public CanteenUser addParentOfChildren(CanteenUser canteenUser) {
        this.parentOfChildren.add(canteenUser);
        canteenUser.setChildren(this);
        return this;
    }

    public CanteenUser removeParentOfChildren(CanteenUser canteenUser) {
        this.parentOfChildren.remove(canteenUser);
        canteenUser.setChildren(null);
        return this;
    }

    public Set<School> getEachParents() {
        return this.eachParents;
    }

    public void setEachParents(Set<School> schools) {
        if (this.eachParents != null) {
            this.eachParents.forEach(i -> i.setSchool(null));
        }
        if (schools != null) {
            schools.forEach(i -> i.setSchool(this));
        }
        this.eachParents = schools;
    }

    public CanteenUser eachParents(Set<School> schools) {
        this.setEachParents(schools);
        return this;
    }

    public CanteenUser addEachParent(School school) {
        this.eachParents.add(school);
        school.setSchool(this);
        return this;
    }

    public CanteenUser removeEachParent(School school) {
        this.eachParents.remove(school);
        school.setSchool(null);
        return this;
    }

    public Set<CanteenUser> getEachStusentParents() {
        return this.eachStusentParents;
    }

    public void setEachStusentParents(Set<CanteenUser> canteenUsers) {
        if (this.eachStusentParents != null) {
            this.eachStusentParents.forEach(i -> i.setWorkers(null));
        }
        if (canteenUsers != null) {
            canteenUsers.forEach(i -> i.setWorkers(this));
        }
        this.eachStusentParents = canteenUsers;
    }

    public CanteenUser eachStusentParents(Set<CanteenUser> canteenUsers) {
        this.setEachStusentParents(canteenUsers);
        return this;
    }

    public CanteenUser addEachStusentParent(CanteenUser canteenUser) {
        this.eachStusentParents.add(canteenUser);
        canteenUser.setWorkers(this);
        return this;
    }

    public CanteenUser removeEachStusentParent(CanteenUser canteenUser) {
        this.eachStusentParents.remove(canteenUser);
        canteenUser.setWorkers(null);
        return this;
    }

    public Set<Product> getEachWorkers() {
        return this.eachWorkers;
    }

    public void setEachWorkers(Set<Product> products) {
        if (this.eachWorkers != null) {
            this.eachWorkers.forEach(i -> i.setProductItemsList(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProductItemsList(this));
        }
        this.eachWorkers = products;
    }

    public CanteenUser eachWorkers(Set<Product> products) {
        this.setEachWorkers(products);
        return this;
    }

    public CanteenUser addEachWorker(Product product) {
        this.eachWorkers.add(product);
        product.setProductItemsList(this);
        return this;
    }

    public CanteenUser removeEachWorker(Product product) {
        this.eachWorkers.remove(product);
        product.setProductItemsList(null);
        return this;
    }

    public Set<ActivationCode> getParentCodes() {
        return this.parentCodes;
    }

    public void setParentCodes(Set<ActivationCode> activationCodes) {
        if (this.parentCodes != null) {
            this.parentCodes.forEach(i -> i.setParentActivationCode(null));
        }
        if (activationCodes != null) {
            activationCodes.forEach(i -> i.setParentActivationCode(this));
        }
        this.parentCodes = activationCodes;
    }

    public CanteenUser parentCodes(Set<ActivationCode> activationCodes) {
        this.setParentCodes(activationCodes);
        return this;
    }

    public CanteenUser addParentCode(ActivationCode activationCode) {
        this.parentCodes.add(activationCode);
        activationCode.setParentActivationCode(this);
        return this;
    }

    public CanteenUser removeParentCode(ActivationCode activationCode) {
        this.parentCodes.remove(activationCode);
        activationCode.setParentActivationCode(null);
        return this;
    }

    public Set<ActivationCode> getWorkerCodes() {
        return this.workerCodes;
    }

    public void setWorkerCodes(Set<ActivationCode> activationCodes) {
        if (this.workerCodes != null) {
            this.workerCodes.forEach(i -> i.setWorkerActivationCode(null));
        }
        if (activationCodes != null) {
            activationCodes.forEach(i -> i.setWorkerActivationCode(this));
        }
        this.workerCodes = activationCodes;
    }

    public CanteenUser workerCodes(Set<ActivationCode> activationCodes) {
        this.setWorkerCodes(activationCodes);
        return this;
    }

    public CanteenUser addWorkerCode(ActivationCode activationCode) {
        this.workerCodes.add(activationCode);
        activationCode.setWorkerActivationCode(this);
        return this;
    }

    public CanteenUser removeWorkerCode(ActivationCode activationCode) {
        this.workerCodes.remove(activationCode);
        activationCode.setWorkerActivationCode(null);
        return this;
    }

    public Set<NotificationHistory> getParentNotifcations() {
        return this.parentNotifcations;
    }

    public void setParentNotifcations(Set<NotificationHistory> notificationHistories) {
        if (this.parentNotifcations != null) {
            this.parentNotifcations.forEach(i -> i.setParentNotificationHistory(null));
        }
        if (notificationHistories != null) {
            notificationHistories.forEach(i -> i.setParentNotificationHistory(this));
        }
        this.parentNotifcations = notificationHistories;
    }

    public CanteenUser parentNotifcations(Set<NotificationHistory> notificationHistories) {
        this.setParentNotifcations(notificationHistories);
        return this;
    }

    public CanteenUser addParentNotifcations(NotificationHistory notificationHistory) {
        this.parentNotifcations.add(notificationHistory);
        notificationHistory.setParentNotificationHistory(this);
        return this;
    }

    public CanteenUser removeParentNotifcations(NotificationHistory notificationHistory) {
        this.parentNotifcations.remove(notificationHistory);
        notificationHistory.setParentNotificationHistory(null);
        return this;
    }

    public Set<UserOrder> getEachStudents() {
        return this.eachStudents;
    }

    public void setEachStudents(Set<UserOrder> userOrders) {
        if (this.eachStudents != null) {
            this.eachStudents.forEach(i -> i.setOrders(null));
        }
        if (userOrders != null) {
            userOrders.forEach(i -> i.setOrders(this));
        }
        this.eachStudents = userOrders;
    }

    public CanteenUser eachStudents(Set<UserOrder> userOrders) {
        this.setEachStudents(userOrders);
        return this;
    }

    public CanteenUser addEachStudent(UserOrder userOrder) {
        this.eachStudents.add(userOrder);
        userOrder.setOrders(this);
        return this;
    }

    public CanteenUser removeEachStudent(UserOrder userOrder) {
        this.eachStudents.remove(userOrder);
        userOrder.setOrders(null);
        return this;
    }

    public Set<Transaction> getParentTransactions() {
        return this.parentTransactions;
    }

    public void setParentTransactions(Set<Transaction> transactions) {
        if (this.parentTransactions != null) {
            this.parentTransactions.forEach(i -> i.setTransactions(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setTransactions(this));
        }
        this.parentTransactions = transactions;
    }

    public CanteenUser parentTransactions(Set<Transaction> transactions) {
        this.setParentTransactions(transactions);
        return this;
    }

    public CanteenUser addParentTransactions(Transaction transaction) {
        this.parentTransactions.add(transaction);
        transaction.setTransactions(this);
        return this;
    }

    public CanteenUser removeParentTransactions(Transaction transaction) {
        this.parentTransactions.remove(transaction);
        transaction.setTransactions(null);
        return this;
    }

    public Set<School> getWorkerAtSchools() {
        return this.workerAtSchools;
    }

    public void setWorkerAtSchools(Set<School> schools) {
        if (this.workerAtSchools != null) {
            this.workerAtSchools.forEach(i -> i.setSchools(null));
        }
        if (schools != null) {
            schools.forEach(i -> i.setSchools(this));
        }
        this.workerAtSchools = schools;
    }

    public CanteenUser workerAtSchools(Set<School> schools) {
        this.setWorkerAtSchools(schools);
        return this;
    }

    public CanteenUser addWorkerAtSchool(School school) {
        this.workerAtSchools.add(school);
        school.setSchools(this);
        return this;
    }

    public CanteenUser removeWorkerAtSchool(School school) {
        this.workerAtSchools.remove(school);
        school.setSchools(null);
        return this;
    }

    public School getEveryWorker() {
        return this.everyWorker;
    }

    public void setEveryWorker(School school) {
        if (this.everyWorker != null) {
            this.everyWorker.setEverySchool(null);
        }
        if (school != null) {
            school.setEverySchool(this);
        }
        this.everyWorker = school;
    }

    public CanteenUser everyWorker(School school) {
        this.setEveryWorker(school);
        return this;
    }

    public CanteenUser getChildren() {
        return this.children;
    }

    public void setChildren(CanteenUser canteenUser) {
        this.children = canteenUser;
    }

    public CanteenUser children(CanteenUser canteenUser) {
        this.setChildren(canteenUser);
        return this;
    }

    public School getParents() {
        return this.parents;
    }

    public void setParents(School school) {
        this.parents = school;
    }

    public CanteenUser parents(School school) {
        this.setParents(school);
        return this;
    }

    public School getStudents() {
        return this.students;
    }

    public void setStudents(School school) {
        this.students = school;
    }

    public CanteenUser students(School school) {
        this.setStudents(school);
        return this;
    }

    public CanteenUser getWorkers() {
        return this.workers;
    }

    public void setWorkers(CanteenUser canteenUser) {
        this.workers = canteenUser;
    }

    public CanteenUser workers(CanteenUser canteenUser) {
        this.setWorkers(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CanteenUser)) {
            return false;
        }
        return id != null && id.equals(((CanteenUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CanteenUser{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", isEnabled='" + getIsEnabled() + "'" +
            ", phoneVerified='" + getPhoneVerified() + "'" +
            ", emailVerified='" + getEmailVerified() + "'" +
            ", kkUseId='" + getKkUseId() + "'" +
            ", role='" + getRole() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
