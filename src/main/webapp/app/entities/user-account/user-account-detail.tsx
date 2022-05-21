import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-account.reducer';

export const UserAccountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userAccountEntity = useAppSelector(state => state.userAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAccountDetailsHeading">
          <Translate contentKey="eCanteenApp.userAccount.detail.title">UserAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userAccountEntity.id}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="eCanteenApp.userAccount.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{userAccountEntity.accountNumber}</dd>
          <dt>
            <span id="accountName">
              <Translate contentKey="eCanteenApp.userAccount.accountName">Account Name</Translate>
            </span>
          </dt>
          <dd>{userAccountEntity.accountName}</dd>
          <dt>
            <span id="walletBalance">
              <Translate contentKey="eCanteenApp.userAccount.walletBalance">Wallet Balance</Translate>
            </span>
          </dt>
          <dd>{userAccountEntity.walletBalance}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.userAccount.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {userAccountEntity.createdDate ? (
              <TextFormat value={userAccountEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.userAccount.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {userAccountEntity.modifiedDate ? (
              <TextFormat value={userAccountEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-account/${userAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAccountDetail;
