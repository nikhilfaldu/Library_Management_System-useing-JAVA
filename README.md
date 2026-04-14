# Library Management System

A simple **Java console-based** Library Management System built as a college project.
This project helps manage books, members, issue/return books, and generate reports — all from the terminal.

---

## Features

### 1. Login System
- Username and password based login
- 3 login attempts allowed
- Logout and switch user support

### 2. Library Section
| Option | Description |
|--------|-------------|
| Add Book | Add a new book with title, author, category, and copies |
| View All Books | See all books in the library |
| Search Books | Search by title, author, category, or book ID |
| Issue Book | Issue a book to a member |
| Return Book | Return a book with fine calculation |
| Update Book | Edit book details |
| Delete Book | Remove a book from library |
| View Issued Books | See all currently issued books |

### 3. Account Section
| Option | Description |
|--------|-------------|
| My Profile | View your profile details |
| My Issued Books | See books you currently have |
| My Book History | See all your past issue/return records |
| My Fines | See your fine details |
| View All Members | See all registered members |
| Add New Member | Register a new member (can login after adding) |
| Edit Member | Update member details |
| Delete Member | Deactivate a member |
| View Member History | See any member's book history |

### 4. Reports Section
| Option | Description |
|--------|-------------|
| Library Summary | Overall statistics of library |
| Most Issued Books | Books ranked by number of times issued |
| Members with Fines | Members who have pending fines |
| All Transaction History | Complete issue/return history |

---

## Project Structure

```
library_managemnt_system/
│
├── LibraryManagementSystem.java   --> Main file (run this)
├── Data.java                      --> All data, dummy data, helper methods
├── Book.java                      --> Book class
├── Member.java                    --> Member class
├── History.java                   --> Issue/Return history class
├── LibrarySection.java            --> Library operations (add, issue, return, etc.)
├── AccountSection.java            --> Account & member management
├── ReportSection.java             --> Reports and statistics
└── README.md                      --> This file
```

---

## How to Run

### Prerequisites
- **Java JDK** must be installed on your computer
- To check if Java is installed, open terminal and type:
```
java -version
```
- If not installed, download from: https://www.oracle.com/java/technologies/downloads/

### Steps to Run

**Step 1:** Open Command Prompt / Terminal

**Step 2:** Navigate to project folder:
```
cd E:\library_managemnt_system
```

**Step 3:** Compile all Java files:
```
javac *.java
```

**Step 4:** Run the program:
```
java LibraryManagementSystem
```

**Step 5:** Login with any of these credentials:

| Username | Password | Name |
|----------|----------|------|
| admin | 1234 | Admin |
| rahul | pass1 | Rahul Patel |
| priya | pass2 | Priya Shah |
| amit | pass3 | Amit Kumar |
| sneha | pass4 | Sneha Joshi |

---

## Dummy Data (Pre-loaded for Testing)

### Books (10 books)
| ID | Title | Author | Category | Copies |
|----|-------|--------|----------|--------|
| 1 | Java Programming | James Gosling | Programming | 3 |
| 2 | Data Structures | Narasimha Karumanchi | Computer Science | 2 |
| 3 | The Alchemist | Paulo Coelho | Fiction | 4 |
| 4 | Wings of Fire | APJ Abdul Kalam | Biography | 2 |
| 5 | Let Us C | Yashavant Kanetkar | Programming | 3 |
| 6 | Python Crash Course | Eric Matthes | Programming | 2 |
| 7 | Clean Code | Robert C. Martin | Programming | 1 |
| 8 | Harry Potter | J.K. Rowling | Fiction | 5 |
| 9 | Atomic Habits | James Clear | Self Help | 3 |
| 10 | The White Tiger | Aravind Adiga | Fiction | 2 |

### Currently Issued Books
| Member | Book | Issue Date | Due Date |
|--------|------|------------|----------|
| Rahul | Java Programming | 01-04-2026 | 15-04-2026 |
| Amit | Wings of Fire | 10-04-2026 | 24-04-2026 |
| Sneha | Harry Potter | 05-04-2026 | 19-04-2026 |
| Admin | Clean Code | 12-04-2026 | 26-04-2026 |

### Returned Books (History)
| Member | Book | Returned On | Fine |
|--------|------|-------------|------|
| Priya | The Alchemist | 05-04-2026 | Rs 4 (2 days late) |
| Rahul | Let Us C | 28-03-2026 | No fine |
| Admin | Data Structures | 20-03-2026 | Rs 10 (5 days late) |

---

## Library Rules
- Each member can issue **maximum 3 books** at a time
- Books must be returned within **14 days**
- Late return fine: **Rs 2 per day**

---

## Technologies Used
- **Language:** Java
- **Concepts:** Classes, Objects, ArrayList, Loops, Conditional Statements, Methods, Scanner
- **Type:** Console / Terminal based application

