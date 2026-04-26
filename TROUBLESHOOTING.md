# 🔧 Troubleshooting Guide

## Common Issues & Solutions

### 🔴 Backend Issues

#### Issue 1: "Address already in use"
**Error**: `Address already in use: /0.0.0.0:8080`

**Solution**:
```bash
# Find process using port 8080
Windows: netstat -ano | findstr :8080
Linux/Mac: lsof -i :8080

# Kill the process
Windows: taskkill /PID <process_id> /F
Linux/Mac: kill -9 <process_id>

# Or change port in application.properties:
server.port=8081
```

---

#### Issue 2: "Cannot find MySQL"
**Error**: `Access denied for user 'root'@'localhost'`

**Solution**:
```bash
# Start MySQL service
Windows: services.msc (search for MySQL service and start)
Linux: sudo service mysql start
Mac: brew services start mysql

# Verify MySQL is running:
mysql -u root -p
# Enter password: root
```

---

#### Issue 3: "Database does not exist"
**Error**: `Unknown database 'hackathon_form_db'`

**Solution**:
```bash
# Create database manually
mysql -u root -p
# Enter password: root

CREATE DATABASE hackathon_form_db;
EXIT;
```

---

#### Issue 4: "Cannot compile Java"
**Error**: `java.lang.UnsupportedClassVersionError`

**Solution**:
```bash
# Check Java version
java -version
# Should show Java 17+

# If not installed:
# Download from https://www.oracle.com/java/technologies/downloads/

# Update JAVA_HOME environment variable to Java 17+ installation path
```

---

#### Issue 5: "Maven command not found"
**Error**: `'mvn' is not recognized`

**Solution**:
```bash
# Check Maven installation
mvn -version
# Should show Maven version

# If not installed:
# Download from https://maven.apache.org/download.cgi
# Add Maven bin directory to PATH

# Windows: Add to System Environment Variables
# C:\path\to\maven\bin

# Linux/Mac: Add to ~/.bashrc or ~/.zshrc
# export PATH=$PATH:/path/to/maven/bin
```

---

#### Issue 6: "Lombok not found"
**Error**: `error: package lombok does not exist`

**Solution**:
```bash
# Already added to pom.xml, just rebuild:
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

---

#### Issue 7: "Port 5173 blocked by Windows Firewall"
**Error**: Cannot connect to frontend from backend

**Solution**:
1. Open Windows Defender Firewall
2. Click "Allow an app through firewall"
3. Click "Change settings"
4. Add Node.js to allowed apps
5. Or add firewall rule for port 5173

---

### 🟡 Frontend Issues

#### Issue 1: "npm: command not found"
**Error**: `npm: command not found`

**Solution**:
```bash
# Check Node.js installation
node -v
npm -v

# If not installed:
# Download from https://nodejs.org/

# Should install Node 16+
```

---

#### Issue 2: "Cannot find module react"
**Error**: `Cannot find module 'react'`

**Solution**:
```bash
# Clear npm cache and reinstall
cd frontend
npm cache clean --force
rm -rf node_modules package-lock.json
npm install

# Then run
npm run dev
```

---

#### Issue 3: "Port 5173 already in use"
**Error**: `Port 5173 already in use`

**Solution**:
```bash
# Find process using port 5173
Windows: netstat -ano | findstr :5173
Linux/Mac: lsof -i :5173

# Kill the process
Windows: taskkill /PID <process_id> /F
Linux/Mac: kill -9 <process_id>

# Or use different port
npm run dev -- --port 5174
```

---

#### Issue 4: "CORS error"
**Error**: `Access to XMLHttpRequest at 'http://localhost:8080/...' from origin 'http://localhost:5173' has been blocked by CORS policy`

**Solution**:
- Backend already has `@CrossOrigin(origins = "*")`
- Ensure backend is running on port 8080
- Clear browser cache (Ctrl+Shift+Del)
- Try in Incognito mode

---

#### Issue 5: "Vite hot reload not working"
**Error**: Changes not reflecting in browser

**Solution**:
```bash
# Hard refresh in browser
Ctrl+Shift+R (Windows)
Cmd+Shift+R (Mac)

# Or clear browser cache and hard refresh
# Or restart Vite server:
npm run dev
```

---

#### Issue 6: "Module not found: react-dom"
**Error**: `Cannot find module 'react-dom'`

**Solution**:
```bash
# Install missing dependency
npm install react-dom

# Or reinstall all
cd frontend
npm install
```

---

### 🔵 API Integration Issues

#### Issue 1: "GET request returns 404"
**Error**: `404 Not Found`

**Solution**:
```javascript
// Check API endpoint is correct
// http://localhost:8080/api/problem-statements?page=0&size=10

// Check backend is running
// Check console for exact error

// Verify CORS is enabled in backend
```

---

#### Issue 2: "POST request returns 400"
**Error**: `Bad Request`

**Solution**:
```javascript
// Check request body format
{
  "teamName": "...",
  "teamHeadName": "...",
  "teamHeadEmail": "..."
}

// Check JSON format is valid
// Check all required fields are present
// Check email format is valid
```

---

#### Issue 3: "Cannot read team from response"
**Error**: `Cannot read property 'data' of undefined`

**Solution**:
```javascript
// Check response structure in browser Network tab
// Ensure API returns expected format:
// { success: true, message: "...", data: {...} }

// Add error logging:
console.log('Response:', data);
```

---

### 🟢 Database Issues

#### Issue 1: "Foreign key constraint fails"
**Error**: `Cannot add or update a child row: a foreign key constraint fails`

**Solution**:
```sql
-- Issue is likely with data already in DB
-- Solution: Drop and recreate
DROP DATABASE hackathon_form_db;
CREATE DATABASE hackathon_form_db;

