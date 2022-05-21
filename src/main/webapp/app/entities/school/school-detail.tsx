import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './school.reducer';

export const SchoolDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const schoolEntity = useAppSelector(state => state.school.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schoolDetailsHeading">
          <Translate contentKey="eCanteenApp.school.detail.title">School</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eCanteenApp.school.name">Name</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="eCanteenApp.school.email">Email</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="eCanteenApp.school.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.phoneNumber}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="eCanteenApp.school.address">Address</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.address}</dd>
          <dt>
            <span id="kkUseId">
              <Translate contentKey="eCanteenApp.school.kkUseId">Kk Use Id</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.kkUseId}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.school.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.createdDate ? <TextFormat value={schoolEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.school.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {schoolEntity.modifiedDate ? <TextFormat value={schoolEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="eCanteenApp.school.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.createdBy}</dd>
          <dt>
            <span id="modifiedBy">
              <Translate contentKey="eCanteenApp.school.modifiedBy">Modified By</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.modifiedBy}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="eCanteenApp.school.role">Role</Translate>
            </span>
          </dt>
          <dd>{schoolEntity.role}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.school.everySchool">Every School</Translate>
          </dt>
          <dd>{schoolEntity.everySchool ? schoolEntity.everySchool.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.school.school">School</Translate>
          </dt>
          <dd>{schoolEntity.school ? schoolEntity.school.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.school.schools">Schools</Translate>
          </dt>
          <dd>{schoolEntity.schools ? schoolEntity.schools.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/school" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/school/${schoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchoolDetail;
