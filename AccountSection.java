// AccountSection.java - Handles member/account related operations
//                       Profile, Issued Books, History, Fines, Member Management

import java.util.ArrayList;

public class AccountSection {

    // Show account menu and handle user choice
    static void menu() {
        while (true) {
            // Get logged in member's name
            Member loggedIn = Data.findMember(Data.loggedInUser);
            String displayName = Data.loggedInUser;
            if (loggedIn != null) {
                displayName = loggedIn.name;
            }

            System.out.println("\n==========================================");
            System.out.println("           ACCOUNT SECTION");
            System.out.println("  Logged in as: " + displayName);
            System.out.println("==========================================");
            System.out.println("  1. My Profile");
            System.out.println("  2. My Issued Books");
            System.out.println("  3. My Book History");
            System.out.println("  4. My Fines");
            System.out.println("  5. View All Members");
            System.out.println("  6. Add New Member");
            System.out.println("  7. Edit Member");
            System.out.println("  8. Delete Member");
            System.out.println("  9. View Member's Book History");
            System.out.println("  10. Back to Main Menu");
            System.out.println("==========================================");
            System.out.print("  Enter your choice: ");

            int choice = Data.getIntInput();

            if (choice == 1) {
                myProfile();
            } else if (choice == 2) {
                myIssuedBooks();
            } else if (choice == 3) {
                myBookHistory();
            } else if (choice == 4) {
                myFines();
            } else if (choice == 5) {
                viewAllMembers();
            } else if (choice == 6) {
                addNewMember();
            } else if (choice == 7) {
                editMember();
            } else if (choice == 8) {
                deleteMember();
            } else if (choice == 9) {
                viewMemberHistory();
            } else if (choice == 10) {
                return;  // go back to main menu
            } else {
                System.out.println(">> Invalid choice!");
            }
        }
    }


    // ---- 1. Show my profile ----
    static void myProfile() {
        System.out.println("\n--- MY PROFILE ---");

        Member m = Data.findMember(Data.loggedInUser);
        if (m == null) {
            System.out.println(">> Profile not found!");
            return;
        }

        System.out.println("  Member ID   : " + m.id);
        System.out.println("  Name        : " + m.name);
        System.out.println("  Username    : " + m.username);
        System.out.println("  Phone       : " + m.phone);
        System.out.println("  Email       : " + m.email);
        System.out.println("  Join Date   : " + m.joinDate);
        System.out.println("  Status      : " + (m.isActive ? "Active" : "Inactive"));
        System.out.println("  Books Issued: " + Data.countIssuedBooks(m.username));
        System.out.println("  Total Fine  : Rs " + Data.getTotalFine(m.username));
    }


