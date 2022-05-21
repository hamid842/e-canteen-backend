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
import { INotificationHistory } from 'app/shared/model/notification-history.model';
import { NotificationStatus } from 'app/shared/model/enumerations/notification-status.model';
import { NotificationMethod } from 'app/shared/model/enumerations/notification-method.model';
import { getEntity, updateEntity, createEntity, reset } from './notification-history.reducer';

export const NotificationHistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const notificationHistoryEntity = useAppSelector(state => state.notificationHistory.entity);
  const loading = useAppSelector(state => state.notificationHistory.loading);
  const updating = useAppSelector(state => state.notificationHistory.updating);
  const updateSuccess = useAppSelector(state => state.notificationHistory.updateSuccess);
  const notificationStatusValues = Object.keys(NotificationStatus);
  const notificationMethodValues = Object.keys(NotificationMethod);
  const handleClose = () => {
    props.history.push('/notification-history' + props.location.search);
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
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...notificationHistoryEntity,
      ...values,
      parentNotificationHistory: canteenUsers.find(it => it.id.toString() === values.parentNotificationHistory.toString()),
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
          date: displayDefaultDateTime(),
        }
      : {
          status: 'SENT',
          method: 'EMAIL',
          ...notificationHistoryEntity,
          date: convertDateTimeFromServer(notificationHistoryEntity.date),
          parentNotificationHistory: notificationHistoryEntity?.parentNotificationHistory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.notificationHistory.home.createOrEditLabel" data-cy="NotificationHistoryCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.notificationHistory.home.createOrEditLabel">Create or edit a NotificationHistory</Translate>
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
                  id="notification-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCanteenApp.notificationHistory.date')}
                id="notification-history-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.notificationHistory.status')}
                id="notification-history-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {notificationStatusValues.map(notificationStatus => (
                  <option value={notificationStatus} key={notificationStatus}>
                    {translate('eCanteenApp.NotificationStatus.' + notificationStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.notificationHistory.method')}
                id="notification-history-method"
                name="method"
                data-cy="method"
                type="select"
              >
                {notificationMethodValues.map(notificationMethod => (
                  <option value={notificationMethod} key={notificationMethod}>
                    {translate('eCanteenApp.NotificationMethod.' + notificationMethod)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="notification-history-parentNotificationHistory"
                name="parentNotificationHistory"
                data-cy="parentNotificationHistory"
                label={translate('eCanteenApp.notificationHistory.parentNotificationHistory')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification-history" replace color="info">
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

export default NotificationHistoryUpdate;
