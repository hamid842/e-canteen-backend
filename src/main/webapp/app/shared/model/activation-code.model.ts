import dayjs from 'dayjs';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';

export interface IActivationCode {
  id?: number;
  code?: string | null;
  expiryTime?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  parentActivationCode?: ICanteenUser | null;
  workerActivationCode?: ICanteenUser | null;
}

export const defaultValue: Readonly<IActivationCode> = {};
