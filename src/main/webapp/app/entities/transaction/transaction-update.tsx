import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { getEntities as getCanteenUsers } from 'app/entities/canteen-user/canteen-user.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';

export const TransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const transactionEntity = useAppSelector(state => state.transaction.entity);
  const loading = useAppSelector(state => state.transaction.loading);
  const updating = useAppSelector(state => state.transaction.updating);
  const updateSuccess = useAppSelector(state => state.transaction.updateSuccess);
  const transactionStatusValues = Object.keys(TransactionStatus);
  const paymentMethodValues = Object.keys(PaymentMethod);
  const handleClose = () => {
    props.history.push('/transaction' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCanteenUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.modifiedDate = convertDateTimeToServer(values.modifiedDate);

    const entity = {
      ...transactionEntity,
      ...values,
      transactions: canteenUsers.find(it => it.id.toString() === values.transactions.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          modifiedDate: displayDefaultDateTime(),
        }
      : {
          transactionStatus: 'SUCCESSFUL',
          paymentMethod: 'CARD',
          ...transactionEntity,
          createdDate: convertDateTimeFromServer(transactionEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(transactionEntity.modifiedDate),
          transactions: transactionEntity?.transactions?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.transaction.home.createOrEditLabel" data-cy="TransactionCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.transaction.home.createOrEditLabel">Create or edit a Transaction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.transaction.transactionId')}
                id="transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.transaction.transactionStatus')}
                id="transaction-transactionStatus"
                name="transactionStatus"
                data-cy="transactionStatus"
                type="select"
              >
                {transactionStatusValues.map(transactionStatus => (
                  <option value={transactionStatus} key={transactionStatus}>
                    {translate('eCanteenApp.TransactionStatus.' + transactionStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.transaction.orderNumber')}
                id="transaction-orderNumber"
                name="orderNumber"
                data-cy="orderNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.transaction.paymentMethod')}
                id="transaction-paymentMethod"
                name="paymentMethod"
                data-cy="paymentMethod"
                type="select"
              >
                {paymentMethodValues.map(paymentMethod => (
                  <option value={paymentMethod} key={paymentMethod}>
                    {translate('eCanteenApp.PaymentMethod.' + paymentMethod)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.transaction.createdDate')}
                id="transaction-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.transaction.modifiedDate')}
                id="transaction-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="transaction-transactions"
                name="transactions"
                data-cy="transactions"
                label={translate('eCanteenApp.transaction.transactions')}
                type="select"
              >
                <option value="" key="0" />
                {canteenUsers
                  ? canteenUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transaction" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransactionUpdate;
