import { UserSimple } from './user.model';

export interface ClientSimple extends UserSimple {
  idNumber: string;
  email: string;
  phoneNumber: string;
  driverLicenseNumber: string;
  domesticate: boolean;
  blocked: boolean;
}
