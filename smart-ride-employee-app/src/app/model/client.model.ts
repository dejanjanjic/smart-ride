import { User } from './user.model';

export interface Client extends User {
  idNumber: string;
  email: string;
  phoneNumber: string;
  driverLicenseNumber: string;
  domesticate: boolean;
  blocked: boolean;
}
