package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.PaymentMethod;
import com.mycompany.myapp.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    private String transactionId;

    private TransactionStatus transactionStatus;

    private String orderNumber;

    private PaymentMethod paymentMethod;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private CanteenUserDTO transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public CanteenUserDTO getTransactions() {
        return transactions;
    }

    public void setTransactions(CanteenUserDTO transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionStatus='" + getTransactionStatus() + "'" +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", transactions=" + getTransactions() +
            "}";
    }
}
