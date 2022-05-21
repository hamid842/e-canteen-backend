import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transaction.reducer';

export const TransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const transactionEntity = useAppSelector(state => state.transaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDetailsHeading">
          <Translate contentKey="eCanteenApp.transaction.detail.title">Transaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.id}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="eCanteenApp.transaction.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.transactionId}</dd>
          <dt>
            <span id="transactionStatus">
              <Translate contentKey="eCanteenApp.transaction.transactionStatus">Transaction Status</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.transactionStatus}</dd>
          <dt>
            <span id="orderNumber">
              <Translate contentKey="eCanteenApp.transaction.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.orderNumber}</dd>
          <dt>
            <span id="paymentMethod">
              <Translate contentKey="eCanteenApp.transaction.paymentMethod">Payment Method</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.paymentMethod}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.transaction.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {transactionEntity.createdDate ? (
              <TextFormat value={transactionEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.transaction.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {transactionEntity.modifiedDate ? (
              <TextFormat value={transactionEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="eCanteenApp.transaction.transactions">Transactions</Translate>
          </dt>
          <dd>{transactionEntity.transactions ? transactionEntity.transactions.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransactionDetail;
