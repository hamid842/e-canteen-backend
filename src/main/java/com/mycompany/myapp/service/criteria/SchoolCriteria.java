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
 * Criteria class for the {@link com.mycompany.myapp.domain.School} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SchoolResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schools?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SchoolCriteria implements Serializable, Criteria {

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

    private StringFilter name;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter address;

    private StringFilter kkUseId;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter modifiedDate;

    private StringFilter createdBy;

    private StringFilter modifiedBy;

    private ROLEFilter role;

    private LongFilter everySchoolId;

    private LongFilter studentSchoolId;

    private LongFilter eachSchoolId;

    private LongFilter schoolId;

    private LongFilter schoolsId;

    private Boolean distinct;

    public SchoolCriteria() {}

    public SchoolCriteria(SchoolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.kkUseId = other.kkUseId == null ? null : other.kkUseId.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.everySchoolId = other.everySchoolId == null ? null : other.everySchoolId.copy();
        this.studentSchoolId = other.studentSchoolId == null ? null : other.studentSchoolId.copy();
        this.eachSchoolId = other.eachSchoolId == null ? null : other.eachSchoolId.copy();
        this.schoolId = other.schoolId == null ? null : other.schoolId.copy();
        this.schoolsId = other.schoolsId == null ? null : other.schoolsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SchoolCriteria copy() {
        return new SchoolCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public LongFilter getEverySchoolId() {
        return everySchoolId;
    }

    public LongFilter everySchoolId() {
        if (everySchoolId == null) {
            everySchoolId = new LongFilter();
        }
        return everySchoolId;
    }

    public void setEverySchoolId(LongFilter everySchoolId) {
        this.everySchoolId = everySchoolId;
    }

    public LongFilter getStudentSchoolId() {
        return studentSchoolId;
    }

    public LongFilter studentSchoolId() {
        if (studentSchoolId == null) {
            studentSchoolId = new LongFilter();
        }
        return studentSchoolId;
    }

    public void setStudentSchoolId(LongFilter studentSchoolId) {
        this.studentSchoolId = studentSchoolId;
    }

    public LongFilter getEachSchoolId() {
        return eachSchoolId;
    }

    public LongFilter eachSchoolId() {
        if (eachSchoolId == null) {
            eachSchoolId = new LongFilter();
        }
        return eachSchoolId;
    }

    public void setEachSchoolId(LongFilter eachSchoolId) {
        this.eachSchoolId = eachSchoolId;
    }

    public LongFilter getSchoolId() {
        return schoolId;
    }

    public LongFilter schoolId() {
        if (schoolId == null) {
            schoolId = new LongFilter();
        }
        return schoolId;
    }

    public void setSchoolId(LongFilter schoolId) {
        this.schoolId = schoolId;
    }

    public LongFilter getSchoolsId() {
        return schoolsId;
    }

    public LongFilter schoolsId() {
        if (schoolsId == null) {
            schoolsId = new LongFilter();
        }
        return schoolsId;
    }

    public void setSchoolsId(LongFilter schoolsId) {
        this.schoolsId = schoolsId;
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
        final SchoolCriteria that = (SchoolCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(address, that.address) &&
            Objects.equals(kkUseId, that.kkUseId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(role, that.role) &&
            Objects.equals(everySchoolId, that.everySchoolId) &&
            Objects.equals(studentSchoolId, that.studentSchoolId) &&
            Objects.equals(eachSchoolId, that.eachSchoolId) &&
            Objects.equals(schoolId, that.schoolId) &&
            Objects.equals(schoolsId, that.schoolsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            email,
            phoneNumber,
            address,
            kkUseId,
            createdDate,
            modifiedDate,
            createdBy,
            modifiedBy,
            role,
            everySchoolId,
            studentSchoolId,
            eachSchoolId,
            schoolId,
            schoolsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (kkUseId != null ? "kkUseId=" + kkUseId + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (everySchoolId != null ? "everySchoolId=" + everySchoolId + ", " : "") +
            (studentSchoolId != null ? "studentSchoolId=" + studentSchoolId + ", " : "") +
            (eachSchoolId != null ? "eachSchoolId=" + eachSchoolId + ", " : "") +
            (schoolId != null ? "schoolId=" + schoolId + ", " : "") +
            (schoolsId != null ? "schoolsId=" + schoolsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
