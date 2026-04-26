import { useState, useEffect } from 'react';
import '../styles/ProblemStatementSelection.css';
import { API_BASE_URL } from '../config/api';

function ProblemStatementSelection({ team, onBack }) {
  const [problemStatements, setProblemStatements] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedPSId, setSelectedPSId] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');

  const pageSize = 10;

  useEffect(() => {
    fetchProblemStatements(0);
  }, []);

  const fetchProblemStatements = async (page) => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(
        `${API_BASE_URL}/api/problem-statements?page=${page}&size=${pageSize}`
      );
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || 'Failed to fetch problem statements');
      }

      setProblemStatements(data.data.content || []);
      setTotalPages(data.data.totalPages || 0);
      setCurrentPage(page);
    } catch (err) {
      setError(err.message || 'Failed to fetch problem statements');
    } finally {
      setLoading(false);
    }
  };

  const handleSelectPS = async (psId) => {
    if (team.selectedProblemStatementId) {
      setError('You already selected a problem statement');
      return;
    }

    setSubmitting(true);
    setError('');
    setSuccessMessage('');

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/teams/${team.id}/select-problem-statement/${psId}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          }
        }
      );

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || 'Failed to select problem statement');
      }

      setSuccessMessage('Problem statement selected successfully!');
      // Update team with selected problem statement
      team.selectedProblemStatementId = psId;
      // Refresh the problem statements to update selection count
      fetchProblemStatements(currentPage);
    } catch (err) {
      setError(err.message || 'An error occurred');
    } finally {
      setSubmitting(false);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      fetchProblemStatements(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      fetchProblemStatements(currentPage + 1);
    }
  };

  return (
    <div className="ps-selection-container">
      <div className="ps-topbar">
        <button className="back-button" onClick={onBack} aria-label="Back to registration">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden>
            <path d="M15 18L9 12L15 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
          </svg>
        </button>
        <div className="page-title">
          <h2>Select Problem Statement</h2>
        </div>
      </div>

      <div className="team-info">
        <div className="team-chip">
          <div className="team-avatar">{team.teamName?.charAt(0) || 'T'}</div>
          <div className="team-meta">
            <div className="team-name">{team.teamName}</div>
            <div className="team-lead">Lead: {team.teamHeadName}</div>
          </div>
        </div>
      </div>

      {successMessage && (
        <div className="success-message">{successMessage}</div>
      )}

      {error && (
        <div className="error-message">{error}</div>
      )}

      {loading ? (
        <div className="loading">Loading problem statements...</div>
      ) : (
        <>
          <div className="ps-grid">
            {problemStatements.length > 0 ? (
              problemStatements.map(ps => (
                <div
                  key={ps.id}
                  className={`ps-card ${!ps.isAvailable ? 'unavailable' : ''} ${team.selectedProblemStatementId === ps.id ? 'your-selection' : ''}`}
                >
                  <div className="ps-card-header">
                    <h3>{ps.title}</h3>
                    <span className={`availability-badge ${ps.isAvailable ? 'available' : 'unavailable'}`}>
                      {ps.isAvailable ? 'Available' : 'Blocked (2/2)'}
                    </span>
                  </div>
                  <p className="ps-description">{ps.description}</p>
                  <div className="ps-footer">
                    <span className="teams-count">
                      Capacity: <strong>{ps.capacity}</strong>
                    </span>
                    <button
                      className="select-button"
                      onClick={() => handleSelectPS(ps.id)}
                      disabled={
                        !ps.isAvailable ||
                        submitting ||
                        team.selectedProblemStatementId === ps.id
                      }
                    >
                      {team.selectedProblemStatementId === ps.id ? 'Selected' : 'Select'}
                    </button>
                  </div>
                </div>
              ))
            ) : (
              <p className="no-ps">No problem statements available</p>
            )}
          </div>

          <div className="pagination">
            <button
              className="pagination-button"
              onClick={handlePreviousPage}
              disabled={currentPage === 0}
            >
              ← Previous
            </button>
            <span className="page-info">
              Page {currentPage + 1} of {totalPages}
            </span>
            <button
              className="pagination-button"
              onClick={handleNextPage}
              disabled={currentPage >= totalPages - 1}
            >
              Next →
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default ProblemStatementSelection;
