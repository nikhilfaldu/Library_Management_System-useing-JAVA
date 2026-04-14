// LibraryManagementSystem.java - MAIN FILE (Run this file)
// This is the entry point of the program
// It handles Login and Main Menu

public class LibraryManagementSystem {

    // Main method - program starts here
    public static void main(String[] args) {

        // Load sample/dummy data
        Data.loadDummyData();

        // Login loop - allows re-login after logout
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("   WELCOME TO LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==========================================");

            // Try to login (3 attempts)
            boolean loginSuccess = login();

            if (!loginSuccess) {
                System.out.println("Too many failed attempts. Exiting...");
                return;  // exit program
            }

            // Show main menu (returns true if user wants to exit fully)
            boolean wantsToExit = mainMenu();

            if (wantsToExit) {
                System.out.println("\n>> Thank you for using Library System. Goodbye! <<");
                return;  // exit program
            }

            // If user chose logout, loop goes back to login screen
        }
    }


    // Login method - returns true if login successful, false if failed
    static boolean login() {
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("\nEnter Username: ");
            String user = Data.sc.nextLine().trim();

            System.out.print("Enter Password: ");
            String pass = Data.sc.nextLine().trim();

            // Find member with this username
            Member m = Data.findMember(user);

            // Check if member exists and password matches
            if (m != null && m.password.equals(pass)) {
                Data.loggedInUser = m.username;
                System.out.println("\n>> Login Successful! Welcome, " + m.name + " <<");
                return true;
            } else {
                attempts = attempts - 1;
                System.out.println(">> Invalid credentials! Attempts left: " + attempts);
            }
        }

        return false;  // all attempts used
    }


    // Main menu - returns true if user wants to exit, false if just logout
    static boolean mainMenu() {
        while (true) {
            // Get logged in member's name
            Member loggedIn = Data.findMember(Data.loggedInUser);
            String displayName = Data.loggedInUser;
            if (loggedIn != null) {
                displayName = loggedIn.name;
            }

            System.out.println("\n==========================================");
            System.out.println("            MAIN MENU");
            System.out.println("  Welcome: " + displayName);
            System.out.println("==========================================");
            System.out.println("  1. Library Section");
            System.out.println("  2. Account Section");
            System.out.println("  3. Reports Section");
            System.out.println("  4. Logout (Switch User)");
            System.out.println("  5. Exit");
            System.out.println("==========================================");
            System.out.print("  Enter your choice: ");

            int choice = Data.getIntInput();

            if (choice == 1) {
                LibrarySection.menu();     // go to library section
            } else if (choice == 2) {
                AccountSection.menu();     // go to account section
            } else if (choice == 3) {
                ReportSection.menu();      // go to reports section
            } else if (choice == 4) {
                // Logout - go back to login screen
                System.out.println("\n>> Logged out: " + displayName);
                Data.loggedInUser = "";
                return false;  // false = don't exit, just logout
            } else if (choice == 5) {
                return true;   // true = exit program
            } else {
                System.out.println(">> Invalid choice!");
            }
        }
    }
}
