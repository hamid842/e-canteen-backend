package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CanteenUser;
import com.mycompany.myapp.repository.CanteenUserRepository;
import com.mycompany.myapp.service.criteria.CanteenUserCriteria;
import com.mycompany.myapp.service.dto.CanteenUserDTO;
import com.mycompany.myapp.service.mapper.CanteenUserMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CanteenUser} entities in the database.
 * The main input is a {@link CanteenUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CanteenUserDTO} or a {@link Page} of {@link CanteenUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CanteenUserQueryService extends QueryService<CanteenUser> {

    private final Logger log = LoggerFactory.getLogger(CanteenUserQueryService.class);

    private final CanteenUserRepository canteenUserRepository;

    private final CanteenUserMapper canteenUserMapper;

    public CanteenUserQueryService(CanteenUserRepository canteenUserRepository, CanteenUserMapper canteenUserMapper) {
        this.canteenUserRepository = canteenUserRepository;
        this.canteenUserMapper = canteenUserMapper;
    }

    /**
     * Return a {@link List} of {@link CanteenUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CanteenUserDTO> findByCriteria(CanteenUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CanteenUser> specification = createSpecification(criteria);
        return canteenUserMapper.toDto(canteenUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CanteenUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CanteenUserDTO> findByCriteria(CanteenUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CanteenUser> specification = createSpecification(criteria);
        return canteenUserRepository.findAll(specification, page).map(canteenUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CanteenUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CanteenUser> specification = createSpecification(criteria);
        return canteenUserRepository.count(specification);
    }

    /**
     * Function to convert {@link CanteenUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CanteenUser> createSpecification(CanteenUserCriteria criteria) {
        Specification<CanteenUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CanteenUser_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), CanteenUser_.fullName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CanteenUser_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), CanteenUser_.phoneNumber));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), CanteenUser_.address));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), CanteenUser_.imageUrl));
            }
            if (criteria.getIsEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEnabled(), CanteenUser_.isEnabled));
            }
            if (criteria.getPhoneVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getPhoneVerified(), CanteenUser_.phoneVerified));
            }
            if (criteria.getEmailVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getEmailVerified(), CanteenUser_.emailVerified));
            }
            if (criteria.getKkUseId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKkUseId(), CanteenUser_.kkUseId));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), CanteenUser_.role));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CanteenUser_.createdDate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), CanteenUser_.modifiedDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CanteenUser_.createdBy));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), CanteenUser_.modifiedBy));
            }
            if (criteria.getEveryWorkerAtSchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEveryWorkerAtSchoolId(),
                            root -> root.join(CanteenUser_.everyWorkerAtSchool, JoinType.LEFT).get(Menu_.id)
                        )
                    );
            }
            if (criteria.getEveryParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEveryParentId(),
                            root -> root.join(CanteenUser_.everyParent, JoinType.LEFT).get(UserAccount_.id)
                        )
                    );
            }
            if (criteria.getParentOfChildrenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentOfChildrenId(),
                            root -> root.join(CanteenUser_.parentOfChildren, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getEachParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEachParentId(),
                            root -> root.join(CanteenUser_.eachParents, JoinType.LEFT).get(School_.id)
                        )
                    );
            }
            if (criteria.getEachStusentParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEachStusentParentId(),
                            root -> root.join(CanteenUser_.eachStusentParents, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getEachWorkerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEachWorkerId(),
                            root -> root.join(CanteenUser_.eachWorkers, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getParentCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentCodeId(),
                            root -> root.join(CanteenUser_.parentCodes, JoinType.LEFT).get(ActivationCode_.id)
                        )
                    );
            }
            if (criteria.getWorkerCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorkerCodeId(),
                            root -> root.join(CanteenUser_.workerCodes, JoinType.LEFT).get(ActivationCode_.id)
                        )
                    );
            }
            if (criteria.getParentNotifcationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentNotifcationsId(),
                            root -> root.join(CanteenUser_.parentNotifcations, JoinType.LEFT).get(NotificationHistory_.id)
                        )
                    );
            }
            if (criteria.getEachStudentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEachStudentId(),
                            root -> root.join(CanteenUser_.eachStudents, JoinType.LEFT).get(UserOrder_.id)
                        )
                    );
            }
            if (criteria.getParentTransactionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentTransactionsId(),
                            root -> root.join(CanteenUser_.parentTransactions, JoinType.LEFT).get(Transaction_.id)
                        )
                    );
            }
            if (criteria.getWorkerAtSchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorkerAtSchoolId(),
                            root -> root.join(CanteenUser_.workerAtSchools, JoinType.LEFT).get(School_.id)
                        )
                    );
            }
            if (criteria.getEveryWorkerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEveryWorkerId(),
                            root -> root.join(CanteenUser_.everyWorker, JoinType.LEFT).get(School_.id)
                        )
                    );
            }
            if (criteria.getChildrenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChildrenId(),
                            root -> root.join(CanteenUser_.children, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getParentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParentsId(), root -> root.join(CanteenUser_.parents, JoinType.LEFT).get(School_.id))
                    );
            }
            if (criteria.getStudentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStudentsId(),
                            root -> root.join(CanteenUser_.students, JoinType.LEFT).get(School_.id)
                        )
                    );
            }
            if (criteria.getWorkersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorkersId(),
                            root -> root.join(CanteenUser_.workers, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
