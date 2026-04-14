// LibrarySection.java - Handles all book-related operations
//                       Add, View, Search, Issue, Return, Update, Delete books

import java.util.ArrayList;

public class LibrarySection {

    // Show library menu and handle user choice
    static void menu() {
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("           LIBRARY SECTION");
            System.out.println("==========================================");
            System.out.println("  1. Add Book");
            System.out.println("  2. View All Books");
            System.out.println("  3. Search Books");
            System.out.println("  4. Issue Book");
            System.out.println("  5. Return Book");
            System.out.println("  6. Update Book");
            System.out.println("  7. Delete Book");
            System.out.println("  8. View Issued Books");
            System.out.println("  9. Back to Main Menu");
            System.out.println("==========================================");
            System.out.print("  Enter your choice: ");

            int choice = Data.getIntInput();

            if (choice == 1) {
                addBook();
            } else if (choice == 2) {
                viewAllBooks();
            } else if (choice == 3) {
                searchBooks();
            } else if (choice == 4) {
                issueBook();
            } else if (choice == 5) {
                returnBook();
            } else if (choice == 6) {
                updateBook();
            } else if (choice == 7) {
                deleteBook();
            } else if (choice == 8) {
                viewIssuedBooks();
            } else if (choice == 9) {
                return;  // go back to main menu
            } else {
                System.out.println(">> Invalid choice!");
            }
        }
    }


    // ---- 1. Add a new book to library ----
    static void addBook() {
        System.out.println("\n--- ADD NEW BOOK ---");

        System.out.print("Enter Book Title    : ");
        String title = Data.sc.nextLine();

        System.out.print("Enter Author Name   : ");
        String author = Data.sc.nextLine();

        System.out.print("Enter Category      : ");
        String category = Data.sc.nextLine();

        System.out.print("Enter No. of Copies : ");
        int copies = Data.getIntInput();

        // Check copies should be at least 1
        if (copies <= 0) {
            System.out.println(">> Copies must be at least 1.");
            return;
        }

        // Create new book and add to list
        Book newBook = new Book(Data.nextBookId, title, author, category, copies);
        Data.nextBookId = Data.nextBookId + 1;
        Data.books.add(newBook);

        System.out.println(">> Book added successfully!");
        System.out.println("   Book ID: " + newBook.id + " | Copies: " + copies);
    }


    // ---- 2. View all books in library ----
    static void viewAllBooks() {
        System.out.println("\n--- ALL BOOKS IN LIBRARY ---");

        if (Data.books.size() == 0) {
            System.out.println(">> No books in library!");
            return;
        }

        printBookTable(Data.books);
        System.out.println("  Total Books: " + Data.books.size());
    }


    // ---- 3. Search books by title, author, category, or ID ----
    static void searchBooks() {
        while (true) {
            System.out.println("\n--- SEARCH BOOKS ---");
            System.out.println("  1. Search by Title");
            System.out.println("  2. Search by Author");
            System.out.println("  3. Search by Category");
            System.out.println("  4. Search by Book ID");
            System.out.println("  5. Back");
            System.out.print("  Enter your choice: ");

            int choice = Data.getIntInput();

            // Go back
            if (choice == 5) {
                return;
            }

            // Search by ID
            if (choice == 4) {
                System.out.print("Enter Book ID: ");
                int id = Data.getIntInput();
                Book b = Data.findBookById(id);
                if (b == null) {
                    System.out.println(">> No book found with ID: " + id);
                } else {
                    ArrayList<Book> result = new ArrayList<>();
                    result.add(b);
                    printBookTable(result);
                }
                continue;
            }

            // Search by keyword (title, author, or category)
            if (choice < 1 || choice > 4) {
                System.out.println(">> Invalid choice!");
                continue;
            }

            System.out.print("Enter search keyword: ");
            String keyword = Data.sc.nextLine().toLowerCase();
            ArrayList<Book> results = new ArrayList<>();

            // Loop through all books and find matches
            for (int i = 0; i < Data.books.size(); i++) {
                Book b = Data.books.get(i);
                boolean match = false;

                if (choice == 1) {
                    // Search by title
                    match = b.title.toLowerCase().contains(keyword);
                } else if (choice == 2) {
                    // Search by author
                    match = b.author.toLowerCase().contains(keyword);
                } else if (choice == 3) {
                    // Search by category
                    match = b.category.toLowerCase().contains(keyword);
                }

                if (match) {
                    results.add(b);
                }
            }

            if (results.size() == 0) {
                System.out.println(">> No books found matching: " + keyword);
            } else {
                System.out.println(">> Found " + results.size() + " book(s):");
                printBookTable(results);
            }
        }
    }


    // ---- 4. Issue a book to a member ----
    static void issueBook() {
        System.out.println("\n--- ISSUE BOOK ---");

        // Show all members
        System.out.println("\nAvailable Members:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("| %-4s | %-15s | %-20s | %-8s |%n", "ID", "Username", "Name", "Issued");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < Data.members.size(); i++) {
            Member m = Data.members.get(i);
            if (m.isActive) {
                int issued = Data.countIssuedBooks(m.username);
                System.out.printf("| %-4d | %-15s | %-20s | %-8d |%n", m.id, m.username, m.name, issued);
            }
        }
        System.out.println("----------------------------------------------------------");

        // Ask which member
        System.out.print("\nEnter Member Username: ");
        String memberUser = Data.sc.nextLine().trim();
        Member member = Data.findMember(memberUser);

        if (member == null) {
            System.out.println(">> Member not found or inactive: " + memberUser);
            return;
        }

        // Check if member already has 3 books (max limit)
        int currentIssued = Data.countIssuedBooks(memberUser);
        if (currentIssued >= 3) {
            System.out.println(">> Member already has 3 books issued. Return a book first.");
            return;
        }

        // Ask which book
        System.out.print("Enter Book ID to issue: ");
        int id = Data.getIntInput();

        Book book = Data.findBookById(id);
        if (book == null) {
            System.out.println(">> Book not found with ID: " + id);
            return;
        }

        // Check if copies are available
        if (book.availableCopies <= 0) {
            System.out.println(">> No copies available! All " + book.totalCopies + " copies are issued.");
            return;
        }

        // Get dates
        System.out.print("Enter issue date (e.g. 14-04-2026): ");
        String issueDate = Data.sc.nextLine();

        System.out.print("Enter due date   (e.g. 28-04-2026): ");
        String dueDate = Data.sc.nextLine();

        // Issue the book
        book.availableCopies = book.availableCopies - 1;

        History newRecord = new History(Data.nextRecordId, member.username, member.name,
                book.title, book.id, issueDate, dueDate);
        Data.nextRecordId = Data.nextRecordId + 1;
        Data.history.add(newRecord);

        System.out.println("\n>> Book issued successfully!");
        System.out.println("   Book      : " + book.title);
        System.out.println("   Issued To : " + member.name + " (" + member.username + ")");
        System.out.println("   Issue Date: " + issueDate);
        System.out.println("   Due Date  : " + dueDate);
        System.out.println("   (Return before due date to avoid fine)");
    }


    // ---- 5. Return a book ----
    static void returnBook() {
        System.out.println("\n--- RETURN BOOK ---");

        // Find all currently issued records
        ArrayList<History> issuedRecords = new ArrayList<>();
        for (int i = 0; i < Data.history.size(); i++) {
            if (Data.history.get(i).status.equals("ISSUED")) {
                issuedRecords.add(Data.history.get(i));
            }
        }

        if (issuedRecords.size() == 0) {
            System.out.println(">> No books are currently issued.");
            return;
        }

        // Show issued books
        System.out.println("Currently Issued Books:");
        printHistoryTable(issuedRecords);

        // Ask which record to return
        System.out.print("\nEnter Record ID to return: ");
        int recordId = Data.getIntInput();

        // Find the record
        History record = null;
        for (int i = 0; i < Data.history.size(); i++) {
            if (Data.history.get(i).recordId == recordId && Data.history.get(i).status.equals("ISSUED")) {
                record = Data.history.get(i);
                break;
            }
        }

        if (record == null) {
            System.out.println(">> Record not found or already returned.");
            return;
        }

        // Show book details
        System.out.println("\n  Book Title : " + record.bookTitle);
        System.out.println("  Issued To  : " + record.memberName + " (" + record.memberUsername + ")");
        System.out.println("  Issue Date : " + record.issueDate);
        System.out.println("  Due Date   : " + record.dueDate);

        // Get return date
        System.out.print("\nEnter return date (e.g. 20-04-2026): ");
        String returnDate = Data.sc.nextLine();

        // Ask if there is a late fine
        System.out.print("Is this a late return? (yes/no): ");
        String isLate = Data.sc.nextLine();

        double fine = 0;
        if (isLate.equalsIgnoreCase("yes")) {
            System.out.print("Enter number of days late: ");
            int daysLate = Data.getIntInput();
            fine = daysLate * Data.FINE_PER_DAY;
            System.out.println("  ** Fine: Rs " + fine + " (Rs " + Data.FINE_PER_DAY + " x " + daysLate + " days) **");
        } else {
            System.out.println("  No fine. Returned on time!");
        }

        // Confirm return
        System.out.print("\nConfirm return? (yes/no): ");
        String confirm = Data.sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            // Update the record
            record.returnDate = returnDate;
            record.status = "RETURNED";
            record.fineAmount = fine;

            // Increase available copies
            Book book = Data.findBookById(record.bookId);
            if (book != null) {
                book.availableCopies = book.availableCopies + 1;
            }

            System.out.println(">> Book \"" + record.bookTitle + "\" returned successfully on " + returnDate);
            if (fine > 0) {
                System.out.println(">> Fine charged: Rs " + fine);
            }
        } else {
            System.out.println(">> Return cancelled.");
        }
    }


    // ---- 6. Update book details ----
    static void updateBook() {
        System.out.println("\n--- UPDATE BOOK ---");

        System.out.print("Enter Book ID to update: ");
        int id = Data.getIntInput();

        Book book = Data.findBookById(id);
        if (book == null) {
            System.out.println(">> Book not found with ID: " + id);
            return;
        }

        // Show current details
        System.out.println("  Current Title    : " + book.title);
        System.out.println("  Current Author   : " + book.author);
        System.out.println("  Current Category : " + book.category);
        System.out.println("  Current Copies   : " + book.totalCopies);
        System.out.println("\n(Press Enter to keep current value)");

        // Get new values
        System.out.print("New Title    : ");
        String title = Data.sc.nextLine();
        if (title.length() > 0) {
            book.title = title;
        }

        System.out.print("New Author   : ");
        String author = Data.sc.nextLine();
        if (author.length() > 0) {
            book.author = author;
        }

        System.out.print("New Category : ");
        String category = Data.sc.nextLine();
        if (category.length() > 0) {
            book.category = category;
        }

        System.out.print("New Total Copies (0 to skip): ");
        int copies = Data.getIntInput();
        if (copies > 0) {
            int issuedCopies = book.totalCopies - book.availableCopies;
            if (copies < issuedCopies) {
                System.out.println(">> Cannot set copies less than currently issued (" + issuedCopies + ")");
            } else {
                book.availableCopies = copies - issuedCopies;
                book.totalCopies = copies;
            }
        }

        System.out.println(">> Book updated successfully!");
    }


    // ---- 7. Delete a book ----
    static void deleteBook() {
        System.out.println("\n--- DELETE BOOK ---");

        System.out.print("Enter Book ID to delete: ");
        int id = Data.getIntInput();

        Book book = Data.findBookById(id);
        if (book == null) {
            System.out.println(">> Book not found with ID: " + id);
            return;
        }

        // Check if any copies are issued
        int issuedCopies = book.totalCopies - book.availableCopies;
        if (issuedCopies > 0) {
            System.out.println(">> Cannot delete! " + issuedCopies + " copy(s) are currently issued.");
            return;
        }

        // Confirm delete
        System.out.println("  Book: " + book.title + " by " + book.author);
        System.out.print("  Are you sure you want to delete? (yes/no): ");
        String confirm = Data.sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            Data.books.remove(book);
            System.out.println(">> Book deleted successfully!");
        } else {
            System.out.println(">> Delete cancelled.");
        }
    }


    // ---- 8. View all currently issued books ----
    static void viewIssuedBooks() {
        System.out.println("\n--- CURRENTLY ISSUED BOOKS ---");

        ArrayList<History> issuedRecords = new ArrayList<>();
        for (int i = 0; i < Data.history.size(); i++) {
            if (Data.history.get(i).status.equals("ISSUED")) {
                issuedRecords.add(Data.history.get(i));
            }
        }

        if (issuedRecords.size() == 0) {
            System.out.println(">> No books are currently issued.");
            return;
        }

        printHistoryTable(issuedRecords);
        System.out.println("  Total issued: " + issuedRecords.size());
    }


    // ---- Print book table ----
    static void printBookTable(ArrayList<Book> list) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-22s | %-18s | %-16s | %-6s | %-9s |%n",
                "ID", "Title", "Author", "Category", "Total", "Available");
        System.out.println("------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            Book b = list.get(i);
            System.out.printf("| %-3d | %-22s | %-18s | %-16s | %-6d | %-9d |%n",
                    b.id, b.title, b.author, b.category, b.totalCopies, b.availableCopies);
        }
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    // ---- Print history table ----
    static void printHistoryTable(ArrayList<History> list) {
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-15s | %-12s | %-12s | %-12s | %-6s |%n",
                "Rec", "Book Title", "Member", "Issue Date", "Due Date", "Return Date", "Fine");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            History h = list.get(i);
            String ret = h.returnDate.length() == 0 ? "---" : h.returnDate;
            String fine = h.fineAmount > 0 ? "Rs " + h.fineAmount : "---";
            System.out.printf("| %-4d | %-20s | %-15s | %-12s | %-12s | %-12s | %-6s |%n",
                    h.recordId, h.bookTitle, h.memberUsername, h.issueDate, h.dueDate, ret, fine);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}
