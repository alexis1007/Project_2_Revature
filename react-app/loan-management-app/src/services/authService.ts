import { AuthRequest } from '../models/AuthRequest';
import { UserProfile } from '../models/UserProfile';

const API_BASE_URL = 'http://localhost:7070';

export const registerUser = async (userProfile: UserProfile) => {
  return fetch(`${API_BASE_URL}/api/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(userProfile),
  });
};

export const loginUser = async (user: AuthRequest) => {
  return fetch(`${API_BASE_URL}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(user),
    credentials: 'include',
  });
};

export const logoutUser = async () => {
  return fetch(`${API_BASE_URL}/api/auth/logout`, {
    method: 'POST',
    credentials: 'include',
  });
};
