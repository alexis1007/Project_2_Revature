import { Loan } from '../models/Loan';

const API_BASE_URL = 'http://localhost:7070';

const getToken = () => localStorage.getItem('token');

export const getLoans = async () => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });
  return response.json();
};

export const addLoan = async (loan: Loan) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loan),
    credentials: 'include',
  });
  return response.json();
};

export const updateLoan = async (id: number, loan: Loan) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans/${id}`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loan),
    credentials: 'include',
  });
  return response.json();
};

export const deleteLoan = async (id: number) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    credentials: 'include',
  });
  return response;
};

export const approveLoan = async (id: number, loan: Loan) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans/${id}/approve`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loan),
    credentials: 'include',
  });
  return response.json();
};

export const rejectLoan = async (id: number, loan: Loan) => {
  const token = getToken();
  const response = await fetch(`${API_BASE_URL}/api/loans/${id}/reject`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(loan),
    credentials: 'include',
  });
  return response.json();
};


