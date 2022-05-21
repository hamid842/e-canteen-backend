import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CanteenUser from './canteen-user';
import CanteenUserDetail from './canteen-user-detail';
import CanteenUserUpdate from './canteen-user-update';
import CanteenUserDeleteDialog from './canteen-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CanteenUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CanteenUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CanteenUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={CanteenUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CanteenUserDeleteDialog} />
  </>
);

export default Routes;
