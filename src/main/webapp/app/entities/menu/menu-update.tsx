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
import { IMenu } from 'app/shared/model/menu.model';
import { getEntity, updateEntity, createEntity, reset } from './menu.reducer';

export const MenuUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const menuEntity = useAppSelector(state => state.menu.entity);
  const loading = useAppSelector(state => state.menu.loading);
  const updating = useAppSelector(state => state.menu.updating);
  const updateSuccess = useAppSelector(state => state.menu.updateSuccess);
  const handleClose = () => {
    props.history.push('/menu' + props.location.search);
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
      ...menuEntity,
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
          ...menuEntity,
          createdDate: convertDateTimeFromServer(menuEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(menuEntity.modifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.menu.home.createOrEditLabel">Create or edit a Menu</Translate>
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
                  id="menu-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eCanteenApp.menu.name')} id="menu-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('eCanteenApp.menu.code')} id="menu-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label={translate('eCanteenApp.menu.createdDate')}
                id="menu-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.menu.modifiedDate')}
                id="menu-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/menu" replace color="info">
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

export default MenuUpdate;
