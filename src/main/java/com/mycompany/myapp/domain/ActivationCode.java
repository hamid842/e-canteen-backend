package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A ActivationCode.
 */
@Entity
@Table(name = "activation_code")
public class ActivationCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "expiry_time")
    private String expiryTime;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

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
    private CanteenUser parentActivationCode;

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
    private CanteenUser workerActivationCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActivationCode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ActivationCode code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpiryTime() {
        return this.expiryTime;
    }

    public ActivationCode expiryTime(String expiryTime) {
        this.setExpiryTime(expiryTime);
        return this;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public ActivationCode createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ActivationCode createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CanteenUser getParentActivationCode() {
        return this.parentActivationCode;
    }

    public void setParentActivationCode(CanteenUser canteenUser) {
        this.parentActivationCode = canteenUser;
    }

    public ActivationCode parentActivationCode(CanteenUser canteenUser) {
        this.setParentActivationCode(canteenUser);
        return this;
    }

    public CanteenUser getWorkerActivationCode() {
        return this.workerActivationCode;
    }

    public void setWorkerActivationCode(CanteenUser canteenUser) {
        this.workerActivationCode = canteenUser;
    }

    public ActivationCode workerActivationCode(CanteenUser canteenUser) {
        this.setWorkerActivationCode(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivationCode)) {
            return false;
        }
        return id != null && id.equals(((ActivationCode) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivationCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
