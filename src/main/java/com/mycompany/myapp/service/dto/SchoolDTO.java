package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ROLE;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.School} entity.
 */
public class SchoolDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String kkUseId;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private ROLE role;

    private CanteenUserDTO everySchool;

    private CanteenUserDTO school;

    private CanteenUserDTO schools;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKkUseId() {
        return kkUseId;
    }

    public void setKkUseId(String kkUseId) {
        this.kkUseId = kkUseId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public CanteenUserDTO getEverySchool() {
        return everySchool;
    }

    public void setEverySchool(CanteenUserDTO everySchool) {
        this.everySchool = everySchool;
    }

    public CanteenUserDTO getSchool() {
        return school;
    }

    public void setSchool(CanteenUserDTO school) {
        this.school = school;
    }

    public CanteenUserDTO getSchools() {
        return schools;
    }

    public void setSchools(CanteenUserDTO schools) {
        this.schools = schools;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolDTO)) {
            return false;
        }

        SchoolDTO schoolDTO = (SchoolDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, schoolDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolDTO{" +
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
            ", everySchool=" + getEverySchool() +
            ", school=" + getSchool() +
            ", schools=" + getSchools() +
            "}";
    }
}
