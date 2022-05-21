import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { getEntities } from './canteen-user.reducer';

export const CanteenUser = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const canteenUserList = useAppSelector(state => state.canteenUser.entities);
  const loading = useAppSelector(state => state.canteenUser.loading);
  const totalItems = useAppSelector(state => state.canteenUser.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="canteen-user-heading" data-cy="CanteenUserHeading">
        <Translate contentKey="eCanteenApp.canteenUser.home.title">Canteen Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCanteenApp.canteenUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/canteen-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCanteenApp.canteenUser.home.createLabel">Create new Canteen User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {canteenUserList && canteenUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCanteenApp.canteenUser.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullName')}>
                  <Translate contentKey="eCanteenApp.canteenUser.fullName">Full Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="eCanteenApp.canteenUser.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phoneNumber')}>
                  <Translate contentKey="eCanteenApp.canteenUser.phoneNumber">Phone Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="eCanteenApp.canteenUser.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageUrl')}>
                  <Translate contentKey="eCanteenApp.canteenUser.imageUrl">Image Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnabled')}>
                  <Translate contentKey="eCanteenApp.canteenUser.isEnabled">Is Enabled</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phoneVerified')}>
                  <Translate contentKey="eCanteenApp.canteenUser.phoneVerified">Phone Verified</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emailVerified')}>
                  <Translate contentKey="eCanteenApp.canteenUser.emailVerified">Email Verified</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kkUseId')}>
                  <Translate contentKey="eCanteenApp.canteenUser.kkUseId">Kk Use Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('role')}>
                  <Translate contentKey="eCanteenApp.canteenUser.role">Role</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="eCanteenApp.canteenUser.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiedDate')}>
                  <Translate contentKey="eCanteenApp.canteenUser.modifiedDate">Modified Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="eCanteenApp.canteenUser.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiedBy')}>
                  <Translate contentKey="eCanteenApp.canteenUser.modifiedBy">Modified By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.everyWorkerAtSchool">Every Worker At School</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.everyParent">Every Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.children">Children</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.parents">Parents</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.students">Students</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.canteenUser.workers">Workers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {canteenUserList.map((canteenUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/canteen-user/${canteenUser.id}`} color="link" size="sm">
                      {canteenUser.id}
                    </Button>
                  </td>
                  <td>{canteenUser.fullName}</td>
                  <td>{canteenUser.email}</td>
                  <td>{canteenUser.phoneNumber}</td>
                  <td>{canteenUser.address}</td>
                  <td>{canteenUser.imageUrl}</td>
                  <td>{canteenUser.isEnabled ? 'true' : 'false'}</td>
                  <td>{canteenUser.phoneVerified ? 'true' : 'false'}</td>
                  <td>{canteenUser.emailVerified ? 'true' : 'false'}</td>
                  <td>{canteenUser.kkUseId}</td>
                  <td>
                    <Translate contentKey={`eCanteenApp.ROLE.${canteenUser.role}`} />
                  </td>
                  <td>
                    {canteenUser.createdDate ? <TextFormat type="date" value={canteenUser.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {canteenUser.modifiedDate ? <TextFormat type="date" value={canteenUser.modifiedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{canteenUser.createdBy}</td>
                  <td>{canteenUser.modifiedBy}</td>
                  <td>
                    {canteenUser.everyWorkerAtSchool ? (
                      <Link to={`/menu/${canteenUser.everyWorkerAtSchool.id}`}>{canteenUser.everyWorkerAtSchool.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {canteenUser.everyParent ? (
                      <Link to={`/user-account/${canteenUser.everyParent.id}`}>{canteenUser.everyParent.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {canteenUser.children ? <Link to={`/canteen-user/${canteenUser.children.id}`}>{canteenUser.children.id}</Link> : ''}
                  </td>
                  <td>{canteenUser.parents ? <Link to={`/school/${canteenUser.parents.id}`}>{canteenUser.parents.id}</Link> : ''}</td>
                  <td>{canteenUser.students ? <Link to={`/school/${canteenUser.students.id}`}>{canteenUser.students.id}</Link> : ''}</td>
                  <td>{canteenUser.workers ? <Link to={`/canteen-user/${canteenUser.workers.id}`}>{canteenUser.workers.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/canteen-user/${canteenUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/canteen-user/${canteenUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/canteen-user/${canteenUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eCanteenApp.canteenUser.home.notFound">No Canteen Users found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={canteenUserList && canteenUserList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default CanteenUser;
