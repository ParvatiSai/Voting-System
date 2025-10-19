# UI Flow Diagram - College Voting System

## Application Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                         START APPLICATION                        │
│                          (Main.java)                            │
└───────────────────────────────┬─────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         LOGIN SCREEN                            │
│                        (LoginUI.java)                           │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐  │
│  │  Email:    [________________________]                    │  │
│  │  Password: [________________________]                    │  │
│  │                                                           │  │
│  │  [  Login  ]  [  Register  ]                            │  │
│  └─────────────────────────────────────────────────────────┘  │
└───────────────────────┬─────────────────────┬───────────────────┘
                        │                     │
            ┌───────────▼─────────┐  ┌────────▼──────────┐
            │  ADMIN LOGIN        │  │  STUDENT LOGIN    │
            └───────────┬─────────┘  └────────┬──────────┘
                        │                     │
                        │                     │
┌───────────────────────▼────────────────────────────────────────────┐
│                    ADMIN DASHBOARD                                 │
│                  (AdminDashboard.java)                             │
│                                                                    │
│  ┌──────────────┬─────────────────┬────────────────┐            │
│  │   Manage     │   Election      │   System       │            │
│  │  Candidates  │    Results      │     Info       │            │
│  └──────────────┴─────────────────┴────────────────┘            │
│                                                                    │
│  TAB 1: Manage Candidates                                        │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  [Add] [Edit] [Delete] [Refresh]                           │ │
│  │                                                             │ │
│  │  ┌──────────────────────────────────────────────────────┐ │ │
│  │  │ ID │ Name              │ Department                  │ │ │
│  │  ├────┼───────────────────┼───────────────────────────┤ │ │
│  │  │ 1  │ John Doe          │ Computer Science          │ │ │
│  │  │ 2  │ Jane Smith        │ Electrical Engineering    │ │ │
│  │  └──────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                    │
│  TAB 2: Election Results                                         │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  [Refresh] [Export]                                        │ │
│  │                                                             │ │
│  │  ELECTION RESULTS                                          │ │
│  │  Total Votes: 150                                          │ │
│  │                                                             │ │
│  │  John Doe (CS)        | 85 votes (56.7%)                  │ │
│  │  Jane Smith (EE)      | 65 votes (43.3%)                  │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                    │
│  [Logout]                                                         │
└────────────────────────────────────────────────────────────────────┘


┌────────────────────────────────────────────────────────────────────┐
│                      STUDENT VOTING PORTAL                         │
│                       (VotingUI.java)                              │
│                                                                    │
│  Logged in as: Alice Johnson                                      │
│                                                                    │
│  ┌──────────────────────────────────────────────────────────────┐ │
│  │  Select Candidate:                                           │ │
│  │  [ John Doe (Computer Science)        ▼]                    │ │
│  └──────────────────────────────────────────────────────────────┘ │
│                                                                    │
│  ┌──────────────────────────────────────────────────────────────┐ │
│  │  Candidate Information                                       │ │
│  │  ┌────────────────────────────────────────────────────────┐ │ │
│  │  │ Name:       John Doe                                   │ │ │
│  │  │                                                         │ │ │
│  │  │ Department: Computer Science                           │ │ │
│  │  │                                                         │ │ │
│  │  │ Candidate ID: 1                                        │ │ │
│  │  │                                                         │ │ │
│  │  │ Please review carefully before voting.                 │ │ │
│  │  └────────────────────────────────────────────────────────┘ │ │
│  └──────────────────────────────────────────────────────────────┘ │
│                                                                    │
│  [ Cast Vote ]  [ View Results ]  [ Logout ]                     │
└────────────────────────────────────────────────────────────────────┘


┌────────────────────────────────────────────────────────────────────┐
│                      ELECTION RESULTS                              │
│                       (ResultUI.java)                              │
│                                                                    │
│  ╔════════════════════════════════════════════════════════════╗  │
│  ║              ELECTION RESULTS                              ║  │
│  ╚════════════════════════════════════════════════════════════╝  │
│                                                                    │
│  Total Votes Cast: 150                                            │
│                                                                    │
│  ────────────────────────────────────────────────────────────    │
│                                                                    │
│  Rank #1                                                          │
│  Name:       John Doe 🏆 LEADING                                 │
│  Department: Computer Science                                     │
│  Votes:      85 (56.7%)                                          │
│  Progress:   [█████████████████████████████░░░░░░░░░░░░░░░░░]   │
│                                                                    │
│  Rank #2                                                          │
│  Name:       Jane Smith                                           │
│  Department: Electrical Engineering                               │
│  Votes:      65 (43.3%)                                          │
│  Progress:   [█████████████████████░░░░░░░░░░░░░░░░░░░░░░░░░]   │
│                                                                    │
│  ────────────────────────────────────────────────────────────    │
│                                                                    │
│  Last Updated: 2025-10-19 23:27:03                               │
│                                                                    │
│  [ Refresh Results ]  [ Close ]                                   │
└────────────────────────────────────────────────────────────────────┘
```

---

## User Journeys

### 👨‍💼 Admin Journey

1. **Login** → Enter admin credentials
2. **Dashboard** → See tabbed interface
3. **Manage Candidates** → Add/Edit/Delete candidates in table
4. **View Results** → Check election statistics with percentages
5. **System Info** → Review system capabilities
6. **Logout** → Return to login screen

### 👨‍🎓 Student Journey

1. **Login** → Enter student credentials
2. **Voting Portal** → View candidate list
3. **Select Candidate** → See detailed information
4. **Cast Vote** → Confirm and submit (blockchain secured)
5. **View Results** → Check live election results
6. **Logout** → Return to login screen

### 🆕 New User Journey

1. **Login Screen** → Click "Register"
2. **Registration Form** → Fill all fields (name, email, password, role)
3. **Validation** → System checks email format, password strength
4. **Success** → Return to login with credentials
5. **Login** → Access appropriate portal (Admin/Student)

---

## UI Components Hierarchy

```
Main.java (Entry Point)
│
└─── LoginUI
     ├─── Login Form
     │    ├── Email Field (with validation)
     │    ├── Password Field (with validation)
     │    ├── Login Button
     │    └── Register Button
     │
     ├─── Registration Dialog (Modal)
     │    ├── Full Name Field
     │    ├── Email Field (validated)
     │    ├── Password Field (validated)
     │    ├── Confirm Password Field
     │    ├── Role ComboBox
     │    ├── Submit Button
     │    └── Cancel Button
     │
     ├─── Success → AdminDashboard (if admin)
     └─── Success → VotingUI (if student)

