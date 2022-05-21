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
import { IUserOrder } from 'app/shared/model/user-order.model';
import { getEntity, updateEntity, createEntity, reset } from './user-order.reducer';

export const UserOrderUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const userOrderEntity = useAppSelector(state => state.userOrder.entity);
  const loading = useAppSelector(state => state.userOrder.loading);
  const updating = useAppSelector(state => state.userOrder.updating);
  const updateSuccess = useAppSelector(state => state.userOrder.updateSuccess);
  const handleClose = () => {
    props.history.push('/user-order' + props.location.search);
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
      ...userOrderEntity,
      ...values,
      orders: canteenUsers.find(it => it.id.toString() === values.orders.toString()),
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
          ...userOrderEntity,
          createdDate: convertDateTimeFromServer(userOrderEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(userOrderEntity.modifiedDate),
          orders: userOrderEntity?.orders?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.userOrder.home.createOrEditLabel" data-cy="UserOrderCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.userOrder.home.createOrEditLabel">Create or edit a UserOrder</Translate>
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
                  id="user-order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.userOrder.orderNumber')}
                id="user-order-orderNumber"
                name="orderNumber"
                data-cy="orderNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.userOrder.ordrerCode')}
                id="user-order-ordrerCode"
                name="ordrerCode"
                data-cy="ordrerCode"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.userOrder.createdDate')}
                id="user-order-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.userOrder.modifiedDate')}
                id="user-order-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="user-order-orders"
                name="orders"
                data-cy="orders"
                label={translate('eCanteenApp.userOrder.orders')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-order" replace color="info">
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

export default UserOrderUpdate;
