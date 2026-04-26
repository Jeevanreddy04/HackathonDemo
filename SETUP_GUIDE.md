# Hackathon Form Application

A complete web application for managing hackathon team registration and problem statement selection.

## 📋 Features

### Team Registration
- Teams register with team name, team head name, and email
- Email validation
- Duplicate prevention for team names and emails

### Problem Statement Selection
- Browse problem statements with pagination (10 per page)
- Real-time availability tracking (max 2 teams per PS)
- One problem statement per team enforcement
- Visual feedback for selected and unavailable problem statements

## 🏗️ Architecture

### Backend (Spring Boot)
- **Database**: MySQL
- **Framework**: Spring Boot 4.0.6
- **Java Version**: 17
- **Build Tool**: Maven

#### Key Components:
- **Entities**: Team, ProblemStatement
- **DTOs**: TeamRegistrationRequest, TeamResponse, ProblemStatementResponse, SelectProblemStatementRequest, ApiResponse
- **Repositories**: TeamRepository, ProblemStatementRepository
- **Services**: TeamService, ProblemStatementService
- **Controllers**: TeamController, ProblemStatementController

### Frontend (React + Vite)
- **Framework**: React 19.2.5
- **Build Tool**: Vite
- **Component-based architecture**
- **Responsive design with CSS**

#### Components:
- **App**: Main component managing state
- **TeamRegistrationForm**: Registration form component
- **ProblemStatementSelection**: PS listing and selection component

## 🚀 Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 16+
- MySQL 5.7+ or 8.0+

### Backend Setup

1. **Configure MySQL Database**
   ```
   Create a database: hackathon_form_db
   Update credentials in application.properties if needed
   ```

2. **Update Database Configuration** (if different from default)
   Edit `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hackathon_form_db
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. **Build Backend**
   ```bash
   cd backend
   mvn clean install
   ```

4. **Run Backend**
   ```bash
   mvn spring-boot:run
   ```
   Backend will run on `http://localhost:8080`

### Frontend Setup

1. **Install Dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Run Frontend Development Server**
   ```bash
   npm run dev
   ```
   Frontend will run on `http://localhost:5173`

3. **Build Frontend for Production**
   ```bash
   npm run build
   ```

## 📡 API Endpoints

### Team Management
- **POST** `/api/teams/register` - Register a new team
- **GET** `/api/teams/{teamId}` - Get team details
- **POST** `/api/teams/{teamId}/select-problem-statement/{psId}` - Select problem statement

### Problem Statements
- **GET** `/api/problem-statements?page=0&size=10` - Get paginated problem statements
- **GET** `/api/problem-statements/{id}` - Get problem statement details

## 🔄 Data Flow

1. **Team Registration**
   - User fills registration form
   - Frontend sends POST request to `/api/teams/register`
   - Backend validates and stores team
   - Returns team ID and details

2. **Problem Statement Selection**
   - Frontend fetches paginated PS list from `/api/problem-statements`
   - User clicks on a PS card to select
   - Frontend sends POST request with team ID and PS ID
   - Backend validates:
     - Team hasn't already selected a PS
     - PS hasn't reached 2-team limit
   - Updates selection count and returns success
   - Frontend shows error or success message

## 🎨 UI Components

### Team Registration Form
- Text input fields with validation
- Email format validation
- Error message display
- Loading state during submission

### Problem Statement Selection
- Grid layout with PS cards (responsive)
- Pagination controls (Previous/Next)
- Availability badges (Available/Full)
- Teams selected counter
- Select button with disabled states

## 🛡️ Validation Rules

### Team Registration
- Team name: Required, unique
- Team head name: Required
- Email: Required, valid format, unique

### Problem Statement Selection
- Team can only select one PS
- Each PS can be selected by maximum 2 teams
- PS selection is permanent (cannot be changed)

## 📊 Database Schema

### teams table
- id (Long, Primary Key)
- teamName (String, Unique)
- teamHeadName (String)
- teamHeadEmail (String, Unique)
- selectedProblemStatementId (Foreign Key)
- registrationDate (LocalDateTime)

### problem_statements table
- id (Long, Primary Key)
- title (String)
- description (Text)
- selectedTeamsCount (Integer, default: 0)
- createdDate (LocalDateTime)

## 🔄 Default Data

The application automatically creates 25 sample problem statements on first run.

## 📱 Responsive Design

- Desktop: Grid layout with multiple columns
- Tablet: Adjusted grid with fewer columns
- Mobile: Single column layout, stacked components

## 🚨 Error Handling

- Input validation on both frontend and backend
- Clear error messages displayed to users
- HTTP status codes for different error scenarios
- Try-catch blocks with appropriate error responses

## 📝 Future Enhancements

- Team member management
- Real-time notifications
- Admin dashboard for monitoring
- Export registration data
- Email notifications to teams
- PS categorization/filtering
- Team profile editing

## 📄 License

This project is for educational purposes.

