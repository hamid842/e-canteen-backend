import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CanteenUser from './canteen-user';
import School from './school';
import ProductCategory from './product-category';
import Product from './product';
import Menu from './menu';
import NotificationHistory from './notification-history';
import ActivationCode from './activation-code';
import UserAccount from './user-account';
import Transaction from './transaction';
import UserOrder from './user-order';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}canteen-user`} component={CanteenUser} />
        <ErrorBoundaryRoute path={`${match.url}school`} component={School} />
        <ErrorBoundaryRoute path={`${match.url}product-category`} component={ProductCategory} />
        <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
        <ErrorBoundaryRoute path={`${match.url}menu`} component={Menu} />
        <ErrorBoundaryRoute path={`${match.url}notification-history`} component={NotificationHistory} />
        <ErrorBoundaryRoute path={`${match.url}activation-code`} component={ActivationCode} />
        <ErrorBoundaryRoute path={`${match.url}user-account`} component={UserAccount} />
        <ErrorBoundaryRoute path={`${match.url}transaction`} component={Transaction} />
        <ErrorBoundaryRoute path={`${match.url}user-order`} component={UserOrder} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
