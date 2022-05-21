import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-order.reducer';

export const UserOrderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userOrderEntity = useAppSelector(state => state.userOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userOrderDetailsHeading">
          <Translate contentKey="eCanteenApp.userOrder.detail.title">UserOrder</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userOrderEntity.id}</dd>
          <dt>
            <span id="orderNumber">
              <Translate contentKey="eCanteenApp.userOrder.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{userOrderEntity.orderNumber}</dd>
          <dt>
            <span id="ordrerCode">
              <Translate contentKey="eCanteenApp.userOrder.ordrerCode">Ordrer Code</Translate>
            </span>
          </dt>
          <dd>{userOrderEntity.ordrerCode}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.userOrder.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {userOrderEntity.createdDate ? <TextFormat value={userOrderEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.userOrder.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {userOrderEntity.modifiedDate ? <TextFormat value={userOrderEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="eCanteenApp.userOrder.orders">Orders</Translate>
          </dt>
          <dd>{userOrderEntity.orders ? userOrderEntity.orders.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-order/${userOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserOrderDetail;
