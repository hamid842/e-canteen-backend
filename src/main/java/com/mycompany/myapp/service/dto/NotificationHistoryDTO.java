package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.NotificationMethod;
import com.mycompany.myapp.domain.enumeration.NotificationStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.NotificationHistory} entity.
 */
public class NotificationHistoryDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private NotificationStatus status;

    private NotificationMethod method;

    private CanteenUserDTO parentNotificationHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationMethod getMethod() {
        return method;
    }

    public void setMethod(NotificationMethod method) {
        this.method = method;
    }

    public CanteenUserDTO getParentNotificationHistory() {
        return parentNotificationHistory;
    }

    public void setParentNotificationHistory(CanteenUserDTO parentNotificationHistory) {
        this.parentNotificationHistory = parentNotificationHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationHistoryDTO)) {
            return false;
        }

        NotificationHistoryDTO notificationHistoryDTO = (NotificationHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", method='" + getMethod() + "'" +
            ", parentNotificationHistory=" + getParentNotificationHistory() +
            "}";
    }
}
