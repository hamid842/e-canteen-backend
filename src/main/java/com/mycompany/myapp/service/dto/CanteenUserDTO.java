package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ROLE;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CanteenUser} entity.
 */
public class CanteenUserDTO implements Serializable {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String imageUrl;

    private Boolean isEnabled;

    private Boolean phoneVerified;

    private Boolean emailVerified;

    private String kkUseId;

    private ROLE role;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private MenuDTO everyWorkerAtSchool;

    private UserAccountDTO everyParent;

    private CanteenUserDTO children;

    private SchoolDTO parents;

    private SchoolDTO students;

    private CanteenUserDTO workers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getKkUseId() {
        return kkUseId;
    }

    public void setKkUseId(String kkUseId) {
        this.kkUseId = kkUseId;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
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

    public MenuDTO getEveryWorkerAtSchool() {
        return everyWorkerAtSchool;
    }

    public void setEveryWorkerAtSchool(MenuDTO everyWorkerAtSchool) {
        this.everyWorkerAtSchool = everyWorkerAtSchool;
    }

    public UserAccountDTO getEveryParent() {
        return everyParent;
    }

    public void setEveryParent(UserAccountDTO everyParent) {
        this.everyParent = everyParent;
    }

    public CanteenUserDTO getChildren() {
        return children;
    }

    public void setChildren(CanteenUserDTO children) {
        this.children = children;
    }

    public SchoolDTO getParents() {
        return parents;
    }

    public void setParents(SchoolDTO parents) {
        this.parents = parents;
    }

    public SchoolDTO getStudents() {
        return students;
    }

    public void setStudents(SchoolDTO students) {
        this.students = students;
    }

    public CanteenUserDTO getWorkers() {
        return workers;
    }

    public void setWorkers(CanteenUserDTO workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CanteenUserDTO)) {
            return false;
        }

        CanteenUserDTO canteenUserDTO = (CanteenUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, canteenUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CanteenUserDTO{" +
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
            ", everyWorkerAtSchool=" + getEveryWorkerAtSchool() +
            ", everyParent=" + getEveryParent() +
            ", children=" + getChildren() +
            ", parents=" + getParents() +
            ", students=" + getStudents() +
            ", workers=" + getWorkers() +
            "}";
    }
}
