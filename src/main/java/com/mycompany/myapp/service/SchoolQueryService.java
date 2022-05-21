package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.repository.SchoolRepository;
import com.mycompany.myapp.service.criteria.SchoolCriteria;
import com.mycompany.myapp.service.dto.SchoolDTO;
import com.mycompany.myapp.service.mapper.SchoolMapper;
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
 * Service for executing complex queries for {@link School} entities in the database.
 * The main input is a {@link SchoolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchoolDTO} or a {@link Page} of {@link SchoolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchoolQueryService extends QueryService<School> {

    private final Logger log = LoggerFactory.getLogger(SchoolQueryService.class);

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    public SchoolQueryService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    /**
     * Return a {@link List} of {@link SchoolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchoolDTO> findByCriteria(SchoolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<School> specification = createSpecification(criteria);
        return schoolMapper.toDto(schoolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchoolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchoolDTO> findByCriteria(SchoolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<School> specification = createSpecification(criteria);
        return schoolRepository.findAll(specification, page).map(schoolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchoolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<School> specification = createSpecification(criteria);
        return schoolRepository.count(specification);
    }

    /**
     * Function to convert {@link SchoolCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<School> createSpecification(SchoolCriteria criteria) {
        Specification<School> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), School_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), School_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), School_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), School_.phoneNumber));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), School_.address));
            }
            if (criteria.getKkUseId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKkUseId(), School_.kkUseId));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), School_.createdDate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), School_.modifiedDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), School_.createdBy));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), School_.modifiedBy));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), School_.role));
            }
            if (criteria.getEverySchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEverySchoolId(),
                            root -> root.join(School_.everySchool, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getStudentSchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStudentSchoolId(),
                            root -> root.join(School_.studentSchools, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getEachSchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEachSchoolId(),
                            root -> root.join(School_.eachSchools, JoinType.LEFT).get(CanteenUser_.id)
                        )
                    );
            }
            if (criteria.getSchoolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSchoolId(), root -> root.join(School_.school, JoinType.LEFT).get(CanteenUser_.id))
                    );
            }
            if (criteria.getSchoolsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSchoolsId(), root -> root.join(School_.schools, JoinType.LEFT).get(CanteenUser_.id))
                    );
            }
        }
        return specification;
    }
}
