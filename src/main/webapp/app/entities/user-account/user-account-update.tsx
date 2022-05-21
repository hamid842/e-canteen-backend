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
import { IUserAccount } from 'app/shared/model/user-account.model';
import { getEntity, updateEntity, createEntity, reset } from './user-account.reducer';

export const UserAccountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const userAccountEntity = useAppSelector(state => state.userAccount.entity);
  const loading = useAppSelector(state => state.userAccount.loading);
  const updating = useAppSelector(state => state.userAccount.updating);
  const updateSuccess = useAppSelector(state => state.userAccount.updateSuccess);
  const handleClose = () => {
    props.history.push('/user-account' + props.location.search);
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
      ...userAccountEntity,
      ...values,
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
          ...userAccountEntity,
          createdDate: convertDateTimeFromServer(userAccountEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(userAccountEntity.modifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.userAccount.home.createOrEditLabel" data-cy="UserAccountCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.userAccount.home.createOrEditLabel">Create or edit a UserAccount</Translate>
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
                  id="user-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.userAccount.accountNumber')}
                id="user-account-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.userAccount.accountName')}
                id="user-account-accountName"
                name="accountName"
                data-cy="accountName"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.userAccount.walletBalance')}
                id="user-account-walletBalance"
                name="walletBalance"
                data-cy="walletBalance"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.userAccount.createdDate')}
                id="user-account-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.userAccount.modifiedDate')}
                id="user-account-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-account" replace color="info">
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

export default UserAccountUpdate;
