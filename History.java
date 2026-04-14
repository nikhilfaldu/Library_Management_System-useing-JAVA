// History.java - This class stores record of book issue and return

public class History {

    // Variables to store history details
    int recordId;
    String memberUsername;
    String memberName;
    String bookTitle;
    int bookId;
    String issueDate;
    String dueDate;
    String returnDate;
    String status;      // "ISSUED" or "RETURNED"
    double fineAmount;

    // Constructor - used to create a new history record
    History(int recordId, String memberUsername, String memberName, String bookTitle,
            int bookId, String issueDate, String dueDate) {
        this.recordId = recordId;
        this.memberUsername = memberUsername;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = "";
        this.status = "ISSUED";
        this.fineAmount = 0;
    }
}
