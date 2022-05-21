import canteenUser from 'app/entities/canteen-user/canteen-user.reducer';
import school from 'app/entities/school/school.reducer';
import productCategory from 'app/entities/product-category/product-category.reducer';
import product from 'app/entities/product/product.reducer';
import menu from 'app/entities/menu/menu.reducer';
import notificationHistory from 'app/entities/notification-history/notification-history.reducer';
import activationCode from 'app/entities/activation-code/activation-code.reducer';
import userAccount from 'app/entities/user-account/user-account.reducer';
import transaction from 'app/entities/transaction/transaction.reducer';
import userOrder from 'app/entities/user-order/user-order.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  canteenUser,
  school,
  productCategory,
  product,
  menu,
  notificationHistory,
  activationCode,
  userAccount,
  transaction,
  userOrder,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
