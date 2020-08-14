import java.util.*;

public class Book {

//  Initializing class variables
    private final String title;
    private final String author;
    private final String publisher;
    private final int id;
    private int quantity;
    public static Map<String, Book> allBooks = new HashMap<>();

//  Class Constructor
    public Book(String title, String author, String publisher, int id, int quantity) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.id = id;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//  Adds book to the initialized book Map
    public static Book addBook(String title, Book book) {
        allBooks.put(title, book);
        return book;
    }
}
