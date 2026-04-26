import { useState } from 'react';
import './App.css';
import TeamRegistrationForm from './components/TeamRegistrationForm';
import ProblemStatementSelection from './components/ProblemStatementSelection';

function App() {
  const [currentTeam, setCurrentTeam] = useState(null);
  const [showPSSelection, setShowPSSelection] = useState(false);

  const handleTeamRegistration = (team) => {
    setCurrentTeam(team);
    setShowPSSelection(true);
  };

  const handleBackToForm = () => {
    setCurrentTeam(null);
    setShowPSSelection(false);
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>🚀 Hackathon Form</h1>
      </header>

      <main className="app-main">
        {!showPSSelection ? (
          <TeamRegistrationForm onTeamRegistered={handleTeamRegistration} />
        ) : (
          <ProblemStatementSelection 
            team={currentTeam} 
            onBack={handleBackToForm}
          />
        )}
      </main>

      <footer className="app-footer">
        <p>&copy; 2024 Hackathon Platform. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
