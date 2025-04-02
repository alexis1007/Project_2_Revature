import React, { useEffect, useState } from 'react';
import { Loan } from '../models/Loan';
import {
  getLoans,
  addLoan,
  updateLoan,
  deleteLoan,
  approveLoan,
  rejectLoan,
} from '../services/loanService';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

const getUser = () => localStorage.getItem('user');

export const Loans = () => {
  const [loans, setLoans] = useState<Loan[]>([]);
  const [formData, setFormData] = useState<Partial<Loan>>({
    principalBalance: 0,
    interest: 0,
    termLength: 0,
    applicationStatus: {
      id: 1,
      status: 'pending',
    },
    loanType: { id: 1, loanType: 'Home' },
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [error, setError] = useState('');
  const [isManager, setIsManager] = useState(false);
  const [currentUserId, setCurrentUserId] = useState<number | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [location.pathname]);

  useEffect(() => {
    const user = JSON.parse(getUser() || '{}');
    setIsManager(user.userType?.id === 1);
    setCurrentUserId(user.id || null);
    fetchLoans();

    console.log('User type:', user.userType);
    console.log('Is manager:', user.userType?.id === 1);
  }, []);

  const fetchLoans = async () => {
    try {
      const data = await getLoans();
      const user = JSON.parse(getUser() || '{}');

      if (user.userType?.id === 1) {
        // Manager sees all loans
        setLoans(data);
      } else {
        // Regular user sees only their loans
        const userLoans = data.filter(
          (loan: { userProfile: { id: any } }) =>
            loan.userProfile && loan.userProfile.id === user.id
        );
        setLoans(userLoans);
      }
    } catch (err) {
      setError('Failed to fetch loans');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const user = JSON.parse(getUser() || '{}');

      let loanToSave;

      if (editingId) {
        // When editing, preserve the original userProfile that's already in formData
        loanToSave = { ...formData };
      } else {
        // Only for new loans, set the userProfile to current user
        loanToSave = {
          ...formData,
          userProfile: { id: user.id },
        };
      }

      if (editingId) {
        await updateLoan(editingId, loanToSave as Loan);
      } else {
        await addLoan(loanToSave as Loan);
      }
      setEditingId(null);
      setFormData({
        principalBalance: 0,
        interest: 0,
        termLength: 0,
        applicationStatus: {
          id: 1,
          status: 'pending',
        },
        loanType: { id: 1, loanType: 'Home' },
      });
      await fetchLoans();
    } catch (err) {
      setError('Failed to save loan');
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await deleteLoan(id);
      await fetchLoans();
    } catch (err) {
      setError('Failed to delete loan');
    }
  };

  const handleStatusChange = async (
    id: number,
    status: 'approved' | 'rejected'
  ) => {
    try {
      const loan = loans.find((l) => l.id === id);
      if (!loan) return;

      // Create a deep copy of the loan and update its status
      const updatedLoan = {
        ...loan,
        applicationStatus: {
          ...loan.applicationStatus,
          id: status === 'approved' ? 2 : 3,
          status: status,
        },
      };

      console.log('Updating loan status:', id, status, updatedLoan);

      if (status === 'approved') {
        await approveLoan(id, updatedLoan);
      } else {
        await rejectLoan(id, updatedLoan);
      }

      // Force UI update with the new status
      setLoans((prevLoans) =>
        prevLoans.map((l) => (l.id === id ? updatedLoan : l))
      );

      // Then fetch fresh data from server
      await fetchLoans();
    } catch (err) {
      console.error('Error updating loan status:', err);
      setError(`Failed to ${status} loan`);
    }
  };

  const startEdit = (loan: Loan) => {
    setEditingId(loan.id!);
    setFormData(loan);
  };

  const canEditLoan = (loan: Loan) => {
    // Managers can edit any loan, regular users can only edit their own pending loans
    if (isManager) return true;
    return loan.userProfile && loan.userProfile.id === currentUserId;
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
        <h2>Loan Operations</h2>

        <button
          className="buttons logout-button"
          onClick={() =>
            logoutUser().then(() => {
              localStorage.removeItem('user');
              localStorage.removeItem('token');
              navigate('/');
            })
          }
        >
          Logout
        </button>
      </div>

      <div className="main-content-grid">
        <div className="form-column">
          <form onSubmit={handleSubmit} className="entity-form">
            <h3>
              {editingId
                ? 'Edit Loan'
                : isManager
                ? 'Create/Modify Loan'
                : 'New Loan'}
            </h3>

            <div className="form-group">
              <label>Amount:</label>
              <input
                type="number"
                value={formData.principalBalance}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    principalBalance: Number(e.target.value),
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Interest Rate:</label>
              <input
                type="number"
                step="0.01"
                value={formData.interest}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    interest: Number(e.target.value),
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Term (months):</label>
              <input
                type="number"
                value={formData.termLength}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    termLength: Number(e.target.value),
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Type:</label>
              <label htmlFor="loanType">Loan Type:</label>
              <select
                id="loanType"
                value={formData.loanType?.id}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    loanType: {
                      id: Number(e.target.value),
                      loanType: e.target.selectedOptions[0].text,
                    },
                  })
                }
              >
                <option value={1}>Home</option>
                <option value={2}>Auto</option>
                <option value={3}>Business</option>
              </select>
            </div>

            <button className="buttons" type="submit">
              {editingId ? 'Update' : 'Create'}
            </button>
            {editingId && (
              <button
                className="buttons"
                type="button"
                onClick={() => setEditingId(null)}
              >
                Cancel
              </button>
            )}
          </form>
        </div>

        <div className={isManager ? 'loans-column full-width' : 'loans-column'}>
          <h3>{isManager ? 'All Loans' : 'Your Loans'}</h3>

          {/* Pending Loans Section */}
          <div className="pending-loans-section">
            <h4 className="pending-loans-header">Pending Loans</h4>
            <div className="entity-list">
              {loans
                .filter(
                  (loan) =>
                    loan.applicationStatus.status.toLowerCase() === 'pending'
                )
                .map((loan) => (
                  <div key={loan.id} className="entity-card pending-loan-card">
                    <div className="entity-info">
                      <h4>{loan.loanType.loanType} Loan</h4>
                      <h5>${loan.principalBalance}</h5>
                      <p>Interest: {loan.interest}%</p>
                      <p>Term: {loan.termLength} months</p>
                      <p>
                        Status:{' '}
                        <span className={loan.applicationStatus.status}>
                          {loan.applicationStatus.status}
                        </span>
                      </p>
                      {isManager && loan.userProfile && (
                        <p>
                          Applicant: {loan.userProfile.firstName}{' '}
                          {loan.userProfile.lastName}
                        </p>
                      )}
                    </div>
                    <div className="entity-actions">
                      {isManager ? (
                        <>
                          <button
                            className="buttons approved"
                            onClick={() =>
                              handleStatusChange(loan.id!, 'approved')
                            }
                          >
                            Approve
                          </button>
                          <button
                            className="buttons rejected"
                            onClick={() =>
                              handleStatusChange(loan.id!, 'rejected')
                            }
                          >
                            Reject
                          </button>
                          <button
                            className="buttons edit-button"
                            onClick={() => startEdit(loan)}
                          >
                            Edit
                          </button>
                        </>
                      ) : (
                        <>
                          {canEditLoan(loan) && (
                            <>
                              <button
                                className="buttons edit-button"
                                onClick={() => startEdit(loan)}
                              >
                                Edit
                              </button>
                              <button
                                className="buttons delete-button"
                                onClick={() => handleDelete(loan.id!)}
                              >
                                Delete
                              </button>
                            </>
                          )}
                        </>
                      )}
                    </div>
                  </div>
                ))}
              {loans.filter(
                (loan) =>
                  loan.applicationStatus.status.toLowerCase() === 'pending'
              ).length === 0 && (
                <p className="no-pending-loans">No pending loans</p>
              )}
            </div>
          </div>

          <h4 className="loans-count">Total Loans: {loans.length}</h4>
          <div className="entity-list">
            {error && <div className="error">{error}</div>}

            {loans
              .filter(
                (loan) =>
                  loan.applicationStatus.status.toLowerCase() !== 'pending'
              )
              .map((loan) => {
                return (
                  <div key={loan.id} className="entity-card">
                    <div className="entity-info">
                      <h4>{loan.loanType.loanType} Loan</h4>
                      <h5>${loan.principalBalance}</h5>
                      <p>Interest: {loan.interest}%</p>
                      <p>Term: {loan.termLength} months</p>
                      <p>
                        Status:{' '}
                        <span className={loan.applicationStatus.status}>
                          {loan.applicationStatus.status}
                        </span>
                      </p>
                      {isManager && loan.userProfile && (
                        <p>
                          Applicant: {loan.userProfile.firstName}{' '}
                          {loan.userProfile.lastName}
                        </p>
                      )}
                    </div>
                    <div className="entity-actions">
                      {isManager ? (
                        <>
                          {loan.applicationStatus.status.toLowerCase() ===
                            'pending' && (
                            <>
                              <button
                                className="buttons approved"
                                onClick={() =>
                                  handleStatusChange(loan.id!, 'approved')
                                }
                              >
                                Approve
                              </button>
                              <button
                                className="buttons rejected"
                                onClick={() =>
                                  handleStatusChange(loan.id!, 'rejected')
                                }
                              >
                                Reject
                              </button>
                              <button
                                className="buttons edit-button"
                                onClick={() => startEdit(loan)}
                              >
                                Edit
                              </button>
                            </>
                          )}
                          {loan.applicationStatus.status.toLowerCase() !==
                            'pending' && (
                            <button
                              className="buttons edit-button"
                              onClick={() => startEdit(loan)}
                            >
                              Edit
                            </button>
                          )}
                        </>
                      ) : (
                        <>
                          {canEditLoan(loan) &&
                            loan.applicationStatus.status === 'pending' && (
                              <>
                                <button
                                  className="buttons edit-button"
                                  onClick={() => startEdit(loan)}
                                >
                                  Edit
                                </button>
                                <button
                                  className="buttons delete-button"
                                  onClick={() => handleDelete(loan.id!)}
                                >
                                  Delete
                                </button>
                              </>
                            )}
                        </>
                      )}
                    </div>
                  </div>
                );
              })}

            {loans.filter(
              (loan) =>
                loan.applicationStatus.status.toLowerCase() !== 'pending'
            ).length === 0 && (
              <p className="no-loans">No approved or rejected loans</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
