package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {}
