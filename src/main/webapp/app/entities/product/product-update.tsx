import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProductCategory } from 'app/shared/model/product-category.model';
import { getEntities as getProductCategories } from 'app/entities/product-category/product-category.reducer';
import { IUserOrder } from 'app/shared/model/user-order.model';
import { getEntities as getUserOrders } from 'app/entities/user-order/user-order.reducer';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { getEntities as getCanteenUsers } from 'app/entities/canteen-user/canteen-user.reducer';
import { IMenu } from 'app/shared/model/menu.model';
import { getEntities as getMenus } from 'app/entities/menu/menu.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { Rating } from 'app/shared/model/enumerations/rating.model';
import { Category } from 'app/shared/model/enumerations/category.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productCategories = useAppSelector(state => state.productCategory.entities);
  const userOrders = useAppSelector(state => state.userOrder.entities);
  const canteenUsers = useAppSelector(state => state.canteenUser.entities);
  const menus = useAppSelector(state => state.menu.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const ratingValues = Object.keys(Rating);
  const categoryValues = Object.keys(Category);
  const handleClose = () => {
    props.history.push('/product' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductCategories({}));
    dispatch(getUserOrders({}));
    dispatch(getCanteenUsers({}));
    dispatch(getMenus({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.expiryDate = convertDateTimeToServer(values.expiryDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.modifiedDate = convertDateTimeToServer(values.modifiedDate);

    const entity = {
      ...productEntity,
      ...values,
      everyProductCategory: productCategories.find(it => it.id.toString() === values.everyProductCategory.toString()),
      product: productCategories.find(it => it.id.toString() === values.product.toString()),
      productItem: userOrders.find(it => it.id.toString() === values.productItem.toString()),
      productItemsList: canteenUsers.find(it => it.id.toString() === values.productItemsList.toString()),
      products: menus.find(it => it.id.toString() === values.products.toString()),
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
          expiryDate: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          modifiedDate: displayDefaultDateTime(),
        }
      : {
          grade: 'POOR',
          category: 'PIZA',
          ...productEntity,
          expiryDate: convertDateTimeFromServer(productEntity.expiryDate),
          createdDate: convertDateTimeFromServer(productEntity.createdDate),
          modifiedDate: convertDateTimeFromServer(productEntity.modifiedDate),
          everyProductCategory: productEntity?.everyProductCategory?.id,
          productItem: productEntity?.productItem?.id,
          product: productEntity?.product?.id,
          productItemsList: productEntity?.productItemsList?.id,
          products: productEntity?.products?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCanteenApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            <Translate contentKey="eCanteenApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
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
                  id="product-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eCanteenApp.product.name')} id="product-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('eCanteenApp.product.price')} id="product-price" name="price" data-cy="price" type="text" />
              <ValidatedField
                label={translate('eCanteenApp.product.barcode')}
                id="product-barcode"
                name="barcode"
                data-cy="barcode"
                type="text"
              />
              <ValidatedField label={translate('eCanteenApp.product.grade')} id="product-grade" name="grade" data-cy="grade" type="select">
                {ratingValues.map(rating => (
                  <option value={rating} key={rating}>
                    {translate('eCanteenApp.Rating.' + rating)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.product.category')}
                id="product-category"
                name="category"
                data-cy="category"
                type="select"
              >
                {categoryValues.map(category => (
                  <option value={category} key={category}>
                    {translate('eCanteenApp.Category.' + category)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCanteenApp.product.imageUrl')}
                id="product-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.product.expiryDate')}
                id="product-expiryDate"
                name="expiryDate"
                data-cy="expiryDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.product.createdDate')}
                id="product-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.product.modifiedDate')}
                id="product-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCanteenApp.product.createdBy')}
                id="product-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('eCanteenApp.product.modifiedBy')}
                id="product-modifiedBy"
                name="modifiedBy"
                data-cy="modifiedBy"
                type="text"
              />
              <ValidatedField
                id="product-everyProductCategory"
                name="everyProductCategory"
                data-cy="everyProductCategory"
                label={translate('eCanteenApp.product.everyProductCategory')}
                type="select"
              >
                <option value="" key="0" />
                {productCategories
                  ? productCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-productItem"
                name="productItem"
                data-cy="productItem"
                label={translate('eCanteenApp.product.productItem')}
                type="select"
              >
                <option value="" key="0" />
                {userOrders
                  ? userOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-product"
                name="product"
                data-cy="product"
                label={translate('eCanteenApp.product.product')}
                type="select"
              >
                <option value="" key="0" />
                {productCategories
                  ? productCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="product-productItemsList"
                name="productItemsList"
                data-cy="productItemsList"
                label={translate('eCanteenApp.product.productItemsList')}
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
                id="product-products"
                name="products"
                data-cy="products"
                label={translate('eCanteenApp.product.products')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
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

export default ProductUpdate;
