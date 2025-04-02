import { ApplicationStatus } from '../models/ApplicationStatus';

const API_BASE_URL = 'http://localhost:7070';

const getToken = () => localStorage.getItem('token');

export const getAllApplicationStatuses = async () => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/application_statuses`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });
  return response.json();
};

export const getApplicationStatusById = async (id: number) => {
  const token = getToken();
  const response = await fetch(
    `${API_BASE_URL}/api/application_statuses/${id}`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    }
  );
  return response.json();
};

export const createApplicationStatus = async (status: ApplicationStatus) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/application_statuses`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(status),
    credentials: 'include',
  });
  return response.json();
};

export const updateApplicationStatus = async (
  id: number,
  status: ApplicationStatus
) => {
  const token = getToken();
  const response = await fetch(
    `${API_BASE_URL}/api/application_statuses/${id}`,
    {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(status),
      credentials: 'include',
    }
  );
  return response.json();
};

export const deleteApplicationStatus = async (id: number) => {
  const token = getToken();
  const response = await fetch(
    `${API_BASE_URL}/api/application_statuses/${id}`,
    {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      },
      credentials: 'include',
    }
  );
  return response;
};
