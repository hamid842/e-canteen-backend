import dayjs from 'dayjs';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IUserOrder } from 'app/shared/model/user-order.model';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { IMenu } from 'app/shared/model/menu.model';
import { Rating } from 'app/shared/model/enumerations/rating.model';
import { Category } from 'app/shared/model/enumerations/category.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  price?: number | null;
  barcode?: string | null;
  grade?: Rating | null;
  category?: Category | null;
  imageUrl?: string | null;
  expiryDate?: string | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  everyProductCategory?: IProductCategory | null;
  productItem?: IUserOrder | null;
  product?: IProductCategory | null;
  productItemsList?: ICanteenUser | null;
  products?: IMenu | null;
}

export const defaultValue: Readonly<IProduct> = {};
