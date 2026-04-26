# 📁 Complete Project Structure

```
Hackathon-Form-2/
│
├── 📘 Documentation Files
│   ├── README_PROJECT.md                    ← START HERE! Complete overview
│   ├── QUICK_START.md                       ← Get running in 5 minutes
│   ├── SETUP_GUIDE.md                       ← Detailed setup instructions
│   ├── IMPLEMENTATION_SUMMARY.md            ← Architecture & implementation
│   ├── ARCHITECTURE.md                      ← Diagrams & data flow
│   └── TROUBLESHOOTING.md                   ← Common issues & solutions
│
├── 📂 backend/                              (Spring Boot Application)
│   │
│   ├── 📄 pom.xml                          ← Maven configuration (UPDATED)
│   │   └── Updated with Lombok dependency
│   │
│   ├── mvnw / mvnw.cmd                     ← Maven wrapper
│   │
│   ├── 📂 src/
│   │   │
│   │   ├── 📂 main/
│   │   │   │
│   │   │   ├── 📂 java/com/backend/backend/
│   │   │   │   │
│   │   │   │   ├── 📂 entity/              (JPA Entities)
│   │   │   │   │   ├── Team.java
│   │   │   │   │   └── ProblemStatement.java
│   │   │   │   │
│   │   │   │   ├── 📂 dto/                 (Data Transfer Objects)
│   │   │   │   │   ├── TeamRegistrationRequest.java
│   │   │   │   │   ├── TeamResponse.java
│   │   │   │   │   ├── ProblemStatementResponse.java
│   │   │   │   │   ├── SelectProblemStatementRequest.java
│   │   │   │   │   └── ApiResponse.java
│   │   │   │   │
│   │   │   │   ├── 📂 repository/          (Data Access Layer)
│   │   │   │   │   ├── TeamRepository.java
│   │   │   │   │   └── ProblemStatementRepository.java
│   │   │   │   │
│   │   │   │   ├── 📂 service/             (Business Logic Layer)
│   │   │   │   │   ├── TeamService.java
│   │   │   │   │   └── ProblemStatementService.java
│   │   │   │   │
│   │   │   │   ├── 📂 controller/          (REST Controllers)
│   │   │   │   │   ├── TeamController.java
│   │   │   │   │   └── ProblemStatementController.java
│   │   │   │   │
│   │   │   │   ├── 📂 config/              (Configuration)
│   │   │   │   │   └── DataInitializer.java
│   │   │   │   │
│   │   │   │   └── BackendApplication.java  (Main Application)
│   │   │   │
│   │   │   └── 📂 resources/
│   │   │       ├── application.properties   ← Database config (UPDATED)
│   │   │       ├── 📂 static/
│   │   │       └── 📂 templates/
│   │   │
│   │   └── 📂 test/
│   │       └── 📂 java/com/backend/backend/
│   │           └── BackendApplicationTests.java
│   │
│   ├── HELP.md                              ← Spring Boot help
│   └── .gitignore
│
├── 📂 frontend/                              (React + Vite Application)
│   │
│   ├── 📄 package.json                     ← Node dependencies
│   ├── 📄 package-lock.json
│   ├── 📄 vite.config.js                   ← Vite configuration
│   ├── 📄 eslint.config.js                 ← ESLint configuration
│   ├── 📄 index.html                       ← HTML entry point
│   │
│   ├── 📂 src/                              (React Source Code)
│   │   │
│   │   ├── 📂 components/                  (React Components)
│   │   │   ├── TeamRegistrationForm.jsx    ← Registration form component
│   │   │   └── ProblemStatementSelection.jsx ← PS selection component
│   │   │
│   │   ├── 📂 styles/                      (Component Styles)
│   │   │   ├── TeamRegistrationForm.css
│   │   │   └── ProblemStatementSelection.css
│   │   │
│   │   ├── App.jsx                         ← Main App component (UPDATED)
│   │   ├── App.css                         ← App styles
│   │   ├── AppStyles.css                   ← Global styles (NEW)
│   │   ├── index.css                       ← Global index styles
│   │   ├── main.jsx                        ← React entry point
│   │   │
│   │   └── 📂 assets/                      ← Static assets
│   │
│   ├── 📂 public/                           ← Public assets
│   │
│   ├── 📄 README.md
│   └── .gitignore
│
└── 📝 Project Root Files
    ├── SETUP_GUIDE.md
    ├── QUICK_START.md
    ├── README_PROJECT.md
    ├── IMPLEMENTATION_SUMMARY.md
    ├── ARCHITECTURE.md
    ├── TROUBLESHOOTING.md
    └── .gitignore (if exists)

```

---

## 📊 File Count Summary

| Layer | Component | Count | Status |
|-------|-----------|-------|--------|
| **Backend** | Entity | 2 | ✅ Created |
| | DTO | 5 | ✅ Created |
| | Repository | 2 | ✅ Created |
| | Service | 2 | ✅ Created |
| | Controller | 2 | ✅ Created |
| | Config | 1 | ✅ Created |
| | **Backend Total** | **14** | ✅ |
| **Frontend** | Component | 2 | ✅ Created |
| | Style | 2 | ✅ Created |
| | Main App | 1 | ✅ Updated |
| | **Frontend Total** | **5** | ✅ |
| **Documentation** | Guides | 6 | ✅ Created |
| **Total** | | **25+** | ✅ |

---

## 🔍 Key Files Explanation

### Backend

**Entity Files** (database models)
```
Team.java
└─ Represents a team with name, head info, selected PS
└─ Annotated with @Entity for JPA
└─ Has foreign key to ProblemStatement

ProblemStatement.java
└─ Represents a problem statement
└─ Tracks selection count (0-2)
└─ Has relationship to Teams
```

