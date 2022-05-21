import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransaction } from 'app/shared/model/transaction.model';
import { getEntities } from './transaction.reducer';

export const Transaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const transactionList = useAppSelector(state => state.transaction.entities);
  const loading = useAppSelector(state => state.transaction.loading);
  const totalItems = useAppSelector(state => state.transaction.totalItems);

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
      <h2 id="transaction-heading" data-cy="TransactionHeading">
        <Translate contentKey="eCanteenApp.transaction.home.title">Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCanteenApp.transaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/transaction/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCanteenApp.transaction.home.createLabel">Create new Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {transactionList && transactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCanteenApp.transaction.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('transactionId')}>
                  <Translate contentKey="eCanteenApp.transaction.transactionId">Transaction Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('transactionStatus')}>
                  <Translate contentKey="eCanteenApp.transaction.transactionStatus">Transaction Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderNumber')}>
                  <Translate contentKey="eCanteenApp.transaction.orderNumber">Order Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentMethod')}>
                  <Translate contentKey="eCanteenApp.transaction.paymentMethod">Payment Method</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="eCanteenApp.transaction.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiedDate')}>
                  <Translate contentKey="eCanteenApp.transaction.modifiedDate">Modified Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCanteenApp.transaction.transactions">Transactions</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transactionList.map((transaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/transaction/${transaction.id}`} color="link" size="sm">
                      {transaction.id}
                    </Button>
                  </td>
                  <td>{transaction.transactionId}</td>
                  <td>
                    <Translate contentKey={`eCanteenApp.TransactionStatus.${transaction.transactionStatus}`} />
                  </td>
                  <td>{transaction.orderNumber}</td>
                  <td>
                    <Translate contentKey={`eCanteenApp.PaymentMethod.${transaction.paymentMethod}`} />
                  </td>
                  <td>
                    {transaction.createdDate ? <TextFormat type="date" value={transaction.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {transaction.modifiedDate ? <TextFormat type="date" value={transaction.modifiedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {transaction.transactions ? (
                      <Link to={`/canteen-user/${transaction.transactions.id}`}>{transaction.transactions.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/transaction/${transaction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/transaction/${transaction.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/transaction/${transaction.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="eCanteenApp.transaction.home.notFound">No Transactions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={transactionList && transactionList.length > 0 ? '' : 'd-none'}>
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

export default Transaction;
