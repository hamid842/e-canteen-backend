package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationHistoryDTO.class);
        NotificationHistoryDTO notificationHistoryDTO1 = new NotificationHistoryDTO();
        notificationHistoryDTO1.setId(1L);
        NotificationHistoryDTO notificationHistoryDTO2 = new NotificationHistoryDTO();
        assertThat(notificationHistoryDTO1).isNotEqualTo(notificationHistoryDTO2);
        notificationHistoryDTO2.setId(notificationHistoryDTO1.getId());
        assertThat(notificationHistoryDTO1).isEqualTo(notificationHistoryDTO2);
        notificationHistoryDTO2.setId(2L);
        assertThat(notificationHistoryDTO1).isNotEqualTo(notificationHistoryDTO2);
        notificationHistoryDTO1.setId(null);
        assertThat(notificationHistoryDTO1).isNotEqualTo(notificationHistoryDTO2);
    }
}
