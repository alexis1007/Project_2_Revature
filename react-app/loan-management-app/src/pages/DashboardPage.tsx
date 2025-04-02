import React from 'react';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

export const DashboardPage = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logoutUser();
    navigate('/');
  };

  return (
    <div className="main-container dashboard-container">
      <div className="dashboard-header">
        <h2 className="form-title">Welcome to Your Dashboard</h2>
      </div>

      <div className="dashboard-nav">
        <div className="header">
          <button
            className="buttons logout-button"
            onClick={() => logoutUser().then(() => navigate('/'))}
          >
            Logout
          </button>
        </div>

        <div className="nav-card">
          <h3 className="section-title">Profile Management</h3>
          <Link to="/profile" className="nav-link">
            View/Edit Profile
          </Link>
        </div>

        <div className="nav-card">
          <h3 className="section-title">Loan Operations</h3>
          <Link to="/loans" className="nav-link">
            Manage Loans
          </Link>
        </div>

        <div className="nav-card">
          <h3 className="section-title">Account Actions</h3>
          <button className="nav-button" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};
