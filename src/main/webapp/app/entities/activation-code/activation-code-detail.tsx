import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activation-code.reducer';

export const ActivationCodeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const activationCodeEntity = useAppSelector(state => state.activationCode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activationCodeDetailsHeading">
          <Translate contentKey="eCanteenApp.activationCode.detail.title">ActivationCode</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{activationCodeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="eCanteenApp.activationCode.code">Code</Translate>
            </span>
          </dt>
          <dd>{activationCodeEntity.code}</dd>
          <dt>
            <span id="expiryTime">
              <Translate contentKey="eCanteenApp.activationCode.expiryTime">Expiry Time</Translate>
            </span>
          </dt>
          <dd>{activationCodeEntity.expiryTime}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.activationCode.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {activationCodeEntity.createdDate ? (
              <TextFormat value={activationCodeEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="eCanteenApp.activationCode.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{activationCodeEntity.createdBy}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.activationCode.parentActivationCode">Parent Activation Code</Translate>
          </dt>
          <dd>{activationCodeEntity.parentActivationCode ? activationCodeEntity.parentActivationCode.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.activationCode.workerActivationCode">Worker Activation Code</Translate>
          </dt>
          <dd>{activationCodeEntity.workerActivationCode ? activationCodeEntity.workerActivationCode.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/activation-code" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activation-code/${activationCodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivationCodeDetail;
