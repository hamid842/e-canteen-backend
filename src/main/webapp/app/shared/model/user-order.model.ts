import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';

export interface IUserOrder {
  id?: number;
  orderNumber?: string | null;
  ordrerCode?: string | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  userOrderLists?: IProduct[] | null;
  orders?: ICanteenUser | null;
}

export const defaultValue: Readonly<IUserOrder> = {};
