# Running the College Voting System

## Prerequisites

✅ Java 17+ installed (you have Java 24.0.2)
✅ MySQL Connector/J downloaded (`mysql-connector-j-9.4.0.jar` in project root)
⚠️ MySQL database setup (see Database Setup below)

## Quick Start

### Option 1: Using PowerShell Script (Recommended)

```powershell
.\run.ps1
```

This script will:
- Compile all Java sources
- Include the MySQL connector in classpath
- Launch the application

### Option 2: Manual Steps

1. **Compile:**
```powershell
.\compile.ps1
```

2. **Run:**
```powershell
java -cp "out;resources;mysql-connector-j-9.4.0.jar" app.Main
```

## Database Setup

Before running the application for the first time:

### 1. Start MySQL Server
Make sure MySQL is running on `localhost:3306`

### 2. Create Database and Tables
Run the SQL script:

```bash
mysql -u root -p < schema.sql
```

Or manually execute the contents of `schema.sql` in MySQL Workbench or command line.

### 3. Update Database Configuration
Edit `resources/db_config.properties` if needed:

```properties
db.url=jdbc:mysql://localhost:3306/voting_system
db.user=root
db.password=1234
```

## Application Features

### 1. Login/Registration
- **Default Admin:** admin@example.com / hashed_password (update in DB)
- **Default Student:** student@example.com / hashed_password (update in DB)
- **Register new users** through the registration form

### 2. Student Portal
- View all candidates with department information
- Cast vote (one vote per student)
- View live election results
- Logout functionality

### 3. Admin Dashboard
- **Manage Candidates Tab:**
  - Add new candidates
  - Edit existing candidates
  - Delete candidates
  - View candidate table
  
- **Election Results Tab:**
  - View detailed results with percentages
  - See candidate names and departments
  - Export results (placeholder)
  
- **System Info Tab:**
  - View system information
  - Blockchain details
  - Security features

## UI Improvements Implemented

✅ **Professional Design:**
- Modern color scheme
- Proper spacing and padding
- Responsive layouts
- Centered windows

✅ **Enhanced Forms:**
- Input validation
- Email format checking
- Password strength validation
- Confirmation dialogs

✅ **Better Navigation:**
- Logout buttons
- Tab-based admin interface
- Modal dialogs for forms
- Progress feedback

✅ **Error Handling:**
- Database connection errors
- Validation errors
- User-friendly messages
- Exception logging

✅ **Rich Results Display:**
- Candidate names and departments
- Vote percentages
- Visual progress bars
- Real-time updates

## Troubleshooting

### "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
- Ensure `mysql-connector-j-9.4.0.jar` is in the project root
- Check the classpath includes the JAR file

### "SQLException: Access denied for user"
- Verify MySQL credentials in `resources/db_config.properties`
- Ensure MySQL user has proper permissions

### "SQLException: Unknown database 'voting_system'"
- Run `schema.sql` to create the database
- Check database name in `db_config.properties`

### Compilation Errors
```powershell
# Clean and recompile
Remove-Item -Recurse -Force out
.\compile.ps1
```

## Testing the Application

1. **Register as Admin and Student**
2. **Login as Admin:**
   - Add 3-4 candidates with different departments
   
3. **Login as Student:**
   - View candidates
   - Cast a vote
   - View results

4. **Login as Admin again:**
   - Check election results
   - Edit/delete candidates
   - View system info

## Project Structure

```
Voting System/
├── src/main/java/
│   ├── app/          # Main entry point
│   ├── dao/          # Database access layer
│   ├── model/        # Domain models
│   ├── service/      # Business logic
│   ├── ui/           # Swing UI components (ENHANCED!)
│   └── util/         # Utilities
├── resources/
│   └── db_config.properties
├── out/              # Compiled classes
├── schema.sql        # Database schema
├── run.ps1          # Run script
└── compile.ps1      # Compile script
```

## Next Steps

After UI testing is complete, we'll work on:
- Database initialization with sample data
- Blockchain verification features
- Additional security enhancements
- Report generation
