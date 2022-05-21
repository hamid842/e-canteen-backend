package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CanteenUserMapperTest {

    private CanteenUserMapper canteenUserMapper;

    @BeforeEach
    public void setUp() {
        canteenUserMapper = new CanteenUserMapperImpl();
    }
}
