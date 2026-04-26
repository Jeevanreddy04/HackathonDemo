# Quick Start Guide

## 🚀 Quick Setup (5 Minutes)

### Prerequisites Check
- ✓ Java 17+ installed
- ✓ Maven 3.6+ installed
- ✓ Node.js 16+ installed
- ✓ MySQL 5.7+ running

### Step 1: Create Database
```sql
CREATE DATABASE hackathon_form_db;
```

### Step 2: Terminal 1 - Start Backend

```bash
# Navigate to backend directory
cd backend

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

**Output should show:**
```
Started BackendApplication in X.XXX seconds
Server listening on port 8080
```

### Step 3: Terminal 2 - Start Frontend

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

**Output should show:**
```
VITE v8.0.10  ready in XXX ms
➜  Local:   http://localhost:5173/
```

### Step 4: Access Application

Open browser and go to: **http://localhost:5173**

---

## 📝 Testing the Application

### Test Case 1: Register First Team
1. Enter Team Name: "Team Alpha"
2. Enter Team Head: "John Doe"
3. Enter Email: "john@example.com"
4. Click "Register Team"
5. Should see success and navigate to PS selection

### Test Case 2: Select Problem Statement
1. Browse PS cards (10 per page)
2. Click "Select" on any PS
3. Should see success message
4. Button should change to "Selected" (disabled)
5. Should see "✓ Problem statement selected" notice

### Test Case 3: Try Duplicate Selection
1. Try to click another PS "Select" button
2. Should see error: "You already selected a problem statement"
3. Previous selection remains unchanged

### Test Case 4: Fill Problem Statement to Capacity
1. Register Team 2 with different email
2. Select same PS as Team 1
3. Register Team 3 with different email
4. Try to select same PS
5. Should see error: "This problem statement has reached maximum team selection limit"
6. PS should show "Full" badge

### Test Case 5: Pagination
1. Click "Next →" to see more PS
2. Verify page number updates
3. Click "← Previous" to go back
4. Buttons should be disabled at boundaries

---

## 🛠️ Troubleshooting

### Backend Won't Start
- **Check MySQL is running**
  ```bash
  mysql -u root -p
  ```
- **Check port 8080 is free**
  ```bash
  # Windows
  netstat -ano | findstr :8080
  ```
- **Check Java version**
  ```bash
  java -version
  ```

### Frontend Won't Start
- **Clear npm cache**
  ```bash
  npm cache clean --force
  npm install
  ```
- **Check port 5173 is free**
- **Check Node version**
  ```bash
  node -v
  ```

### Database Connection Error
- **Verify MySQL credentials in application.properties**
- **Check database exists**
  ```sql
  SHOW DATABASES;
  ```

### API Calls Failing
- **Ensure backend is running on port 8080**
- **Check browser console for CORS errors**
- **Verify endpoint URLs in frontend components**

---

## 📊 Database Check

### View Created Tables
```sql
USE hackathon_form_db;
SHOW TABLES;
```

### Check Teams Table
```sql
SELECT * FROM teams;
```

### Check Problem Statements Table
```sql
SELECT * FROM problem_statements;
```

### Count Selections per PS
```sql
SELECT ps.id, ps.title, ps.selected_teams_count, COUNT(t.id) as actual_count
FROM problem_statements ps
LEFT JOIN teams t ON ps.id = t.problem_statement_id
GROUP BY ps.id;
```

---

## 🔄 Common Commands

### Build Backend
```bash
cd backend
mvn clean install
```

### Run Backend Tests
```bash
cd backend
mvn test
```

### Build Frontend
```bash
cd frontend
npm run build
```

### Lint Frontend
```bash
cd frontend
npm run lint
```

### Stop Backend (Ctrl+C in terminal)
```bash
# Then kill the process if needed
# Windows
taskkill /PID <process_id> /F

# Linux/Mac
kill -9 <process_id>
```

---

## 📱 Accessing from Mobile/Laptop

If you want to access from another machine:

### 1. Get Your IP Address
```bash
# Windows
ipconfig
# Look for IPv4 Address under your network adapter

# Linux/Mac
ifconfig
# Look for inet address
```

### 2. Update Frontend (if accessing from different machine)
Edit API base URL in components if needed, or use:
```bash
http://<your-ip>:8080/api/...
```

### 3. Access from Another Device
```
http://<your-ip>:5173
```

---

## 🎯 Next Steps

- Review `SETUP_GUIDE.md` for detailed setup
- Review `IMPLEMENTATION_SUMMARY.md` for architecture details
- Check `HELP.md` in backend folder for Spring Boot help

---

## ⚡ Performance Tips

1. **First Load**: Database initialization takes a few seconds
2. **Clear Browser Cache**: If UI doesn't update properly
3. **Hot Reload**: Frontend supports hot module replacement (HMR)
4. **Dev Tools**: Use browser DevTools (F12) to debug

---

## 📞 Support

For issues or questions:
1. Check error messages in console
2. Review `SETUP_GUIDE.md`
3. Check backend logs (terminal)
4. Check browser console (F12)
5. Review API responses in Network tab

---

**Ready to start? Run the commands above and enjoy! 🎉**
