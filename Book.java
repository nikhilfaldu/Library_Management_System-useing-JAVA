// Book.java - This class stores information about a book

public class Book {

    // Variables to store book details
    int id;
    String title;
    String author;
    String category;
    int totalCopies;
    int availableCopies;

    // Constructor - used to create a new book
    Book(int id, String title, String author, String category, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
}
