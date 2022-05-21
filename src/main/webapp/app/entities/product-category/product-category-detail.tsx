import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-category.reducer';

export const ProductCategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productCategoryEntity = useAppSelector(state => state.productCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productCategoryDetailsHeading">
          <Translate contentKey="eCanteenApp.productCategory.detail.title">ProductCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eCanteenApp.productCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.name}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="eCanteenApp.productCategory.code">Code</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.code}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.productCategory.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productCategoryEntity.createdDate ? (
              <TextFormat value={productCategoryEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.productCategory.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {productCategoryEntity.modifiedDate ? (
              <TextFormat value={productCategoryEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="eCanteenApp.productCategory.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.createdBy}</dd>
          <dt>
            <span id="modifiedBy">
              <Translate contentKey="eCanteenApp.productCategory.modifiedBy">Modified By</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.modifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/product-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-category/${productCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductCategoryDetail;
