package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NotificationHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NotificationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {}
