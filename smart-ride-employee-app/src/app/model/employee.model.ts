import { Role } from '../enum/role.enum';

export interface Employee {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  role: Role;
}
