package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivationCodeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivationCodeDTO.class);
        ActivationCodeDTO activationCodeDTO1 = new ActivationCodeDTO();
        activationCodeDTO1.setId(1L);
        ActivationCodeDTO activationCodeDTO2 = new ActivationCodeDTO();
        assertThat(activationCodeDTO1).isNotEqualTo(activationCodeDTO2);
        activationCodeDTO2.setId(activationCodeDTO1.getId());
        assertThat(activationCodeDTO1).isEqualTo(activationCodeDTO2);
        activationCodeDTO2.setId(2L);
        assertThat(activationCodeDTO1).isNotEqualTo(activationCodeDTO2);
        activationCodeDTO1.setId(null);
        assertThat(activationCodeDTO1).isNotEqualTo(activationCodeDTO2);
    }
}
