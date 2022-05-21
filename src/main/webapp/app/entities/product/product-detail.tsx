import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="eCanteenApp.product.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eCanteenApp.product.name">Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="eCanteenApp.product.price">Price</Translate>
            </span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="barcode">
              <Translate contentKey="eCanteenApp.product.barcode">Barcode</Translate>
            </span>
          </dt>
          <dd>{productEntity.barcode}</dd>
          <dt>
            <span id="grade">
              <Translate contentKey="eCanteenApp.product.grade">Grade</Translate>
            </span>
          </dt>
          <dd>{productEntity.grade}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="eCanteenApp.product.category">Category</Translate>
            </span>
          </dt>
          <dd>{productEntity.category}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="eCanteenApp.product.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{productEntity.imageUrl}</dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="eCanteenApp.product.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>{productEntity.expiryDate ? <TextFormat value={productEntity.expiryDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="eCanteenApp.product.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.createdDate ? <TextFormat value={productEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiedDate">
              <Translate contentKey="eCanteenApp.product.modifiedDate">Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.modifiedDate ? <TextFormat value={productEntity.modifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="eCanteenApp.product.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{productEntity.createdBy}</dd>
          <dt>
            <span id="modifiedBy">
              <Translate contentKey="eCanteenApp.product.modifiedBy">Modified By</Translate>
            </span>
          </dt>
          <dd>{productEntity.modifiedBy}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.product.everyProductCategory">Every Product Category</Translate>
          </dt>
          <dd>{productEntity.everyProductCategory ? productEntity.everyProductCategory.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.product.productItem">Product Item</Translate>
          </dt>
          <dd>{productEntity.productItem ? productEntity.productItem.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.product.product">Product</Translate>
          </dt>
          <dd>{productEntity.product ? productEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.product.productItemsList">Product Items List</Translate>
          </dt>
          <dd>{productEntity.productItemsList ? productEntity.productItemsList.id : ''}</dd>
          <dt>
            <Translate contentKey="eCanteenApp.product.products">Products</Translate>
          </dt>
          <dd>{productEntity.products ? productEntity.products.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
