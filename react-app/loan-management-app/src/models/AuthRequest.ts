import { UserType } from './UserType';

export interface AuthRequest {
  username: string;
  password: string;
  userType?: UserType;
}
