import { Role } from '../enum/role.enum';
import { UserSimple } from './user.model';

export interface EmployeeSimple extends UserSimple {}

export interface AddEmployeeDTO {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  role: Role;
}
