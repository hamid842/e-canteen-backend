import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserOrder from './user-order';
import UserOrderDetail from './user-order-detail';
import UserOrderUpdate from './user-order-update';
import UserOrderDeleteDialog from './user-order-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserOrderDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserOrder} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserOrderDeleteDialog} />
  </>
);

export default Routes;
