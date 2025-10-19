# College Voting System

A Java-based Voting System for College Elections using OOP, JDBC, Swing UI, and a simplified blockchain for secure vote recording.

## 🎯 Features

- **User Authentication** - Secure login and registration for Admin/Student roles
- **Election Management** - Full CRUD operations for candidates (Admin only)
- **Secure Voting** - Blockchain-based vote recording with SHA-256 hashing
- **Result Processing** - Real-time election results with visual progress bars
- **Role-Based Access** - Separate dashboards for Admin and Student users
- **Professional UI** - Modern Swing interface with comprehensive validation

## 🚀 Quick Start

### Prerequisites
- Java 17+ (Java 24.0.2 tested)
- MySQL Server (localhost:3306)
- MySQL Connector/J (`mysql-connector-j-9.4.0.jar` included)

### Running the Application

**Option 1: Using PowerShell Script (Recommended)**
```powershell
.\run.ps1
```

**Option 2: Manual Compilation and Run**
```powershell
# Compile
.\compile.ps1

# Run
java -cp "out;resources;mysql-connector-j-9.4.0.jar" app.Main
```

## 📋 Setup Instructions

### 1. Database Setup
Run the SQL schema to create the database:
```bash
mysql -u root -p < schema.sql
```

Or execute `schema.sql` in MySQL Workbench.

### 2. Configure Database Connection
Update `resources/db_config.properties` with your MySQL credentials:
```properties
db.url=jdbc:mysql://localhost:3306/voting_system
db.user=root
db.password=your_password
```

### 3. Run the Application
Use the `run.ps1` script or manual commands above.

## 📁 Project Structure

```
Voting System/
├── src/main/java/
│   ├── app/              # Application entry point
│   ├── dao/              # Data Access Objects (Database layer)
│   ├── model/            # Domain models (User, Candidate, Vote, Blockchain)
│   ├── service/          # Business logic layer
│   ├── ui/               # Swing UI components
│   └── util/             # Utility classes (Hashing, Validation, Config)
├── resources/
│   └── db_config.properties  # Database configuration
├── docs/                 # Original project documentation
├── documentation/        # UI and setup documentation
├── out/                  # Compiled classes (generated)
├── schema.sql           # Database schema
├── run.ps1              # Run script
├── compile.ps1          # Compile script
└── README.md            # This file
```

## 📚 Documentation

- **[Run Instructions](documentation/RUN_INSTRUCTIONS.md)** - Detailed setup and usage guide
- **[UI Enhancements](documentation/UI_ENHANCEMENTS.md)** - Complete list of UI features
- **[UI Flow Diagram](documentation/UI_FLOW_DIAGRAM.md)** - Visual application flow
- **[Completion Summary](documentation/COMPLETION_SUMMARY.md)** - Project overview
- **[UI Checklist](documentation/UI_CHECKLIST.md)** - Implementation checklist
- **[User Manual](docs/User_Manual.md)** - End-user guide
- **[Test Cases](docs/Test_Cases.md)** - Testing documentation
- **[Project Report](docs/Project_Report.md)** - Academic project report

## 🎨 Application Features

### For Students
- View all candidates with department information
- Cast vote with confirmation (one vote per student)
- View live election results
- Secure logout

### For Admins
- **Manage Candidates** - Add, edit, delete candidates with table view
- **View Results** - Detailed election results with percentages
- **System Information** - View system capabilities and security features

## 🔒 Security Features

- Password hashing with SHA-256
- Email validation with regex
- One vote per student enforcement
- Blockchain integrity verification
- Role-based access control

## 🛠️ Technologies Used

- **Java 17+** - Core programming language
- **MySQL** - Database management
- **JDBC** - Database connectivity
- **Swing** - GUI framework
- **SHA-256** - Cryptographic hashing
- **Blockchain** - Vote integrity

## 📝 Usage

1. **Register** a new account (Admin or Student)
2. **Login** with your credentials
3. **Admin** can manage candidates and view results
4. **Students** can cast votes and view results
5. All votes are recorded on a blockchain for integrity

## 🐛 Troubleshooting

See [Run Instructions](documentation/RUN_INSTRUCTIONS.md) for detailed troubleshooting steps.

Common issues:
- **ClassNotFoundException**: Ensure MySQL connector JAR is in project root
- **SQLException**: Verify database is running and credentials are correct
- **Compilation errors**: Run `.\compile.ps1` to see detailed errors

## 👥 Contributors

- Academic Project - College Voting System

## 📄 License

This is an academic project for educational purposes.
