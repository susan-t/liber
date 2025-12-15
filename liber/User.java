package liber;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class User {

    private String name;
    private String rating;
    private int totalRating;
    private List<Book> user_collection;
    private List<Book> books_borrowed;
    private List<Book> books_loaned;
    private boolean loan_max;
    private int user_id;

    public User(String name) {
        this.name = name;
        this.rating = "3.0";
        this.loan_max = false;
        this.totalRating = 0;
        this.user_collection = new ArrayList<>();
        this.books_borrowed = new ArrayList<>();
        this.books_loaned = new ArrayList<>();
    }

    public void setUserId(int user_id) { this.user_id = user_id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public int getUserId() { return user_id; }
    public boolean getLoanMax() { return loan_max; }

    public void add_book(String title, String author, boolean availability, String genre, String desc, String cond, String location, Color rowColor) {
        Book book = new Book(title, author, availability, genre, desc, cond, location, this);
        book.setRowColor(rowColor);
        user_collection.add(book);
        BookStore.addBook(book);
    }

    public void add_book_borrowed(String title, String author, boolean availability, String genre, String desc, String cond, String location, Color rowColor) {
        Book book = new Book(title, author, availability, genre, desc, cond, location, null);
        book.setRowColor(rowColor);
        books_borrowed.add(book);
    }

    public void add_book_loaned(String title, String author, boolean availability, String genre, String desc, String cond, String location, Color rowColor) {
        Book book = new Book(title, author, availability, genre, desc, cond, location, null);
        book.setRowColor(rowColor);
        books_loaned.add(book);
    }

    public List<Book> getCollection() { return user_collection; }

    public void addRating(int rating2) {
        totalRating += rating2;
        int numberOfRatings = Math.max(1, totalRating / 5);
        double average = (double) totalRating / numberOfRatings;
        this.rating = String.format("%.1f", average);
    }
}