    // ---- 2. Show my currently issued books ----
    static void myIssuedBooks() {
        System.out.println("\n--- MY ISSUED BOOKS ---");

        ArrayList<History> myBooks = new ArrayList<>();
        for (int i = 0; i < Data.history.size(); i++) {
            History h = Data.history.get(i);
            if (h.memberUsername.equals(Data.loggedInUser) && h.status.equals("ISSUED")) {
                myBooks.add(h);
            }
        }

        if (myBooks.size() == 0) {
            System.out.println(">> You have no books currently issued.");
            return;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-22s | %-12s | %-12s |%n",
                "BkID", "Title", "Issue Date", "Due Date");
        System.out.println("------------------------------------------------------------------------");
        for (int i = 0; i < myBooks.size(); i++) {
            History h = myBooks.get(i);
            System.out.printf("| %-4d | %-22s | %-12s | %-12s |%n",
                    h.bookId, h.bookTitle, h.issueDate, h.dueDate);
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("  Total issued: " + myBooks.size());
    }


    // ---- 3. Show my complete book history ----
    static void myBookHistory() {
        System.out.println("\n--- MY BOOK HISTORY ---");
        printHistoryForMember(Data.loggedInUser);
    }


    // ---- 4. Show my fines ----
    static void myFines() {
        System.out.println("\n--- MY FINES ---");

        double totalFine = 0;
        ArrayList<History> fineRecords = new ArrayList<>();

        for (int i = 0; i < Data.history.size(); i++) {
            History h = Data.history.get(i);
            if (h.memberUsername.equals(Data.loggedInUser) && h.fineAmount > 0) {
                fineRecords.add(h);
                totalFine = totalFine + h.fineAmount;
            }
        }

        if (fineRecords.size() == 0) {
            System.out.println(">> No fines! You always return books on time.");
            return;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("| %-22s | %-12s | %-12s | %-8s |%n",
                "Book Title", "Due Date", "Returned", "Fine");
        System.out.println("------------------------------------------------------------------------");
        for (int i = 0; i < fineRecords.size(); i++) {
            History h = fineRecords.get(i);
            System.out.printf("| %-22s | %-12s | %-12s | Rs %-5.0f |%n",
                    h.bookTitle, h.dueDate, h.returnDate, h.fineAmount);
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("  Total Fine: Rs " + totalFine);
    }


    // ---- 5. View all members ----
    static void viewAllMembers() {
        System.out.println("\n--- ALL MEMBERS ---");

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-15s | %-18s | %-12s | %-20s | %-8s |%n",
                "ID", "Username", "Name", "Phone", "Email", "Status");
        System.out.println("-------------------------------------------------------------------------------------------");
        for (int i = 0; i < Data.members.size(); i++) {
            Member m = Data.members.get(i);
            String status = "Active";
            if (!m.isActive) {
                status = "Inactive";
            }
            System.out.printf("| %-3d | %-15s | %-18s | %-12s | %-20s | %-8s |%n",
                    m.id, m.username, m.name, m.phone, m.email, status);
        }
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("  Total Members: " + Data.members.size());
    }


    // ---- 6. Add a new member ----
    static void addNewMember() {
        System.out.println("\n--- ADD NEW MEMBER ---");

        System.out.print("Enter Full Name  : ");
        String name = Data.sc.nextLine();

        System.out.print("Enter Username   : ");
        String username = Data.sc.nextLine().trim();

        // Check if username already exists
        for (int i = 0; i < Data.members.size(); i++) {
            if (Data.members.get(i).username.equals(username)) {
                System.out.println(">> Username already exists! Try a different one.");
                return;
            }
        }

        System.out.print("Enter Password   : ");
        String password = Data.sc.nextLine();

        System.out.print("Enter Phone      : ");
        String phone = Data.sc.nextLine();

        System.out.print("Enter Email      : ");
        String email = Data.sc.nextLine();

        System.out.print("Enter Join Date (e.g. 14-04-2026): ");
        String joinDate = Data.sc.nextLine();

        // Create new member and add to list
        Member newMember = new Member(Data.nextMemberId, name, username, password, phone, email, joinDate);
        Data.nextMemberId = Data.nextMemberId + 1;
        Data.members.add(newMember);

        System.out.println("\n>> Member added successfully!");
        System.out.println("   Member ID : " + newMember.id);
        System.out.println("   Username  : " + newMember.username);
        System.out.println("   Password  : " + newMember.password);
        System.out.println("   (Use these credentials to login)");
    }


    // ---- 7. Edit member details ----
    static void editMember() {
        System.out.println("\n--- EDIT MEMBER ---");

        System.out.print("Enter Member Username to edit: ");
        String username = Data.sc.nextLine().trim();

        // Find member (including inactive)
        Member m = null;
        for (int i = 0; i < Data.members.size(); i++) {
            if (Data.members.get(i).username.equals(username)) {
                m = Data.members.get(i);
                break;
            }
        }

        if (m == null) {
            System.out.println(">> Member not found: " + username);
            return;
        }

        // Show current details
        System.out.println("  Current Name  : " + m.name);
        System.out.println("  Current Phone : " + m.phone);
        System.out.println("  Current Email : " + m.email);
        System.out.println("\n(Press Enter to keep current value)");

        System.out.print("New Name  : ");
        String name = Data.sc.nextLine();
        if (name.length() > 0) {
            m.name = name;
        }

        System.out.print("New Phone : ");
        String phone = Data.sc.nextLine();
        if (phone.length() > 0) {
            m.phone = phone;
        }

        System.out.print("New Email : ");
        String email = Data.sc.nextLine();
        if (email.length() > 0) {
            m.email = email;
        }

        System.out.print("New Password (Enter to skip): ");
        String pass = Data.sc.nextLine();
        if (pass.length() > 0) {
            m.password = pass;
        }

        System.out.println(">> Member updated successfully!");
    }


    // ---- 8. Delete (deactivate) a member ----
    static void deleteMember() {
        System.out.println("\n--- DELETE MEMBER ---");

        System.out.print("Enter Member Username to delete: ");
        String username = Data.sc.nextLine().trim();

        // Cannot delete yourself
        if (username.equals(Data.loggedInUser)) {
            System.out.println(">> You cannot delete your own account!");
            return;
        }

        // Find member
        Member m = null;
        for (int i = 0; i < Data.members.size(); i++) {
            if (Data.members.get(i).username.equals(username)) {
                m = Data.members.get(i);
                break;
            }
        }

        if (m == null) {
            System.out.println(">> Member not found: " + username);
            return;
        }

        // Check if member has books issued
        int issuedCount = Data.countIssuedBooks(username);
        if (issuedCount > 0) {
            System.out.println(">> Cannot delete! Member has " + issuedCount + " book(s) currently issued.");
            return;
        }

        // Confirm
        System.out.println("  Member: " + m.name + " (" + m.username + ")");
        System.out.print("  Are you sure? (yes/no): ");
        String confirm = Data.sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            m.isActive = false;
            System.out.println(">> Member deactivated successfully!");
        } else {
            System.out.println(">> Delete cancelled.");
        }
    }


    // ---- 9. View any member's book history ----
    static void viewMemberHistory() {
        System.out.println("\n--- VIEW MEMBER'S BOOK HISTORY ---");

        System.out.print("Enter Member Username: ");
        String username = Data.sc.nextLine().trim();

        // Find member (including inactive)
        Member m = null;
        for (int i = 0; i < Data.members.size(); i++) {
            if (Data.members.get(i).username.equals(username)) {
                m = Data.members.get(i);
                break;
            }
        }

        if (m == null) {
            System.out.println(">> Member not found: " + username);
            return;
        }

        System.out.println("History for: " + m.name + " (" + m.username + ")");
        System.out.println("Books currently issued: " + Data.countIssuedBooks(username));
        System.out.println("Total fines: Rs " + Data.getTotalFine(username));
        printHistoryForMember(username);
    }


    // ---- Print history table for a specific member ----
    static void printHistoryForMember(String username) {

        // Collect all history for this member
        ArrayList<History> memberHistory = new ArrayList<>();
        for (int i = 0; i < Data.history.size(); i++) {
            if (Data.history.get(i).memberUsername.equals(username)) {
                memberHistory.add(Data.history.get(i));
            }
        }

        if (memberHistory.size() == 0) {
            System.out.println(">> No book history found.");
            return;
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-22s | %-12s | %-12s | %-12s | %-10s | %-6s |%n",
                "BkID", "Book Title", "Issue Date", "Due Date", "Return Date", "Status", "Fine");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < memberHistory.size(); i++) {
            History h = memberHistory.get(i);
            String ret = "---";
            if (h.returnDate.length() > 0) {
                ret = h.returnDate;
            }
            String fine = "---";
            if (h.fineAmount > 0) {
                fine = "Rs " + h.fineAmount;
            }
            System.out.printf("| %-4d | %-22s | %-12s | %-12s | %-12s | %-10s | %-6s |%n",
                    h.bookId, h.bookTitle, h.issueDate, h.dueDate, ret, h.status, fine);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}
