package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ActivationCode} entity.
 */
public class ActivationCodeDTO implements Serializable {

    private Long id;

    private String code;

    private String expiryTime;

    private ZonedDateTime createdDate;

    private String createdBy;

    private CanteenUserDTO parentActivationCode;

    private CanteenUserDTO workerActivationCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CanteenUserDTO getParentActivationCode() {
        return parentActivationCode;
    }

    public void setParentActivationCode(CanteenUserDTO parentActivationCode) {
        this.parentActivationCode = parentActivationCode;
    }

    public CanteenUserDTO getWorkerActivationCode() {
        return workerActivationCode;
    }

    public void setWorkerActivationCode(CanteenUserDTO workerActivationCode) {
        this.workerActivationCode = workerActivationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivationCodeDTO)) {
            return false;
        }

        ActivationCodeDTO activationCodeDTO = (ActivationCodeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, activationCodeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivationCodeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", parentActivationCode=" + getParentActivationCode() +
            ", workerActivationCode=" + getWorkerActivationCode() +
            "}";
    }
}
