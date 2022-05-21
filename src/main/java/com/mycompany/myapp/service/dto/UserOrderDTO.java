package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserOrder} entity.
 */
public class UserOrderDTO implements Serializable {

    private Long id;

    private String orderNumber;

    private String ordrerCode;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private CanteenUserDTO orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrdrerCode() {
        return ordrerCode;
    }

    public void setOrdrerCode(String ordrerCode) {
        this.ordrerCode = ordrerCode;
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

    public CanteenUserDTO getOrders() {
        return orders;
    }

    public void setOrders(CanteenUserDTO orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrderDTO)) {
            return false;
        }

        UserOrderDTO userOrderDTO = (UserOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOrderDTO{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", ordrerCode='" + getOrdrerCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", orders=" + getOrders() +
            "}";
    }
}
