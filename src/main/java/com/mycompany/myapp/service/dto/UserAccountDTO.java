package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserAccount} entity.
 */
public class UserAccountDTO implements Serializable {

    private Long id;

    private String accountNumber;

    private String accountName;

    private BigDecimal walletBalance;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccountDTO)) {
            return false;
        }

        UserAccountDTO userAccountDTO = (UserAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccountDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", walletBalance=" + getWalletBalance() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
