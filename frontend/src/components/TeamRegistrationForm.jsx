import { useState } from 'react';
import '../styles/TeamRegistrationForm.css';
import { API_BASE_URL } from '../config/api';

function TeamRegistrationForm({ onTeamRegistered }) {
  const [formData, setFormData] = useState({
    teamName: '',
    teamHeadName: '',
    teamHeadEmail: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    setError('');
  };

  const validateForm = () => {
    if (!formData.teamName.trim()) {
      setError('Team name is required');
      return false;
    }
    if (!formData.teamHeadName.trim()) {
      setError('Team head name is required');
      return false;
    }
    if (!formData.teamHeadEmail.trim()) {
      setError('Team head email is required');
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.teamHeadEmail)) {
      setError('Please enter a valid email address');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await fetch(`${API_BASE_URL}/api/teams/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      const data = await response.json();

      if (!response.ok) {
        // If team already exists, fetch the existing team data
        if (data.message && data.message.includes('already exists')) {
          try {
            const existingTeamResponse = await fetch(
              `${API_BASE_URL}/api/teams/name/${encodeURIComponent(formData.teamName)}`
            );
            const existingTeamData = await existingTeamResponse.json();
            if (existingTeamResponse.ok) {
              onTeamRegistered(existingTeamData.data);
              return;
            }
          } catch (fetchErr) {
            // If fetch fails, still show error
            console.error('Could not fetch existing team:', fetchErr);
          }
        }
        throw new Error(data.message || 'Failed to register team');
      }

      onTeamRegistered(data.data);
    } catch (err) {
      setError(err.message || 'An error occurred during registration');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registration-container">
      <div className="form-wrapper">
        <div className="form-header">
          <h2>Team Registration</h2>
          <p>Enter team details and continue to select a problem statement</p>
        </div>

        <form onSubmit={handleSubmit} className="registration-form">
          <div className="form-group">
            <label htmlFor="teamName">Team Name</label>
            <input
              type="text"
              id="teamName"
              name="teamName"
              value={formData.teamName}
              onChange={handleChange}
              placeholder="Enter your team name"
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label htmlFor="teamHeadName">Team Head Name</label>
            <input
              type="text"
              id="teamHeadName"
              name="teamHeadName"
              value={formData.teamHeadName}
              onChange={handleChange}
              placeholder="Enter team head name"
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label htmlFor="teamHeadEmail">Team Head Email ID</label>
            <input
              type="email"
              id="teamHeadEmail"
              name="teamHeadEmail"
              value={formData.teamHeadEmail}
              onChange={handleChange}
              placeholder="Enter team head email"
              disabled={loading}
            />
          </div>

          {error && <div className="error-message">{error}</div>}

          <button
            type="submit"
            className="submit-button"
            disabled={loading}
          >
            {loading ? 'Registering...' : 'Register Team'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default TeamRegistrationForm;
