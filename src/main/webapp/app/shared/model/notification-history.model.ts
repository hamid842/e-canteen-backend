import dayjs from 'dayjs';
import { ICanteenUser } from 'app/shared/model/canteen-user.model';
import { NotificationStatus } from 'app/shared/model/enumerations/notification-status.model';
import { NotificationMethod } from 'app/shared/model/enumerations/notification-method.model';

export interface INotificationHistory {
  id?: number;
  date?: string | null;
  status?: NotificationStatus | null;
  method?: NotificationMethod | null;
  parentNotificationHistory?: ICanteenUser | null;
}

export const defaultValue: Readonly<INotificationHistory> = {};
