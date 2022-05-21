package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivationCodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivationCode.class);
        ActivationCode activationCode1 = new ActivationCode();
        activationCode1.setId(1L);
        ActivationCode activationCode2 = new ActivationCode();
        activationCode2.setId(activationCode1.getId());
        assertThat(activationCode1).isEqualTo(activationCode2);
        activationCode2.setId(2L);
        assertThat(activationCode1).isNotEqualTo(activationCode2);
        activationCode1.setId(null);
        assertThat(activationCode1).isNotEqualTo(activationCode2);
    }
}
