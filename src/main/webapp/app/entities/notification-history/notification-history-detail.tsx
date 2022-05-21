import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification-history.reducer';

export const NotificationHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const notificationHistoryEntity = useAppSelector(state => state.notificationHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationHistoryDetailsHeading">
          <Translate contentKey="eCanteenApp.notificationHistory.detail.title">NotificationHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notificationHistoryEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="eCanteenApp.notificationHistory.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {notificationHistoryEntity.date ? (
              <TextFormat value={notificationHistoryEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="eCanteenApp.notificationHistory.status">Status</Translate>
            </span>
          </dt>
          <dd>{notificationHistoryEntity.status}</dd>
          <dt>
            <span id="method">
              <Translate contentKey="eCanteenApp.notificationHistory.method">Method</Translate>
            </span>
          </dt>
          <dd>{notificationHistoryEntity.method}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.notificationHistory.parentNotificationHistory">Parent Notification History</Translate>
          </dt>
          <dd>{notificationHistoryEntity.parentNotificationHistory ? notificationHistoryEntity.parentNotificationHistory.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification-history/${notificationHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationHistoryDetail;
