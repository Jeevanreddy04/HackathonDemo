# Hackathon Form - Architecture & Flow Diagrams

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Hackathon Form System                        │
└─────────────────────────────────────────────────────────────────┘

┌──────────────────────┐              ┌──────────────────────┐
│   React Frontend     │              │  Spring Boot Backend │
│   (Port 5173)        │◄────HTTP────►│  (Port 8080)         │
│                      │   JSON API   │                      │
└──────────────────────┘              └──────────────────────┘
         │                                      │
         │ React Components                     │ REST Controllers
         │ • App.jsx                            │ • TeamController
         │ • TeamRegistration...                │ • ProblemStatement...
         │ • ProblemStatement...                │
         │                                      │ Services
         │                                      │ • TeamService
         │ Styling                              │ • ProblemStatement...
         │ • App.css                            │
         │ • TeamRegistrationForm.css           │ Repositories
         │ • ProblemStatement...                │ • TeamRepository
         │                                      │ • ProblemStatement...
         │                                      │
         │                                      ├─► MySQL Database
         │                                      │   • teams
         │                                      │   • problem_statements
         └──────────────────────────────────────┘
```

## 📊 Entity Relationship Diagram

```
┌──────────────────────────────┐
│       TEAMS                  │
├──────────────────────────────┤
│ id (PK)                      │
│ teamName (UNIQUE)            │
│ teamHeadName                 │
│ teamHeadEmail (UNIQUE)       │
│ problem_statement_id (FK)    │◄─────────┐
│ registrationDate             │          │
└──────────────────────────────┘          │
                                          │
                                    (Foreign Key)
                                          │
                                          │
                              ┌──────────────────────────────────┐
                              │  PROBLEM_STATEMENTS              │
                              ├──────────────────────────────────┤
                              │ id (PK)                          │
                              │ title                            │
                              │ description                      │
                              │ selected_teams_count             │
                              │ createdDate                      │
                              └──────────────────────────────────┘

Relationship:
- One Problem Statement can have many Teams (1:N)
- Each Team can select only ONE Problem Statement (N:1)
- Each Problem Statement can have maximum 2 Teams
```

## 🔄 User Journey Flow

```
START
  │
  ▼
