import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductCategory {
  id?: number;
  name?: string | null;
  code?: string | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  productCategoryItems?: IProduct[] | null;
  everyProduct?: IProduct | null;
}

export const defaultValue: Readonly<IProductCategory> = {};
