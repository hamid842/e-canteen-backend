import dayjs from 'dayjs';
import { IMenu } from 'app/shared/model/menu.model';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { ISchool } from 'app/shared/model/school.model';
import { IProduct } from 'app/shared/model/product.model';
import { IActivationCode } from 'app/shared/model/activation-code.model';
import { INotificationHistory } from 'app/shared/model/notification-history.model';
import { IUserOrder } from 'app/shared/model/user-order.model';
import { ITransaction } from 'app/shared/model/transaction.model';
import { ROLE } from 'app/shared/model/enumerations/role.model';

export interface ICanteenUser {
  id?: number;
  fullName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
  imageUrl?: string | null;
  isEnabled?: boolean | null;
  phoneVerified?: boolean | null;
  emailVerified?: boolean | null;
  kkUseId?: string | null;
  role?: ROLE | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  everyWorkerAtSchool?: IMenu | null;
  everyParent?: IUserAccount | null;
  parentOfChildren?: ICanteenUser[] | null;
  eachParents?: ISchool[] | null;
  eachStusentParents?: ICanteenUser[] | null;
  eachWorkers?: IProduct[] | null;
  parentCodes?: IActivationCode[] | null;
  workerCodes?: IActivationCode[] | null;
  parentNotifcations?: INotificationHistory[] | null;
  eachStudents?: IUserOrder[] | null;
  parentTransactions?: ITransaction[] | null;
  workerAtSchools?: ISchool[] | null;
  everyWorker?: ISchool | null;
  children?: ICanteenUser | null;
  parents?: ISchool | null;
  students?: ISchool | null;
  workers?: ICanteenUser | null;
}

export const defaultValue: Readonly<ICanteenUser> = {
  isEnabled: false,
  phoneVerified: false,
  emailVerified: false,
};
