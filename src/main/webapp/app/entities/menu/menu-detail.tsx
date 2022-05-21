import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './menu.reducer';

export const MenuDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const menuEntity = useAppSelector(state => state.menu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="menuDetailsHeading">
          <Translate contentKey="eCanteenApp.menu.detail.title">Menu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{menuEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eCanteenApp.menu.name">Name</Translate>
            </span>
          </dt>
          <dd>{menuEntity.name}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="eCanteenApp.menu.code">Code</Translate>
            </span>
          </dt>
          <dd>{menuEntity.code}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.menu.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{menuEntity.createdDate ? <TextFormat value={menuEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.menu.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>{menuEntity.modifiedDate ? <TextFormat value={menuEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/menu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/menu/${menuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MenuDetail;
