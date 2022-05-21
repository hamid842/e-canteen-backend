import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NotificationHistory from './notification-history';
import NotificationHistoryDetail from './notification-history-detail';
import NotificationHistoryUpdate from './notification-history-update';
import NotificationHistoryDeleteDialog from './notification-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotificationHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotificationHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotificationHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={NotificationHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NotificationHistoryDeleteDialog} />
  </>
);

export default Routes;
