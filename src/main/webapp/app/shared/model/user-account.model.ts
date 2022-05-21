import dayjs from 'dayjs';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';

export interface IUserAccount {
  id?: number;
  accountNumber?: string | null;
  accountName?: string | null;
  walletBalance?: number | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  everyAccount?: ICanteenUser | null;
}

export const defaultValue: Readonly<IUserAccount> = {};
