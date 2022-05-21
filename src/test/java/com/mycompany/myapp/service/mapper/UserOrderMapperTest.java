package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserOrderMapperTest {

    private UserOrderMapper userOrderMapper;

    @BeforeEach
    public void setUp() {
        userOrderMapper = new UserOrderMapperImpl();
    }
}
