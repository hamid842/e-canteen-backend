package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationHistory.class);
        NotificationHistory notificationHistory1 = new NotificationHistory();
        notificationHistory1.setId(1L);
        NotificationHistory notificationHistory2 = new NotificationHistory();
        notificationHistory2.setId(notificationHistory1.getId());
        assertThat(notificationHistory1).isEqualTo(notificationHistory2);
        notificationHistory2.setId(2L);
        assertThat(notificationHistory1).isNotEqualTo(notificationHistory2);
        notificationHistory1.setId(null);
        assertThat(notificationHistory1).isNotEqualTo(notificationHistory2);
    }
}
