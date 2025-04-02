import { User } from './User';

export interface UserProfile {
  id?: number;
  user?: User;
  mailingAddress?: { id: number };
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  creditScore?: number;
  birthDate?: string;
}
