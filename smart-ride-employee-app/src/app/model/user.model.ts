import { Role } from '../enum/role.enum';

export interface UserSimple {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  role: Role;
}
