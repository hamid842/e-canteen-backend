package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CanteenUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CanteenUserDTO.class);
        CanteenUserDTO canteenUserDTO1 = new CanteenUserDTO();
        canteenUserDTO1.setId(1L);
        CanteenUserDTO canteenUserDTO2 = new CanteenUserDTO();
        assertThat(canteenUserDTO1).isNotEqualTo(canteenUserDTO2);
        canteenUserDTO2.setId(canteenUserDTO1.getId());
        assertThat(canteenUserDTO1).isEqualTo(canteenUserDTO2);
        canteenUserDTO2.setId(2L);
        assertThat(canteenUserDTO1).isNotEqualTo(canteenUserDTO2);
        canteenUserDTO1.setId(null);
        assertThat(canteenUserDTO1).isNotEqualTo(canteenUserDTO2);
    }
}
