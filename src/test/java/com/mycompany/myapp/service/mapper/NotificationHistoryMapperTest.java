package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationHistoryMapperTest {

    private NotificationHistoryMapper notificationHistoryMapper;

    @BeforeEach
    public void setUp() {
        notificationHistoryMapper = new NotificationHistoryMapperImpl();
    }
}
