package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CanteenUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CanteenUser.class);
        CanteenUser canteenUser1 = new CanteenUser();
        canteenUser1.setId(1L);
        CanteenUser canteenUser2 = new CanteenUser();
        canteenUser2.setId(canteenUser1.getId());
        assertThat(canteenUser1).isEqualTo(canteenUser2);
        canteenUser2.setId(2L);
        assertThat(canteenUser1).isNotEqualTo(canteenUser2);
        canteenUser1.setId(null);
        assertThat(canteenUser1).isNotEqualTo(canteenUser2);
    }
}
