import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';

export interface IMenu {
  id?: number;
  name?: string | null;
  code?: string | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  menus?: IProduct[] | null;
  everyMenu?: ICanteenUser | null;
}

export const defaultValue: Readonly<IMenu> = {};
