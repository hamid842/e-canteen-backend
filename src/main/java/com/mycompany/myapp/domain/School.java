package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ROLE;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A School.
 */
@Entity
@Table(name = "school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "kk_use_id")
    private String kkUseId;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ROLE role;

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
    @OneToOne
    @JoinColumn(unique = true)
    private CanteenUser everySchool;

    @OneToMany(mappedBy = "parents")
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
    private Set<CanteenUser> studentSchools = new HashSet<>();

    @OneToMany(mappedBy = "students")
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
    private Set<CanteenUser> eachSchools = new HashSet<>();

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
    private CanteenUser school;

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
    private CanteenUser schools;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public School id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public School name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public School email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public School phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public School address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKkUseId() {
        return this.kkUseId;
    }

    public School kkUseId(String kkUseId) {
        this.setKkUseId(kkUseId);
        return this;
    }

    public void setKkUseId(String kkUseId) {
        this.kkUseId = kkUseId;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public School createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public School modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public School createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public School modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ROLE getRole() {
        return this.role;
    }

    public School role(ROLE role) {
        this.setRole(role);
        return this;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public CanteenUser getEverySchool() {
        return this.everySchool;
    }

    public void setEverySchool(CanteenUser canteenUser) {
        this.everySchool = canteenUser;
    }

    public School everySchool(CanteenUser canteenUser) {
        this.setEverySchool(canteenUser);
        return this;
    }

    public Set<CanteenUser> getStudentSchools() {
        return this.studentSchools;
    }

    public void setStudentSchools(Set<CanteenUser> canteenUsers) {
        if (this.studentSchools != null) {
            this.studentSchools.forEach(i -> i.setParents(null));
        }
        if (canteenUsers != null) {
            canteenUsers.forEach(i -> i.setParents(this));
        }
        this.studentSchools = canteenUsers;
    }

    public School studentSchools(Set<CanteenUser> canteenUsers) {
        this.setStudentSchools(canteenUsers);
        return this;
    }

    public School addStudentSchool(CanteenUser canteenUser) {
        this.studentSchools.add(canteenUser);
        canteenUser.setParents(this);
        return this;
    }

    public School removeStudentSchool(CanteenUser canteenUser) {
        this.studentSchools.remove(canteenUser);
        canteenUser.setParents(null);
        return this;
    }

    public Set<CanteenUser> getEachSchools() {
        return this.eachSchools;
    }

    public void setEachSchools(Set<CanteenUser> canteenUsers) {
        if (this.eachSchools != null) {
            this.eachSchools.forEach(i -> i.setStudents(null));
        }
        if (canteenUsers != null) {
            canteenUsers.forEach(i -> i.setStudents(this));
        }
        this.eachSchools = canteenUsers;
    }

    public School eachSchools(Set<CanteenUser> canteenUsers) {
        this.setEachSchools(canteenUsers);
        return this;
    }

    public School addEachSchool(CanteenUser canteenUser) {
        this.eachSchools.add(canteenUser);
        canteenUser.setStudents(this);
        return this;
    }

    public School removeEachSchool(CanteenUser canteenUser) {
        this.eachSchools.remove(canteenUser);
        canteenUser.setStudents(null);
        return this;
    }

    public CanteenUser getSchool() {
        return this.school;
    }

    public void setSchool(CanteenUser canteenUser) {
        this.school = canteenUser;
    }

    public School school(CanteenUser canteenUser) {
        this.setSchool(canteenUser);
        return this;
    }

    public CanteenUser getSchools() {
        return this.schools;
    }

    public void setSchools(CanteenUser canteenUser) {
        this.schools = canteenUser;
    }

    public School schools(CanteenUser canteenUser) {
        this.setSchools(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School)) {
            return false;
        }
        return id != null && id.equals(((School) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", kkUseId='" + getKkUseId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
