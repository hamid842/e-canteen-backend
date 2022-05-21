package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOrderDTO.class);
        UserOrderDTO userOrderDTO1 = new UserOrderDTO();
        userOrderDTO1.setId(1L);
        UserOrderDTO userOrderDTO2 = new UserOrderDTO();
        assertThat(userOrderDTO1).isNotEqualTo(userOrderDTO2);
        userOrderDTO2.setId(userOrderDTO1.getId());
        assertThat(userOrderDTO1).isEqualTo(userOrderDTO2);
        userOrderDTO2.setId(2L);
        assertThat(userOrderDTO1).isNotEqualTo(userOrderDTO2);
        userOrderDTO1.setId(null);
        assertThat(userOrderDTO1).isNotEqualTo(userOrderDTO2);
    }
}
