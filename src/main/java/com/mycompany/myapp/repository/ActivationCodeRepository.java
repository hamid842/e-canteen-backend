package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ActivationCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ActivationCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {}
