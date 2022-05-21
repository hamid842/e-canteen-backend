import dayjs from 'dayjs';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface ITransaction {
  id?: number;
  transactionId?: string | null;
  transactionStatus?: TransactionStatus | null;
  orderNumber?: string | null;
  paymentMethod?: PaymentMethod | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  transactions?: ICanteenUser | null;
}

export const defaultValue: Readonly<ITransaction> = {};