-- Or in backend, change ddl-auto:
spring.jpa.hibernate.ddl-auto=create
```

---

#### Issue 2: "Duplicate entry for key"
**Error**: `Duplicate entry 'Team Alpha' for key 'teamName'`

**Solution**:
```sql
-- Clear the tables
USE hackathon_form_db;
DELETE FROM teams;
DELETE FROM problem_statements;

-- Auto-increment will reset on application restart
```

---

#### Issue 3: "No default value for column"
**Error**: `Field 'selected_teams_count' doesn't have a default value`

**Solution**:
```bash
# Restart backend - it should recreate schema
# Make sure ddl-auto is set to 'create-drop' or 'create'

spring.jpa.hibernate.ddl-auto=create
```

---

### 🟣 Feature-Specific Issues

#### Issue 1: "Problem statements not appearing"
**Error**: PS grid is empty

**Solution**:
```bash
# Check backend logs for DataInitializer
# Should see: "Creating 25 problem statements..."

# If not, check:
# 1. Database connection is working
# 2. ProblemStatementService.createDefaultProblemStatements() is called
# 3. No errors in backend console
```

---

#### Issue 2: "Pagination buttons not working"
**Error**: Previous/Next buttons don't navigate

**Solution**:
```javascript
// Check totalPages value
// Check page state is updating

// Add debugging:
console.log('Current Page:', currentPage);
console.log('Total Pages:', totalPages);
```

---

#### Issue 3: "Cannot select problem statement"
**Error**: Select button doesn't work or shows error

**Solution**:
```
Check error message:
1. "You already selected" - Team already has PS
2. "Has reached max limit" - PS already has 2 teams
3. "Team not found" - Check team ID
4. "Problem statement not found" - Check PS ID
```

---

#### Issue 4: "Form validation not working"
**Error**: Can submit invalid data

**Solution**:
```javascript
// Check validateForm() function is called
// Check email regex pattern:
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// Add console logging:
console.log('Validation passed:', isValid);
```

---

### 💻 Environment & Setup Issues

#### Issue 1: "Environment variable not recognized"
**Error**: `$JAVA_HOME` not found

**Solution**:
```bash
# Windows:
set JAVA_HOME=C:\Program Files\Java\jdk-17
echo %JAVA_HOME%

# Linux/Mac:
export JAVA_HOME=/usr/libexec/java_home -v 17
echo $JAVA_HOME
```

---

#### Issue 2: "Can't access from another computer"
**Error**: Cannot reach application from different machine

**Solution**:
```bash
# Get your IP address
Windows: ipconfig
Linux/Mac: ifconfig

# Use IP instead of localhost:
http://<your-ip>:5173
http://<your-ip>:8080/api/...

# Ensure firewall allows ports 8080 and 5173
```

---

#### Issue 3: "Application works on localhost but not on IP"
**Error**: CORS or network issue

**Solution**:
```javascript
// Update backend CORS if needed:
@CrossOrigin(origins = "http://<your-ip>:5173")

// Or allow all:
@CrossOrigin(origins = "*")
```

---

### 🚀 Performance Issues

#### Issue 1: "Application is slow"
**Solution**:
```bash
# Check if backend is running
# Check database connection
# Check network latency
# Increase Java heap size:
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m"
```

---

#### Issue 2: "Many API calls causing slowness"
**Solution**:
```javascript
// Add caching in frontend
// Reduce pagination size initially
// Add loading indicators
// Implement debouncing for search
```

---

## 🆘 Getting More Help

### Check Logs

**Backend Logs:**
```
Look for error messages in terminal running backend
Search for "ERROR" or "Exception"
```

**Frontend Logs:**
```
Open browser DevTools: F12
Go to Console tab
Look for red error messages
Check Network tab for failed requests
```

---

### Debug Process

1. **Identify the issue**: Read error message carefully
2. **Check logs**: Backend terminal and browser console
3. **Verify setup**: Ports, database, services running
4. **Test endpoint**: Use Postman or curl
5. **Check configuration**: application.properties, API URLs
6. **Inspect network**: Use browser DevTools Network tab
7. **Review code**: Check recent changes
8. **Restart services**: Backend and frontend
9. **Clear cache**: Browser and npm cache
10. **Check documentation**: Review SETUP_GUIDE.md

---

### Useful Commands

```bash
# Backend troubleshooting
mvn clean install          # Clean build
mvn clean install -X       # Build with debug logging
mvn test                   # Run tests

# Frontend troubleshooting
npm cache clean --force    # Clear npm cache
npm list                   # List installed packages
npm audit                  # Check for vulnerabilities

# Database troubleshooting
mysql -u root -p          # Connect to MySQL
SHOW DATABASES;           # List databases
USE hackathon_form_db;    # Select database
SHOW TABLES;              # List tables
SELECT * FROM teams;      # View teams table

# Port troubleshooting
Windows: netstat -ano | findstr :8080     # Check port 8080
Linux/Mac: lsof -i :8080                  # Check port 8080
```

---

### Still Stuck?

1. **Review documentation**:
   - QUICK_START.md
   - SETUP_GUIDE.md
   - ARCHITECTURE.md

2. **Check code comments**: Look for hints in source code

3. **Test individually**: Test backend API separately

4. **Verify prerequisites**: Ensure all tools installed

5. **Start fresh**: 
   ```bash
   rm -rf node_modules package-lock.json
   npm install
   mvn clean install
   ```

---

**Remember**: Most issues are related to missing dependencies, incorrect configuration, or services not running. Check logs first! 🔍

