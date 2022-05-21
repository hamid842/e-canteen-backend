import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMenu } from 'app/shared/model/menu.model';
import { getEntities as getMenus } from 'app/entities/menu/menu.reducer';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { getEntities as getUserAccounts } from 'app/entities/user-account/user-account.reducer';
import { getEntities as getCanteenUsers } from 'app/entities/canteen-user/canteen-user.reducer';
import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { ROLE } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './canteen-user.reducer';

export const CanteenUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const menus = useAppSelector(state => state.menu.entities);
  const userAccounts = useAppSelector(state => state.userAccount.entities);
  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const schools = useAppSelector(state => state.school.entities);
  const canteenUserEntity = useAppSelector(state => state.canteenUser.entity);
  const loading = useAppSelector(state => state.canteenUser.loading);
  const updating = useAppSelector(state => state.canteenUser.updating);
  const updateSuccess = useAppSelector(state => state.canteenUser.updateSuccess);
  const rOLEValues = Object.keys(ROLE);
  const handleClose = () => {
    props.history.push('/canteen-user' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMenus({}));
    dispatch(getUserAccounts({}));
    dispatch(getCanteenUsers({}));
    dispatch(getSchools({}));
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
      ...canteenUserEntity,
      ...values,
      everyWorkerAtSchool: menus.find(it => it.id.toString() === values.everyWorkerAtSchool.toString()),
      everyParent: userAccounts.find(it => it.id.toString() === values.everyParent.toString()),
      children: canteenUsers.find(it => it.id.toString() === values.children.toString()),
      workers: canteenUsers.find(it => it.id.toString() === values.workers.toString()),
      parents: schools.find(it => it.id.toString() === values.parents.toString()),
      students: schools.find(it => it.id.toString() === values.students.toString()),
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
          role: 'ROLE_SCHOOL',
          ...canteenUserEntity,
          createdDate: convertDateTimeFromServer(canteenUserEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(canteenUserEntity.modifiedDate),
          everyWorkerAtSchool: canteenUserEntity?.everyWorkerAtSchool?.id,
          everyParent: canteenUserEntity?.everyParent?.id,
          children: canteenUserEntity?.children?.id,
          parents: canteenUserEntity?.parents?.id,
          students: canteenUserEntity?.students?.id,
          workers: canteenUserEntity?.workers?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.canteenUser.home.createOrEditLabel" data-cy="CanteenUserCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.canteenUser.home.createOrEditLabel">Create or edit a CanteenUser</Translate>
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
                  id="canteen-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.fullName')}
                id="canteen-user-fullName"
                name="fullName"
                data-cy="fullName"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.email')}
                id="canteen-user-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.phoneNumber')}
                id="canteen-user-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.address')}
                id="canteen-user-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.imageUrl')}
                id="canteen-user-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.isEnabled')}
                id="canteen-user-isEnabled"
                name="isEnabled"
                data-cy="isEnabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.phoneVerified')}
                id="canteen-user-phoneVerified"
                name="phoneVerified"
                data-cy="phoneVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.emailVerified')}
                id="canteen-user-emailVerified"
                name="emailVerified"
                data-cy="emailVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.kkUseId')}
                id="canteen-user-kkUseId"
                name="kkUseId"
                data-cy="kkUseId"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.role')}
                id="canteen-user-role"
                name="role"
                data-cy="role"
                type="select"
              >
                {rOLEValues.map(rOLE => (
                  <option value={rOLE} key={rOLE}>
                    {translate('eCanteenApp.ROLE.' + rOLE)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.createdDate')}
                id="canteen-user-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.modifiedDate')}
                id="canteen-user-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.createdBy')}
                id="canteen-user-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.canteenUser.modifiedBy')}
                id="canteen-user-modifiedBy"
                name="modifiedBy"
                data-cy="modifiedBy"
                type="text"
              />
              <ValidatedField
                id="canteen-user-everyWorkerAtSchool"
                name="everyWorkerAtSchool"
                data-cy="everyWorkerAtSchool"
                label={translate('eCanteenApp.canteenUser.everyWorkerAtSchool')}
                type="select"
              >
                <option value="" key="0" />
                {menus
                  ? menus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="canteen-user-everyParent"
                name="everyParent"
                data-cy="everyParent"
                label={translate('eCanteenApp.canteenUser.everyParent')}
                type="select"
              >
                <option value="" key="0" />
                {userAccounts
                  ? userAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="canteen-user-children"
                name="children"
                data-cy="children"
                label={translate('eCanteenApp.canteenUser.children')}
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
                id="canteen-user-parents"
                name="parents"
                data-cy="parents"
                label={translate('eCanteenApp.canteenUser.parents')}
                type="select"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="canteen-user-students"
                name="students"
                data-cy="students"
                label={translate('eCanteenApp.canteenUser.students')}
                type="select"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="canteen-user-workers"
                name="workers"
                data-cy="workers"
                label={translate('eCanteenApp.canteenUser.workers')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/canteen-user" replace color="info">
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

export default CanteenUserUpdate;
