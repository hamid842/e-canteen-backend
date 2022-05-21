import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActivationCode from './activation-code';
import ActivationCodeDetail from './activation-code-detail';
import ActivationCodeUpdate from './activation-code-update';
import ActivationCodeDeleteDialog from './activation-code-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActivationCodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActivationCodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActivationCodeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActivationCode} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ActivationCodeDeleteDialog} />
  </>
);

export default Routes;
