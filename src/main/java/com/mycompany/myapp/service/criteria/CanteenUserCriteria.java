package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.ROLE;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CanteenUser} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CanteenUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /canteen-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CanteenUserCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ROLE
     */
    public static class ROLEFilter extends Filter<ROLE> {

        public ROLEFilter() {}

        public ROLEFilter(ROLEFilter filter) {
            super(filter);
        }

        @Override
        public ROLEFilter copy() {
            return new ROLEFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter address;

    private StringFilter imageUrl;

    private BooleanFilter isEnabled;

    private BooleanFilter phoneVerified;

    private BooleanFilter emailVerified;

    private StringFilter kkUseId;

    private ROLEFilter role;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter modifiedDate;

    private StringFilter createdBy;

    private StringFilter modifiedBy;

    private LongFilter everyWorkerAtSchoolId;

    private LongFilter everyParentId;

    private LongFilter parentOfChildrenId;

    private LongFilter eachParentId;

    private LongFilter eachStusentParentId;

    private LongFilter eachWorkerId;

    private LongFilter parentCodeId;

    private LongFilter workerCodeId;

    private LongFilter parentNotifcationsId;

    private LongFilter eachStudentId;

    private LongFilter parentTransactionsId;

    private LongFilter workerAtSchoolId;

    private LongFilter everyWorkerId;

    private LongFilter childrenId;

    private LongFilter parentsId;

    private LongFilter studentsId;

    private LongFilter workersId;

    private Boolean distinct;

    public CanteenUserCriteria() {}

    public CanteenUserCriteria(CanteenUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.isEnabled = other.isEnabled == null ? null : other.isEnabled.copy();
        this.phoneVerified = other.phoneVerified == null ? null : other.phoneVerified.copy();
        this.emailVerified = other.emailVerified == null ? null : other.emailVerified.copy();
        this.kkUseId = other.kkUseId == null ? null : other.kkUseId.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.everyWorkerAtSchoolId = other.everyWorkerAtSchoolId == null ? null : other.everyWorkerAtSchoolId.copy();
        this.everyParentId = other.everyParentId == null ? null : other.everyParentId.copy();
        this.parentOfChildrenId = other.parentOfChildrenId == null ? null : other.parentOfChildrenId.copy();
        this.eachParentId = other.eachParentId == null ? null : other.eachParentId.copy();
        this.eachStusentParentId = other.eachStusentParentId == null ? null : other.eachStusentParentId.copy();
        this.eachWorkerId = other.eachWorkerId == null ? null : other.eachWorkerId.copy();
        this.parentCodeId = other.parentCodeId == null ? null : other.parentCodeId.copy();
        this.workerCodeId = other.workerCodeId == null ? null : other.workerCodeId.copy();
        this.parentNotifcationsId = other.parentNotifcationsId == null ? null : other.parentNotifcationsId.copy();
        this.eachStudentId = other.eachStudentId == null ? null : other.eachStudentId.copy();
        this.parentTransactionsId = other.parentTransactionsId == null ? null : other.parentTransactionsId.copy();
        this.workerAtSchoolId = other.workerAtSchoolId == null ? null : other.workerAtSchoolId.copy();
        this.everyWorkerId = other.everyWorkerId == null ? null : other.everyWorkerId.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.parentsId = other.parentsId == null ? null : other.parentsId.copy();
        this.studentsId = other.studentsId == null ? null : other.studentsId.copy();
        this.workersId = other.workersId == null ? null : other.workersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CanteenUserCriteria copy() {
        return new CanteenUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getIsEnabled() {
        return isEnabled;
    }

    public BooleanFilter isEnabled() {
        if (isEnabled == null) {
            isEnabled = new BooleanFilter();
        }
        return isEnabled;
    }

    public void setIsEnabled(BooleanFilter isEnabled) {
        this.isEnabled = isEnabled;
    }

    public BooleanFilter getPhoneVerified() {
        return phoneVerified;
    }

    public BooleanFilter phoneVerified() {
        if (phoneVerified == null) {
            phoneVerified = new BooleanFilter();
        }
        return phoneVerified;
    }

    public void setPhoneVerified(BooleanFilter phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public BooleanFilter getEmailVerified() {
        return emailVerified;
    }

    public BooleanFilter emailVerified() {
        if (emailVerified == null) {
            emailVerified = new BooleanFilter();
        }
        return emailVerified;
    }

    public void setEmailVerified(BooleanFilter emailVerified) {
        this.emailVerified = emailVerified;
    }

    public StringFilter getKkUseId() {
        return kkUseId;
    }

    public StringFilter kkUseId() {
        if (kkUseId == null) {
            kkUseId = new StringFilter();
        }
        return kkUseId;
    }

    public void setKkUseId(StringFilter kkUseId) {
        this.kkUseId = kkUseId;
    }

    public ROLEFilter getRole() {
        return role;
    }

    public ROLEFilter role() {
        if (role == null) {
            role = new ROLEFilter();
        }
        return role;
    }

    public void setRole(ROLEFilter role) {
        this.role = role;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public ZonedDateTimeFilter createdDate() {
        if (createdDate == null) {
            createdDate = new ZonedDateTimeFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getModifiedDate() {
        return modifiedDate;
    }

    public ZonedDateTimeFilter modifiedDate() {
        if (modifiedDate == null) {
            modifiedDate = new ZonedDateTimeFilter();
        }
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTimeFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public StringFilter modifiedBy() {
        if (modifiedBy == null) {
            modifiedBy = new StringFilter();
        }
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LongFilter getEveryWorkerAtSchoolId() {
        return everyWorkerAtSchoolId;
    }

    public LongFilter everyWorkerAtSchoolId() {
        if (everyWorkerAtSchoolId == null) {
            everyWorkerAtSchoolId = new LongFilter();
        }
        return everyWorkerAtSchoolId;
    }

    public void setEveryWorkerAtSchoolId(LongFilter everyWorkerAtSchoolId) {
        this.everyWorkerAtSchoolId = everyWorkerAtSchoolId;
    }

    public LongFilter getEveryParentId() {
        return everyParentId;
    }

    public LongFilter everyParentId() {
        if (everyParentId == null) {
            everyParentId = new LongFilter();
        }
        return everyParentId;
    }

    public void setEveryParentId(LongFilter everyParentId) {
        this.everyParentId = everyParentId;
    }

    public LongFilter getParentOfChildrenId() {
        return parentOfChildrenId;
    }

    public LongFilter parentOfChildrenId() {
        if (parentOfChildrenId == null) {
            parentOfChildrenId = new LongFilter();
        }
        return parentOfChildrenId;
    }

    public void setParentOfChildrenId(LongFilter parentOfChildrenId) {
        this.parentOfChildrenId = parentOfChildrenId;
    }

    public LongFilter getEachParentId() {
        return eachParentId;
    }

    public LongFilter eachParentId() {
        if (eachParentId == null) {
            eachParentId = new LongFilter();
        }
        return eachParentId;
    }

    public void setEachParentId(LongFilter eachParentId) {
        this.eachParentId = eachParentId;
    }

    public LongFilter getEachStusentParentId() {
        return eachStusentParentId;
    }

    public LongFilter eachStusentParentId() {
        if (eachStusentParentId == null) {
            eachStusentParentId = new LongFilter();
        }
        return eachStusentParentId;
    }

    public void setEachStusentParentId(LongFilter eachStusentParentId) {
        this.eachStusentParentId = eachStusentParentId;
    }

    public LongFilter getEachWorkerId() {
        return eachWorkerId;
    }

    public LongFilter eachWorkerId() {
        if (eachWorkerId == null) {
            eachWorkerId = new LongFilter();
        }
        return eachWorkerId;
    }

    public void setEachWorkerId(LongFilter eachWorkerId) {
        this.eachWorkerId = eachWorkerId;
    }

    public LongFilter getParentCodeId() {
        return parentCodeId;
    }

    public LongFilter parentCodeId() {
        if (parentCodeId == null) {
            parentCodeId = new LongFilter();
        }
        return parentCodeId;
    }

    public void setParentCodeId(LongFilter parentCodeId) {
        this.parentCodeId = parentCodeId;
    }

    public LongFilter getWorkerCodeId() {
        return workerCodeId;
    }

    public LongFilter workerCodeId() {
        if (workerCodeId == null) {
            workerCodeId = new LongFilter();
        }
        return workerCodeId;
    }

    public void setWorkerCodeId(LongFilter workerCodeId) {
        this.workerCodeId = workerCodeId;
    }

    public LongFilter getParentNotifcationsId() {
        return parentNotifcationsId;
    }

    public LongFilter parentNotifcationsId() {
        if (parentNotifcationsId == null) {
            parentNotifcationsId = new LongFilter();
        }
        return parentNotifcationsId;
    }

    public void setParentNotifcationsId(LongFilter parentNotifcationsId) {
        this.parentNotifcationsId = parentNotifcationsId;
    }

    public LongFilter getEachStudentId() {
        return eachStudentId;
    }

    public LongFilter eachStudentId() {
        if (eachStudentId == null) {
            eachStudentId = new LongFilter();
        }
        return eachStudentId;
    }

    public void setEachStudentId(LongFilter eachStudentId) {
        this.eachStudentId = eachStudentId;
    }

    public LongFilter getParentTransactionsId() {
        return parentTransactionsId;
    }

    public LongFilter parentTransactionsId() {
        if (parentTransactionsId == null) {
            parentTransactionsId = new LongFilter();
        }
        return parentTransactionsId;
    }

    public void setParentTransactionsId(LongFilter parentTransactionsId) {
        this.parentTransactionsId = parentTransactionsId;
    }

    public LongFilter getWorkerAtSchoolId() {
        return workerAtSchoolId;
    }

    public LongFilter workerAtSchoolId() {
        if (workerAtSchoolId == null) {
            workerAtSchoolId = new LongFilter();
        }
        return workerAtSchoolId;
    }

    public void setWorkerAtSchoolId(LongFilter workerAtSchoolId) {
        this.workerAtSchoolId = workerAtSchoolId;
    }

    public LongFilter getEveryWorkerId() {
        return everyWorkerId;
    }

    public LongFilter everyWorkerId() {
        if (everyWorkerId == null) {
            everyWorkerId = new LongFilter();
        }
        return everyWorkerId;
    }

    public void setEveryWorkerId(LongFilter everyWorkerId) {
        this.everyWorkerId = everyWorkerId;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public LongFilter childrenId() {
        if (childrenId == null) {
            childrenId = new LongFilter();
        }
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getParentsId() {
        return parentsId;
    }

    public LongFilter parentsId() {
        if (parentsId == null) {
            parentsId = new LongFilter();
        }
        return parentsId;
    }

    public void setParentsId(LongFilter parentsId) {
        this.parentsId = parentsId;
    }

    public LongFilter getStudentsId() {
        return studentsId;
    }

    public LongFilter studentsId() {
        if (studentsId == null) {
            studentsId = new LongFilter();
        }
        return studentsId;
    }

    public void setStudentsId(LongFilter studentsId) {
        this.studentsId = studentsId;
    }

    public LongFilter getWorkersId() {
        return workersId;
    }

    public LongFilter workersId() {
        if (workersId == null) {
            workersId = new LongFilter();
        }
        return workersId;
    }

    public void setWorkersId(LongFilter workersId) {
        this.workersId = workersId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CanteenUserCriteria that = (CanteenUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(address, that.address) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(isEnabled, that.isEnabled) &&
            Objects.equals(phoneVerified, that.phoneVerified) &&
            Objects.equals(emailVerified, that.emailVerified) &&
            Objects.equals(kkUseId, that.kkUseId) &&
            Objects.equals(role, that.role) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(everyWorkerAtSchoolId, that.everyWorkerAtSchoolId) &&
            Objects.equals(everyParentId, that.everyParentId) &&
            Objects.equals(parentOfChildrenId, that.parentOfChildrenId) &&
            Objects.equals(eachParentId, that.eachParentId) &&
            Objects.equals(eachStusentParentId, that.eachStusentParentId) &&
            Objects.equals(eachWorkerId, that.eachWorkerId) &&
            Objects.equals(parentCodeId, that.parentCodeId) &&
            Objects.equals(workerCodeId, that.workerCodeId) &&
            Objects.equals(parentNotifcationsId, that.parentNotifcationsId) &&
            Objects.equals(eachStudentId, that.eachStudentId) &&
            Objects.equals(parentTransactionsId, that.parentTransactionsId) &&
            Objects.equals(workerAtSchoolId, that.workerAtSchoolId) &&
            Objects.equals(everyWorkerId, that.everyWorkerId) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(parentsId, that.parentsId) &&
            Objects.equals(studentsId, that.studentsId) &&
            Objects.equals(workersId, that.workersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fullName,
            email,
            phoneNumber,
            address,
            imageUrl,
            isEnabled,
            phoneVerified,
            emailVerified,
            kkUseId,
            role,
            createdDate,
            modifiedDate,
            createdBy,
            modifiedBy,
            everyWorkerAtSchoolId,
            everyParentId,
            parentOfChildrenId,
            eachParentId,
            eachStusentParentId,
            eachWorkerId,
            parentCodeId,
            workerCodeId,
            parentNotifcationsId,
            eachStudentId,
            parentTransactionsId,
            workerAtSchoolId,
            everyWorkerId,
            childrenId,
            parentsId,
            studentsId,
            workersId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CanteenUserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (isEnabled != null ? "isEnabled=" + isEnabled + ", " : "") +
            (phoneVerified != null ? "phoneVerified=" + phoneVerified + ", " : "") +
            (emailVerified != null ? "emailVerified=" + emailVerified + ", " : "") +
            (kkUseId != null ? "kkUseId=" + kkUseId + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
            (everyWorkerAtSchoolId != null ? "everyWorkerAtSchoolId=" + everyWorkerAtSchoolId + ", " : "") +
            (everyParentId != null ? "everyParentId=" + everyParentId + ", " : "") +
            (parentOfChildrenId != null ? "parentOfChildrenId=" + parentOfChildrenId + ", " : "") +
            (eachParentId != null ? "eachParentId=" + eachParentId + ", " : "") +
            (eachStusentParentId != null ? "eachStusentParentId=" + eachStusentParentId + ", " : "") +
            (eachWorkerId != null ? "eachWorkerId=" + eachWorkerId + ", " : "") +
            (parentCodeId != null ? "parentCodeId=" + parentCodeId + ", " : "") +
            (workerCodeId != null ? "workerCodeId=" + workerCodeId + ", " : "") +
            (parentNotifcationsId != null ? "parentNotifcationsId=" + parentNotifcationsId + ", " : "") +
            (eachStudentId != null ? "eachStudentId=" + eachStudentId + ", " : "") +
            (parentTransactionsId != null ? "parentTransactionsId=" + parentTransactionsId + ", " : "") +
            (workerAtSchoolId != null ? "workerAtSchoolId=" + workerAtSchoolId + ", " : "") +
            (everyWorkerId != null ? "everyWorkerId=" + everyWorkerId + ", " : "") +
            (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
            (parentsId != null ? "parentsId=" + parentsId + ", " : "") +
            (studentsId != null ? "studentsId=" + studentsId + ", " : "") +
            (workersId != null ? "workersId=" + workersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
