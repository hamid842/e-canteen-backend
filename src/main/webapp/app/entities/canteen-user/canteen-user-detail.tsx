import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './canteen-user.reducer';

export const CanteenUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const canteenUserEntity = useAppSelector(state => state.canteenUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="canteenUserDetailsHeading">
          <Translate contentKey="eCanteenApp.canteenUser.detail.title">CanteenUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.id}</dd>
          <dt>
            <span id="fullName">
              <Translate contentKey="eCanteenApp.canteenUser.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.fullName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="eCanteenApp.canteenUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="eCanteenApp.canteenUser.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.phoneNumber}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="eCanteenApp.canteenUser.address">Address</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.address}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="eCanteenApp.canteenUser.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.imageUrl}</dd>
          <dt>
            <span id="isEnabled">
              <Translate contentKey="eCanteenApp.canteenUser.isEnabled">Is Enabled</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.isEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="phoneVerified">
              <Translate contentKey="eCanteenApp.canteenUser.phoneVerified">Phone Verified</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.phoneVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="emailVerified">
              <Translate contentKey="eCanteenApp.canteenUser.emailVerified">Email Verified</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.emailVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="kkUseId">
              <Translate contentKey="eCanteenApp.canteenUser.kkUseId">Kk Use Id</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.kkUseId}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="eCanteenApp.canteenUser.role">Role</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.role}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.canteenUser.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {canteenUserEntity.createdDate ? (
              <TextFormat value={canteenUserEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.canteenUser.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {canteenUserEntity.modifiedDate ? (
              <TextFormat value={canteenUserEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="eCanteenApp.canteenUser.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.createdBy}</dd>
          <dt>
            <span id="modifiedBy">
              <Translate contentKey="eCanteenApp.canteenUser.modifiedBy">Modified By</Translate>
            </span>
          </dt>
          <dd>{canteenUserEntity.modifiedBy}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.everyWorkerAtSchool">Every Worker At School</Translate>
          </dt>
          <dd>{canteenUserEntity.everyWorkerAtSchool ? canteenUserEntity.everyWorkerAtSchool.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.everyParent">Every Parent</Translate>
          </dt>
          <dd>{canteenUserEntity.everyParent ? canteenUserEntity.everyParent.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.children">Children</Translate>
          </dt>
          <dd>{canteenUserEntity.children ? canteenUserEntity.children.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.parents">Parents</Translate>
          </dt>
          <dd>{canteenUserEntity.parents ? canteenUserEntity.parents.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.students">Students</Translate>
          </dt>
          <dd>{canteenUserEntity.students ? canteenUserEntity.students.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.canteenUser.workers">Workers</Translate>
          </dt>
          <dd>{canteenUserEntity.workers ? canteenUserEntity.workers.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/canteen-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/canteen-user/${canteenUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CanteenUserDetail;
