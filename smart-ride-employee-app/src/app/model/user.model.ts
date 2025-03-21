import { Role } from '../enum/role.enum';

export interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  role: Role;
}
