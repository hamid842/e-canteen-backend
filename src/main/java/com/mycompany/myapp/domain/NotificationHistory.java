package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NotificationMethod;
import com.mycompany.myapp.domain.enumeration.NotificationStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A NotificationHistory.
 */
@Entity
@Table(name = "notification_history")
public class NotificationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private NotificationMethod method;

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
    private CanteenUser parentNotificationHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotificationHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public NotificationHistory date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public NotificationStatus getStatus() {
        return this.status;
    }

    public NotificationHistory status(NotificationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationMethod getMethod() {
        return this.method;
    }

    public NotificationHistory method(NotificationMethod method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(NotificationMethod method) {
        this.method = method;
    }

    public CanteenUser getParentNotificationHistory() {
        return this.parentNotificationHistory;
    }

    public void setParentNotificationHistory(CanteenUser canteenUser) {
        this.parentNotificationHistory = canteenUser;
    }

    public NotificationHistory parentNotificationHistory(CanteenUser canteenUser) {
        this.setParentNotificationHistory(canteenUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationHistory)) {
            return false;
        }
        return id != null && id.equals(((NotificationHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
