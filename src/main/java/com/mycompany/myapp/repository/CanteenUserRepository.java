package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CanteenUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CanteenUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CanteenUserRepository extends JpaRepository<CanteenUser, Long>, JpaSpecificationExecutor<CanteenUser> {}
