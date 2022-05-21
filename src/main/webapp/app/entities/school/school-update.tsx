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
import { ISchool } from 'app/shared/model/school.model';
import { ROLE } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './school.reducer';

export const SchoolUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const schoolEntity = useAppSelector(state => state.school.entity);
  const loading = useAppSelector(state => state.school.loading);
  const updating = useAppSelector(state => state.school.updating);
  const updateSuccess = useAppSelector(state => state.school.updateSuccess);
  const rOLEValues = Object.keys(ROLE);
  const handleClose = () => {
    props.history.push('/school' + props.location.search);
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
      ...schoolEntity,
      ...values,
      everySchool: canteenUsers.find(it => it.id.toString() === values.everySchool.toString()),
      school: canteenUsers.find(it => it.id.toString() === values.school.toString()),
      schools: canteenUsers.find(it => it.id.toString() === values.schools.toString()),
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
          ...schoolEntity,
          createdDate: convertDateTimeFromServer(schoolEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(schoolEntity.modifiedDate),
          everySchool: schoolEntity?.everySchool?.id,
          school: schoolEntity?.school?.id,
          schools: schoolEntity?.schools?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.school.home.createOrEditLabel" data-cy="SchoolCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.school.home.createOrEditLabel">Create or edit a School</Translate>
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
                  id="school-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eCanteenApp.school.name')} id="school-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('eCanteenApp.school.email')} id="school-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('eCanteenApp.school.phoneNumber')}
                id="school-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.address')}
                id="school-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.kkUseId')}
                id="school-kkUseId"
                name="kkUseId"
                data-cy="kkUseId"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.createdDate')}
                id="school-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.modifiedDate')}
                id="school-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.createdBy')}
                id="school-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.school.modifiedBy')}
                id="school-modifiedBy"
                name="modifiedBy"
                data-cy="modifiedBy"
                type="text"
              />
              <ValidatedField label={translate('eCanteenApp.school.role')} id="school-role" name="role" data-cy="role" type="select">
                {rOLEValues.map(rOLE => (
                  <option value={rOLE} key={rOLE}>
                    {translate('eCanteenApp.ROLE.' + rOLE)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="school-everySchool"
                name="everySchool"
                data-cy="everySchool"
                label={translate('eCanteenApp.school.everySchool')}
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
                id="school-school"
                name="school"
                data-cy="school"
                label={translate('eCanteenApp.school.school')}
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
                id="school-schools"
                name="schools"
                data-cy="schools"
                label={translate('eCanteenApp.school.schools')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/school" replace color="info">
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

export default SchoolUpdate;
