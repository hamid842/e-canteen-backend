package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "wallet_balance", precision = 21, scale = 2)
    private BigDecimal walletBalance;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

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
    @OneToOne(mappedBy = "everyParent")
    private CanteenUser everyAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public UserAccount accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public UserAccount accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getWalletBalance() {
        return this.walletBalance;
    }

    public UserAccount walletBalance(BigDecimal walletBalance) {
        this.setWalletBalance(walletBalance);
        return this;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public UserAccount createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public UserAccount modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public CanteenUser getEveryAccount() {
        return this.everyAccount;
    }

    public void setEveryAccount(CanteenUser canteenUser) {
        if (this.everyAccount != null) {
            this.everyAccount.setEveryParent(null);
        }
        if (canteenUser != null) {
            canteenUser.setEveryParent(this);
        }
        this.everyAccount = canteenUser;
    }

    public UserAccount everyAccount(CanteenUser canteenUser) {
        this.setEveryAccount(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        return id != null && id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", walletBalance=" + getWalletBalance() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