AdminDashboard
│
├─── Header (user info, title)
│
├─── TabbedPane
│    ├─── Tab 1: Manage Candidates
│    │    ├── Button Panel (Add/Edit/Delete/Refresh)
│    │    ├── JTable (candidate list)
│    │    ├── Add Dialog (name, department)
│    │    ├── Edit Dialog (pre-filled fields)
│    │    └── Delete Confirmation
│    │
│    ├─── Tab 2: Election Results
│    │    ├── Button Panel (Refresh/Export)
│    │    └── Results TextArea (formatted)
│    │
│    └─── Tab 3: System Info
│         └── Info TextArea (system details)
│
└─── Logout Button

VotingUI
│
├─── Header (user info, title)
│
├─── Content Panel
│    ├── Candidate Selector ComboBox
│    └── Candidate Info Panel
│         └── Detailed Information TextArea
│
└─── Button Panel
     ├── Cast Vote Button (with confirmation)
     ├── View Results Button → opens ResultUI
     └── Logout Button

ResultUI (Standalone Window)
│
├─── Header (title)
│
├─── Results Display
│    └── Formatted TextArea
│         ├── Total votes
│         ├── Candidate rankings
│         ├── Vote counts & percentages
│         ├── Progress bars
│         └── Last updated timestamp
│
└─── Button Panel
     ├── Refresh Button
     └── Close Button
```

---

## Color Coding

```
┌──────────────┬─────────────┬──────────────────────┐
│   Component  │    Color    │      Usage           │
├──────────────┼─────────────┼──────────────────────┤
│ Login        │ Blue        │ Primary action       │
│ Register     │ Green       │ Positive action      │
│ Admin Header │ Purple      │ Admin branding       │
│ Student Hdr  │ Blue        │ Student branding     │
│ Results Hdr  │ Blue        │ Info display         │
│ Add/Success  │ Green       │ Positive action      │
│ Edit/Info    │ Light Blue  │ Modification         │
│ Delete/Error │ Red         │ Destructive action   │
│ Refresh      │ Orange      │ Refresh action       │
└──────────────┴─────────────┴──────────────────────┘
```

---

## Key Interactions

### Voting Flow
```
Select Candidate → View Info → Click "Cast Vote" 
  → Confirmation Dialog → Vote Recorded 
  → Button Disabled → Success Message
```

### Admin Candidate Management
```
Click "Add" → Fill Form → Validate → Save to DB 
  → Refresh Table → Success Message

Select Row → Click "Edit" → Modify Form → Validate 
  → Update DB → Refresh Table → Success Message

Select Row → Click "Delete" → Confirm Dialog 
  → Delete from DB → Refresh Table → Success Message
```

### Registration Flow
```
Click "Register" → Fill Form → Validate Each Field
  → Check Password Match → Hash Password 
  → Save to DB → Success → Return to Login
```

---

## Responsive Elements

✅ All dialogs centered on parent window  
✅ Scroll panes for dynamic content  
✅ Fixed window sizes for consistency  
✅ Proper button sizing (120-150px width)  
✅ Text areas with word wrap  
✅ Tables with column sizing  

---

## Error States

**Database Connection Failed:**
```
┌─────────────────────────────────────┐
│  ⚠️  Connection Error               │
│                                     │
│  Error connecting to database:      │
│  [error message]                    │
│                                     │
│         [    OK    ]                │
└─────────────────────────────────────┘
```

**Validation Failed:**
```
┌─────────────────────────────────────┐
│  ⚠️  Validation Error               │
│                                     │
│  Please enter a valid email         │
│  address.                           │
│                                     │
│         [    OK    ]                │
└─────────────────────────────────────┘
```

**Vote Already Cast:**
```
┌─────────────────────────────────────┐
│  ❌  Vote Failed                    │
│                                     │
│  Vote failed. You may have already  │
│  voted in this election.            │
│                                     │
│         [    OK    ]                │
└─────────────────────────────────────┘
```

---

**This completes the visual UI documentation!**
