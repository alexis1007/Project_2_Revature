import React, { useEffect, useState } from 'react';
import { UserProfile } from '../models/UserProfile';
import {
  getUsersProfiles,
  getUsersProfileById,
  updateUserProfile,
} from '../services/profileService';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

const getUser = () => localStorage.getItem('user');

export const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState<UserProfile[]>([]);
  const [formData, setFormData] = useState<Partial<UserProfile>>({
    //user: {username: "default", passwordHash: "defaultpass"},
    // mailingAddress?: { id: 1},
    //firstName: "first",
    // lastName?: string;
    // phoneNumber?: string;
    // creditScore?: number;
    // birthDate?: string;
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [location.pathname]);

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  const fetchUserProfiles = async () => {
    try {
      const user = JSON.parse(getUser() || '{}');
      if(user.userType.id == 1) {
        const data = await getUsersProfiles();
        setUserProfiles(data);
    }
      else {
        const data = await getUsersProfileById(user.id);
        setUserProfiles([data]);
    }

    } catch (err) {
      setError('Failed to fetch user profiles');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingId) {
        await updateUserProfile(editingId, formData as UserProfile);
      }
      setEditingId(null);
      setFormData({
        firstName: "",
        lastName: "",
        phoneNumber: "",
        // applicationStatus: { id: 1, status: 'pending' },
        // loanType: { id: 1, loanType: 'Home' },
      });
      await fetchUserProfiles();
    } catch (err) {
      setError('Failed to save loan');
    }
  };

  const startEdit = (userProfile: UserProfile) => {
    setEditingId(userProfile.id!);
    setFormData(userProfile);
  };

  return (
    <div className="main-container entity-container">
      <div className="header">

        <button
          className="buttons dashboard-button"
          onClick={() =>
            logoutUser().then(() => {
              navigate('/dashboard');
            })
          }
        >
          Dashboard
        </button>
        <h2>User Profile Management</h2>
        <div className="header">
          <button
            className="buttons logout-button"
            onClick={() => logoutUser().then(() => navigate('/'))}
          >
            Logout
          </button>
        </div>
      </div>

      <div className="main-content-grid">
        {/* ----------------------------------------- */}
        {editingId &&
        <div className="form-column">
          <form onSubmit={handleSubmit} className="loan-form">
            <h3>{editingId ? 'Edit UserProfile' : 'New userProfile'}</h3>

            <div className="form-group">
              <label>First name:</label>
              <input
                type="text"
                value={formData.firstName}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    firstName: e.target.value,
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Last name:</label>
              <input
                type="text"
                step="0.01"
                value={formData.lastName}
                onChange={(e) =>
                  setFormData({
                     ...formData,
                     lastName: e.target.value
                    })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Phone number:</label>
              <input
                type="text"
                value={formData.phoneNumber}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    phoneNumber: e.target.value,
                  })
                }
                required
              />
            </div>

            <button type="submit">{editingId ? 'Update' : 'Create'}</button>
            {editingId && (
              <button type="button" onClick={() => setEditingId(null)}>
                Cancel
              </button>
            )}
          </form>
        </div>
        }
{/* ------------------------------ */}
        <div className="userProfile-column">
          <h3>User Profile information</h3>
          <div className="userProfile-list">
            {error && <div className="error">{error}</div>}

            {userProfiles.map((userProfile) => (
              <div key={userProfile.id} className="userProfile-card">
                <div className="userProfile-info">
                  <h4>User profile: {userProfile.firstName} {userProfile.lastName}</h4>
                  <h5>Username: {userProfile.user?.username}</h5>
                  <p>Phone number: {userProfile.phoneNumber}</p>
                  <p>Credit score: {userProfile.creditScore}</p>
                </div>
                <div className="userProfile-actions">
                  <button
                    className="buttons edit-button"
                    onClick={() => startEdit(userProfile)}
                  >
                    Edit
                  </button>
                  {/* <button
                    className="delete-button"
                    onClick={() => handleDelete(userProfile.id!)}
                  >
                    Delete
                  </button> */}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

