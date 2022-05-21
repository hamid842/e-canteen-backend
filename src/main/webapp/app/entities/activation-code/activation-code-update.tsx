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
import { IActivationCode } from 'app/shared/model/activation-code.model';
import { getEntity, updateEntity, createEntity, reset } from './activation-code.reducer';

export const ActivationCodeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const activationCodeEntity = useAppSelector(state => state.activationCode.entity);
  const loading = useAppSelector(state => state.activationCode.loading);
  const updating = useAppSelector(state => state.activationCode.updating);
  const updateSuccess = useAppSelector(state => state.activationCode.updateSuccess);
  const handleClose = () => {
    props.history.push('/activation-code' + props.location.search);
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

    const entity = {
      ...activationCodeEntity,
      ...values,
      parentActivationCode: canteenUsers.find(it => it.id.toString() === values.parentActivationCode.toString()),
      workerActivationCode: canteenUsers.find(it => it.id.toString() === values.workerActivationCode.toString()),
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
        }
      : {
          ...activationCodeEntity,
          createdDate: convertDateTimeFromServer(activationCodeEntity.createdDate),
          parentActivationCode: activationCodeEntity?.parentActivationCode?.id,
          workerActivationCode: activationCodeEntity?.workerActivationCode?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.activationCode.home.createOrEditLabel" data-cy="ActivationCodeCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.activationCode.home.createOrEditLabel">Create or edit a ActivationCode</Translate>
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
                  id="activation-code-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.activationCode.code')}
                id="activation-code-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.activationCode.expiryTime')}
                id="activation-code-expiryTime"
                name="expiryTime"
                data-cy="expiryTime"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.activationCode.createdDate')}
                id="activation-code-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.activationCode.createdBy')}
                id="activation-code-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="activation-code-parentActivationCode"
                name="parentActivationCode"
                data-cy="parentActivationCode"
                label={translate('eCanteenApp.activationCode.parentActivationCode')}
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
              <ValidatedField
                id="activation-code-workerActivationCode"
                name="workerActivationCode"
                data-cy="workerActivationCode"
                label={translate('eCanteenApp.activationCode.workerActivationCode')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activation-code" replace color="info">
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

export default ActivationCodeUpdate;
