import dayjs from 'dayjs';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { ROLE } from 'app/shared/model/enumerations/role.model';

export interface ISchool {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
  kkUseId?: string | null;
  createdDate?: string | null;
  modifiedDate?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  role?: ROLE | null;
  everySchool?: ICanteenUser | null;
  studentSchools?: ICanteenUser[] | null;
  eachSchools?: ICanteenUser[] | null;
  school?: ICanteenUser | null;
  schools?: ICanteenUser | null;
}

export const defaultValue: Readonly<ISchool> = {};