┌─────────────────────────────────────┐
│  User Opens Application             │
│  (http://localhost:5173)            │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  REGISTRATION PAGE                  │
│  • Enter Team Name                  │
│  • Enter Team Head Name             │
│  • Enter Team Head Email            │
└─────────────────────────────────────┘
  │
  ▼ (Click Register)
┌─────────────────────────────────────┐
│  Validate Form                      │
│  ✓ All fields filled                │
│  ✓ Valid email format               │
└─────────────────────────────────────┘
  │
  ├─ Validation FAILS ──► Show Error Message ──┐
  │                                             │
  └─ Validation OK ──────────────────────┐    │
                                         │    │
                                         ▼    │
                              ┌──────────────────────────┐
                              │  POST /api/teams/register│
                              │  Send Team Data          │
                              └──────────────────────────┘
                                         │
                          ┌──────────────┴──────────────┐
                          │                             │
                    Backend Validation            (May Fail)
                    ✓ Team name unique                 │
                    ✓ Email unique                     │
                    ✓ Email format valid               │
                          │                             │
                ┌─ Validation OK                       │
                │         │                             │
                │         ▼                             │
                │  ┌──────────────────┐                │
                │  │ Save to Database │                │
                │  │ Return Team ID   │                │
                │  └──────────────────┘                │
                │         │                             │
                └────┬────┘                      Show Error ◄──┘
                     │
                     ▼
┌─────────────────────────────────────────┐
│  PROBLEM STATEMENT SELECTION PAGE       │
│  • Show PS Grid (10 per page)           │
│  • Display Availability Badges          │
│  • Show Teams Selected Count            │
└─────────────────────────────────────────┘
  │
  ▼ (Click Select on PS)
┌─────────────────────────────────────────┐
│  POST /api/teams/{teamId}/             │
│       select-problem-statement/{psId}   │
└─────────────────────────────────────────┘
  │
  ├─ Check: Team Already Selected PS?     
  │         YES ──► Error: "Already selected" ──┐
  │         NO                                   │
  │                                              │
  ├─ Check: PS Has >= 2 Teams?                  │
  │         YES ──► Error: "Full" ──────────────┤
  │         NO                                   │
  │                                              │
  └─ Selection Valid                            │
      │                                          │
      ▼                                          │
  ┌─────────────────────────┐                  │
  │ Update Database:        │                  │
  │ • Assign PS to Team     │                  │
  │ • Increment Team Count  │                  │
  │ • Return Success        │                  │
  └─────────────────────────┘                  │
      │                                         │
      └────┬──────────────────────────────────┐ │
           │                                  │ │
           ▼ Success                    Show Error ◄┘
┌─────────────────────────────────────┐
│  Show Success Message               │
│  • PS Card Shows "Selected"         │
│  • Button Becomes Disabled          │
│  • Notice Shows at Top              │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  User Can:                          │
│  • Browse Other PS (Read-Only)      │
│  • Navigate Pages (Pagination)      │
│  • Click Back to Register New Team  │
└─────────────────────────────────────┘
  │
  └──► END (or Back to Registration)
```

## 📡 API Call Sequence

```
REGISTRATION FLOW:
┌─────────┐                                    ┌──────────┐
│ Frontend│                                    │ Backend  │
└────┬────┘                                    └────┬─────┘
     │                                              │
     │  POST /api/teams/register                   │
     │  {                                          │
     │    "teamName": "Team Alpha",                │
     │    "teamHeadName": "John Doe",              │
     │    "teamHeadEmail": "john@example.com"      │
     │  }                                          │
     ├─────────────────────────────────────────►  │
     │                                        [Validate]
     │                                        [Save to DB]
     │                                              │
     │◄─────────────────────────────────────────   │
     │  200 OK                                     │
     │  {                                          │
     │    "success": true,                         │
     │    "message": "...",                        │
     │    "data": {                                │
     │      "id": 1,                               │
     │      "teamName": "Team Alpha",              │
     │      ...                                    │
     │    }                                        │
     │  }                                          │
     │                                              │
[Show Success]                               [Log Entry]


PROBLEM STATEMENT SELECTION FLOW:
┌─────────┐                                    ┌──────────┐
│ Frontend│                                    │ Backend  │
└────┬────┘                                    └────┬─────┘
     │                                              │
     │  POST /api/teams/1/                        │
     │       select-problem-statement/5           │
     │                                              │
     ├─────────────────────────────────────────►  │
     │                                        [Check Validation]
     │                                        [Update DB]
     │                                              │
     │◄─────────────────────────────────────────   │
     │  200 OK / 400 Bad Request                   │
     │  { success, message, data }                │
     │                                              │
[Update UI]                                  [Log Entry]
```

## 🔐 Validation Rules

```
┌─────────────────────────────────────────────────────────┐
│              TEAM REGISTRATION VALIDATION                │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Field: teamName                                        │
│  ├─ Required: YES                                       │
│  ├─ Format: String (1-255 chars)                        │
│  ├─ Unique: YES (Database constraint)                   │
│  └─ Error: "Team with this name already exists"         │
│                                                          │
│  Field: teamHeadName                                    │
│  ├─ Required: YES                                       │
│  ├─ Format: String (1-255 chars)                        │
│  └─ Error: "Team head name is required"                 │
│                                                          │
│  Field: teamHeadEmail                                   │
│  ├─ Required: YES                                       │
│  ├─ Format: Valid Email (RFC 5322)                      │
│  ├─ Unique: YES (Database constraint)                   │
│  └─ Error: "Invalid email" or "Email already registered"│
│                                                          │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│      PROBLEM STATEMENT SELECTION VALIDATION              │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Check 1: Team Exists?                                  │
│  ├─ YES ──► Continue                                    │
│  └─ NO ───► Error: "Team not found"                     │
│                                                          │
│  Check 2: Team Already Selected PS?                     │
│  ├─ YES ──► Error: "You already selected a PS"          │
│  └─ NO ───► Continue                                    │
│                                                          │
│  Check 3: Problem Statement Exists?                     │
│  ├─ YES ──► Continue                                    │
│  └─ NO ───► Error: "Problem statement not found"        │
│                                                          │
│  Check 4: PS Selection Count < 2?                       │
│  ├─ YES ──► Selection Allowed                           │
│  └─ NO ───► Error: "Max team limit reached"             │
│                                                          │
│  All Checks Pass ──► Update Database                    │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## 📊 Data Flow - Registration

```
USER INPUT
    │
    ▼
┌──────────────────────┐
│ Frontend Validation  │ (Client-side)
│ • Not empty          │
│ • Valid email regex  │
└──────────────────────┘
    │
    ├─ FAIL ──► Display Error ──► STOP
    │
    ▼
┌──────────────────────────────────────┐
│ POST /api/teams/register             │
│ (with team data)                     │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Backend Service: TeamService         │ (Server-side)
│ • Check name uniqueness              │
│ • Check email uniqueness             │
│ • Validate email format              │
└──────────────────────────────────────┘
    │
    ├─ FAIL ──► Exception Caught ──► Error Response ──► Frontend Shows Error
    │
    ▼
┌──────────────────────────────────────┐
│ Create Team Entity                   │
│ • Set all fields                     │
│ • Set registrationDate = NOW()       │
│ • selectedProblemStatement = null    │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Save to Database                     │
│ (TeamRepository.save())              │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Convert Entity to DTO                │
│ • Extract relevant fields            │
│ • Create TeamResponse object         │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Return Success Response              │
│ • 200 OK                             │
│ • success: true                      │
│ • data: TeamResponse object          │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Frontend Receives Response           │
│ • Extract team.id                    │
│ • Store in state                     │
└──────────────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│ Frontend Navigation                  │
│ • Show PS Selection Page             │
│ • Display team info at top           │
│ • Load PS list                       │
└──────────────────────────────────────┘
```

## 🎯 Component Hierarchy

```
App.jsx (Root)
├── State Management
│   ├── currentTeam (Team object)
│   └── showPSSelection (boolean)
│
└── Conditional Rendering
    │
    ├─ IF !showPSSelection
    │  │
    │  └─► TeamRegistrationForm
    │      ├── State
    │      │   ├── formData (object)
    │      │   ├── error (string)
    │      │   └── loading (boolean)
    │      │
    │      ├── Functions
    │      │   ├── handleChange()
    │      │   ├── validateForm()
    │      │   └── handleSubmit()
    │      │
    │      └── JSX
    │          ├── Form header
    │          ├── Input fields
    │          ├── Error message
    │          └── Submit button
    │
    └─ ELSE (showPSSelection)
       │
       └─► ProblemStatementSelection
           ├── State
           │   ├── problemStatements (array)
           │   ├── currentPage (number)
           │   ├── totalPages (number)
           │   ├── loading (boolean)
           │   ├── error (string)
           │   ├── submitting (boolean)
           │   └── successMessage (string)
           │
           ├── Functions
           │   ├── fetchProblemStatements()
           │   ├── handleSelectPS()
           │   ├── handlePreviousPage()
           │   └── handleNextPage()
           │
           └── JSX
               ├── Header with team info
               ├── Error/Success messages
               ├── PS Grid (mapped from state)
               │   └── PS Cards (with badges, counts, buttons)
               └── Pagination controls
```

---

This architecture ensures:
- ✓ Separation of concerns
- ✓ Clean data flow
- ✓ Proper validation at both layers
- ✓ Business logic enforcement
- ✓ User-friendly error handling
- ✓ Scalable component structure