**DTO Files** (data transfer objects)
```
TeamRegistrationRequest.java
└─ Request body for registration

TeamResponse.java
└─ Response after registration/selection

ProblemStatementResponse.java
└─ Response for PS list/details
└─ Includes availability info

ApiResponse.java
└─ Standard wrapper for all API responses
└─ Contains success, message, data
```

**Repository Files** (database access)
```
TeamRepository.java
└─ JPA repository for Team entity
└─ Custom query methods

ProblemStatementRepository.java
└─ JPA repository for ProblemStatement
└─ Supports pagination
```

**Service Files** (business logic)
```
TeamService.java
└─ Register team with validation
└─ Select PS with business rules
└─ Enforce constraints

ProblemStatementService.java
└─ Fetch paginated PS list
└─ Create default problem statements
```

**Controller Files** (REST endpoints)
```
TeamController.java
├─ POST /api/teams/register
├─ GET /api/teams/{teamId}
└─ POST /api/teams/{id}/select-ps/{psId}

ProblemStatementController.java
├─ GET /api/problem-statements (paginated)
└─ GET /api/problem-statements/{id}
```

### Frontend

**Component Files** (React components)
```
App.jsx
└─ Main app component
└─ Manages navigation state
└─ Renders registration or PS selection

TeamRegistrationForm.jsx
└─ Registration form component
└─ Form validation
└─ API integration

ProblemStatementSelection.jsx
└─ PS listing and selection
└─ Pagination handling
└─ Availability display
```

**Style Files** (CSS)
```
TeamRegistrationForm.css
└─ Form styling
└─ Input styling
└─ Responsive layout

ProblemStatementSelection.css
└─ Grid layout for PS cards
└─ Card styling
└─ Badge styling
└─ Pagination styling

AppStyles.css
└─ Global styles
└─ Root variables
└─ Header/Footer styles
```

**Configuration Files**
```
package.json
└─ React dependencies
└─ Build scripts

vite.config.js
└─ Vite configuration
└─ React plugin setup

eslint.config.js
└─ ESLint configuration
└─ Code quality rules
```

---

## 🔗 Dependencies Created

### Backend Dependencies (pom.xml)
```xml
Spring Boot 4.0.6
├─ spring-boot-starter-data-jpa
├─ spring-boot-starter-webmvc
├─ spring-boot-devtools
├─ mysql-connector-j
└─ lombok (ADDED)
```

### Frontend Dependencies (package.json)
```json
React 19.2.5
React DOM 19.2.5
Vite 8.0.10
ESLint & Babel (dev dependencies)
```

---

## 📝 Configuration Files

### application.properties (Backend)
```properties
Server Configuration
├─ Port: 8080

Database Configuration
├─ URL: jdbc:mysql://localhost:3306/hackathon_form_db
├─ Username: root
├─ Password: root
└─ Driver: com.mysql.cj.jdbc.Driver

JPA/Hibernate Configuration
├─ Dialect: MySQL8Dialect
├─ DDL Auto: create-drop (development)
└─ Show SQL: true
```

### vite.config.js (Frontend)
```javascript
React plugin setup
Vite development server
Build configuration
```

---

## 🗂️ Directory Purposes

| Directory | Purpose |
|-----------|---------|
| `backend/src/main/java/...` | Java source code |
| `backend/src/main/resources/` | Configuration files |
| `backend/src/test/` | Unit tests |
| `frontend/src/` | React source code |
| `frontend/public/` | Public static files |
| `frontend/node_modules/` | Installed packages |
| `docs/` | Documentation files |

---

## 📦 Build Artifacts

### Backend
- **Target**: `backend/target/backend-0.0.1-SNAPSHOT.jar`
- **Build Command**: `mvn clean install`
- **Run Command**: `mvn spring-boot:run`

### Frontend
- **Build Dist**: `frontend/dist/` (after `npm run build`)
- **Dev Server**: `npm run dev` (runs on :5173)

---

## 🚀 How Files Work Together

```
User Request Flow:
1. User opens http://localhost:5173
2. React loads from frontend/src/App.jsx
3. App.jsx renders TeamRegistrationForm.jsx
4. User fills form and clicks Register
5. Frontend calls POST /api/teams/register
6. Backend TeamController receives request
7. TeamController calls TeamService.registerTeam()
8. TeamService validates and saves via TeamRepository
9. Backend returns TeamResponse DTO
10. Frontend receives response and navigates to ProblemStatementSelection
11. Frontend calls GET /api/problem-statements (paginated)
12. Backend ProblemStatementController calls ProblemStatementService
13. Service queries database and returns list
14. Frontend displays PS cards
15. User selects PS, frontend calls POST /api/teams/{id}/select-ps/{psId}
16. Backend validates constraints and updates database
17. Returns success response
18. Frontend shows success message
```

---

## ✅ What's Included

✅ **Backend**: Complete Spring Boot application
✅ **Frontend**: Complete React application  
✅ **Database**: MySQL schema with relationships
✅ **API**: RESTful endpoints with validation
✅ **UI**: Responsive components with styling
✅ **Documentation**: 6 comprehensive guides
✅ **Error Handling**: Comprehensive error responses
✅ **Data Validation**: Both frontend and backend
✅ **Business Logic**: All constraints enforced

---

## 🎯 Next Steps

1. Navigate to `backend/` directory
2. Run `mvn spring-boot:run`
3. In another terminal, navigate to `frontend/`
4. Run `npm install` then `npm run dev`
5. Open browser to `http://localhost:5173`

---

**For detailed setup, see QUICK_START.md**

