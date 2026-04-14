// ReportSection.java - Shows library reports and statistics
//                      Summary, Most Issued, Fines, History

import java.util.ArrayList;

public class ReportSection {

    // Show reports menu
    static void menu() {
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("           REPORTS SECTION");
            System.out.println("==========================================");
            System.out.println("  1. Library Summary");
            System.out.println("  2. Most Issued Books");
            System.out.println("  3. Members with Fines");
            System.out.println("  4. All Transaction History");
            System.out.println("  5. Back to Main Menu");
            System.out.println("==========================================");
            System.out.print("  Enter your choice: ");

            int choice = Data.getIntInput();

            if (choice == 1) {
                librarySummary();
            } else if (choice == 2) {
                mostIssuedBooks();
            } else if (choice == 3) {
                membersWithFines();
            } else if (choice == 4) {
                allHistory();
            } else if (choice == 5) {
                return;  // go back to main menu
            } else {
                System.out.println(">> Invalid choice!");
            }
        }
    }


    // ---- 1. Library Summary ----
    static void librarySummary() {
        System.out.println("\n--- LIBRARY SUMMARY ---");

        // Count book stats
        int totalBooks = Data.books.size();
        int totalCopies = 0;
        int availableCopies = 0;
        for (int i = 0; i < Data.books.size(); i++) {
            totalCopies = totalCopies + Data.books.get(i).totalCopies;
            availableCopies = availableCopies + Data.books.get(i).availableCopies;
        }
        int issuedCopies = totalCopies - availableCopies;

        // Count member stats
        int totalMembers = Data.members.size();
        int activeMembers = 0;
        for (int i = 0; i < Data.members.size(); i++) {
            if (Data.members.get(i).isActive) {
                activeMembers = activeMembers + 1;
            }
        }

        // Count transaction stats
        int totalTransactions = Data.history.size();
        int currentlyIssued = 0;
        int returned = 0;
        double totalFines = 0;
        for (int i = 0; i < Data.history.size(); i++) {
            History h = Data.history.get(i);
            if (h.status.equals("ISSUED")) {
                currentlyIssued = currentlyIssued + 1;
            } else {
                returned = returned + 1;
            }
            totalFines = totalFines + h.fineAmount;
        }

        // Print summary
        System.out.println("  ==========================================");
        System.out.println("  BOOKS");
        System.out.println("    Total Unique Books   : " + totalBooks);
        System.out.println("    Total Copies         : " + totalCopies);
        System.out.println("    Available Copies     : " + availableCopies);
        System.out.println("    Issued Copies        : " + issuedCopies);
        System.out.println("  ------------------------------------------");
        System.out.println("  MEMBERS");
        System.out.println("    Total Members        : " + totalMembers);
        System.out.println("    Active Members       : " + activeMembers);
        System.out.println("    Inactive Members     : " + (totalMembers - activeMembers));
        System.out.println("  ------------------------------------------");
        System.out.println("  TRANSACTIONS");
        System.out.println("    Total Transactions   : " + totalTransactions);
        System.out.println("    Currently Issued     : " + currentlyIssued);
        System.out.println("    Returned             : " + returned);
        System.out.println("    Total Fines Collected: Rs " + totalFines);
        System.out.println("  ==========================================");
    }


    // ---- 2. Most Issued Books ----
    static void mostIssuedBooks() {
        System.out.println("\n--- MOST ISSUED BOOKS ---");

        if (Data.books.size() == 0) {
            System.out.println(">> No books in library!");
            return;
        }

        // Count how many times each book was issued
        int[] counts = new int[Data.books.size()];
        for (int i = 0; i < Data.books.size(); i++) {
            int bookId = Data.books.get(i).id;
            for (int j = 0; j < Data.history.size(); j++) {
                if (Data.history.get(j).bookId == bookId) {
                    counts[i] = counts[i] + 1;
                }
            }
        }

        // Print in order of most issued
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-3s | %-25s | %-15s | %-6s |%n", "ID", "Title", "Author", "Times");
        System.out.println("------------------------------------------------------------");

        boolean[] printed = new boolean[Data.books.size()];
        for (int round = 0; round < Data.books.size(); round++) {
            // Find the book with highest count that hasn't been printed yet
            int maxIndex = -1;
            int maxCount = -1;
            for (int i = 0; i < Data.books.size(); i++) {
                if (!printed[i] && counts[i] > maxCount) {
                    maxCount = counts[i];
                    maxIndex = i;
                }
            }
            // Print it if it has at least 1 issue
            if (maxIndex >= 0 && maxCount > 0) {
                printed[maxIndex] = true;
                Book b = Data.books.get(maxIndex);
                System.out.printf("| %-3d | %-25s | %-15s | %-6d |%n",
                        b.id, b.title, b.author, maxCount);
            }
        }
        System.out.println("------------------------------------------------------------");
    }


    // ---- 3. Members with Fines ----
    static void membersWithFines() {
        System.out.println("\n--- MEMBERS WITH FINES ---");

        boolean found = false;
        System.out.println("------------------------------------------------------");
        System.out.printf("| %-15s | %-20s | %-10s |%n", "Username", "Name", "Total Fine");
        System.out.println("------------------------------------------------------");

        for (int i = 0; i < Data.members.size(); i++) {
            Member m = Data.members.get(i);
            double fine = Data.getTotalFine(m.username);
            if (fine > 0) {
                found = true;
                System.out.printf("| %-15s | %-20s | Rs %-7.0f |%n", m.username, m.name, fine);
            }
        }
        System.out.println("------------------------------------------------------");

        if (!found) {
            System.out.println(">> No members have fines.");
        }
    }


    // ---- 4. All Transaction History ----
    static void allHistory() {
        System.out.println("\n--- ALL TRANSACTION HISTORY ---");

        if (Data.history.size() == 0) {
            System.out.println(">> No transactions yet.");
            return;
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-15s | %-12s | %-12s | %-12s | %-10s | %-6s |%n",
                "Rec", "Book Title", "Member", "Issue Date", "Due Date", "Return Date", "Status", "Fine");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < Data.history.size(); i++) {
            History h = Data.history.get(i);
            String ret = "---";
            if (h.returnDate.length() > 0) {
                ret = h.returnDate;
            }
            String fine = "---";
            if (h.fineAmount > 0) {
                fine = "Rs " + h.fineAmount;
            }
            System.out.printf("| %-4d | %-20s | %-15s | %-12s | %-12s | %-12s | %-10s | %-6s |%n",
                    h.recordId, h.bookTitle, h.memberUsername, h.issueDate, h.dueDate, ret, h.status, fine);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("  Total records: " + Data.history.size());
    }
}
