import { UserType } from './UserType';

export interface User {
  username: string;
  passwordHash: string;
  userType?: UserType;
}
