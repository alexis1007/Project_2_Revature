import { UserProfile } from '../models/UserProfile';

const API_BASE_URL = 'http://localhost:7070';

const getToken = () => localStorage.getItem('token');


export const getUsersProfiles = async () => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/user-profiles`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });
  return response.json();
};

export const getUsersProfileById = async (id : number) => {
    const token = getToken();
    const response = await fetch(`${API_BASE_URL}/api/user-profiles/${id}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    });
    return response.json();
  };

export const updateUserProfile = async (id: number, userProfile: UserProfile) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/user-profiles/${id}`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userProfile),
    credentials: 'include',
  });
  return response.json();
};
