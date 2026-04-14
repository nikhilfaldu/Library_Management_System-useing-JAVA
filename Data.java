// Data.java - This file stores all the data (books, members, history)
//              and helper methods used by the whole project

import java.util.ArrayList;
import java.util.Scanner;

public class Data {

    // ArrayLists to store all data (like arrays but size can grow)
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();
    static ArrayList<History> history = new ArrayList<>();

    // Scanner to take input from user
    static Scanner sc = new Scanner(System.in);

    // Counters for generating unique IDs
    static int nextBookId = 11;
    static int nextMemberId = 6;
    static int nextRecordId = 8;

    // Store who is currently logged in
    static String loggedInUser = "";

    // Library rules
    static int LOAN_DAYS = 14;        // books can be kept for 14 days
    static double FINE_PER_DAY = 2.0; // Rs 2 fine per day if late


    // ============ Load Dummy Data ============
    // This method adds sample data so we can test the program
    static void loadDummyData() {

        // --- Adding 5 Members ---
        members.add(new Member(1, "Admin", "admin", "1234", "9999999999", "admin@library.com", "01-01-2025"));
        members.add(new Member(2, "Rahul Patel", "rahul", "pass1", "9876543210", "rahul@gmail.com", "15-01-2025"));
        members.add(new Member(3, "Priya Shah", "priya", "pass2", "9123456789", "priya@gmail.com", "20-02-2025"));
        members.add(new Member(4, "Amit Kumar", "amit", "pass3", "9988776655", "amit@gmail.com", "10-03-2025"));
        members.add(new Member(5, "Sneha Joshi", "sneha", "pass4", "9876501234", "sneha@gmail.com", "01-04-2025"));

        // --- Adding 10 Books (with number of copies) ---
        books.add(new Book(1, "Java Programming", "James Gosling", "Programming", 3));
        books.add(new Book(2, "Data Structures", "Narasimha Karumanchi", "Computer Science", 2));
        books.add(new Book(3, "The Alchemist", "Paulo Coelho", "Fiction", 4));
        books.add(new Book(4, "Wings of Fire", "APJ Abdul Kalam", "Biography", 2));
        books.add(new Book(5, "Let Us C", "Yashavant Kanetkar", "Programming", 3));
        books.add(new Book(6, "Python Crash Course", "Eric Matthes", "Programming", 2));
        books.add(new Book(7, "Clean Code", "Robert C. Martin", "Programming", 1));
        books.add(new Book(8, "Harry Potter", "J.K. Rowling", "Fiction", 5));
        books.add(new Book(9, "Atomic Habits", "James Clear", "Self Help", 3));
        books.add(new Book(10, "The White Tiger", "Aravind Adiga", "Fiction", 2));

        // --- Currently Issued Books ---

        // Rahul has "Java Programming"
        books.get(0).availableCopies = books.get(0).availableCopies - 1;
        history.add(new History(1, "rahul", "Rahul Patel", "Java Programming", 1, "01-04-2026", "15-04-2026"));

        // Amit has "Wings of Fire"
        books.get(3).availableCopies = books.get(3).availableCopies - 1;
        history.add(new History(2, "amit", "Amit Kumar", "Wings of Fire", 4, "10-04-2026", "24-04-2026"));

        // Sneha has "Harry Potter"
        books.get(7).availableCopies = books.get(7).availableCopies - 1;
        history.add(new History(3, "sneha", "Sneha Joshi", "Harry Potter", 8, "05-04-2026", "19-04-2026"));

        // Admin has "Clean Code"
        books.get(6).availableCopies = books.get(6).availableCopies - 1;
        history.add(new History(4, "admin", "Admin", "Clean Code", 7, "12-04-2026", "26-04-2026"));

        // --- Already Returned Books (for history) ---

        // Priya returned "The Alchemist" - 2 days late, so fine = Rs 4
        History h1 = new History(5, "priya", "Priya Shah", "The Alchemist", 3, "20-03-2026", "03-04-2026");
        h1.returnDate = "05-04-2026";
        h1.status = "RETURNED";
        h1.fineAmount = 4.0;
        history.add(h1);

        // Rahul returned "Let Us C" - on time, no fine
        History h2 = new History(6, "rahul", "Rahul Patel", "Let Us C", 5, "15-03-2026", "29-03-2026");
        h2.returnDate = "28-03-2026";
        h2.status = "RETURNED";
        h2.fineAmount = 0;
        history.add(h2);

        // Admin returned "Data Structures" - 5 days late, fine = Rs 10
        History h3 = new History(7, "admin", "Admin", "Data Structures", 2, "01-03-2026", "15-03-2026");
        h3.returnDate = "20-03-2026";
        h3.status = "RETURNED";
        h3.fineAmount = 10.0;
        history.add(h3);
    }


    // ============ Helper Methods ============

    // Find a book by its ID
    static Book findBookById(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).id == id) {
                return books.get(i);
            }
        }
        return null;  // not found
    }

    // Find an active member by username
    static Member findMember(String username) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).username.equals(username) && members.get(i).isActive) {
                return members.get(i);
            }
        }
        return null;  // not found
    }

    // Find a member by ID
    static Member findMemberById(int id) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).id == id) {
                return members.get(i);
            }
        }
        return null;  // not found
    }

    // Safely get a number from user (handles wrong input)
    static int getIntInput() {
        while (true) {
            try {
                int number = Integer.parseInt(sc.nextLine().trim());
                return number;
            } catch (NumberFormatException e) {
                System.out.print(">> Please enter a valid number: ");
            }
        }
    }

    // Count how many books a member currently has
    static int countIssuedBooks(String username) {
        int count = 0;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).memberUsername.equals(username) && history.get(i).status.equals("ISSUED")) {
                count = count + 1;
            }
        }
        return count;
    }

    // Get total fine amount for a member
    static double getTotalFine(String username) {
        double total = 0;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).memberUsername.equals(username)) {
                total = total + history.get(i).fineAmount;
            }
        }
        return total;
    }
}
