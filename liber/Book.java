package liber;

import java.awt.Color;

public class Book {

    private String title;
    private String author;
    private boolean availability;
    private String genre;
    private String desc;
    private String cond;
    private String location;
    private int book_id;
    private Color rowColor;
    private User owner;
    private User borrower;

    public Book(String title, String author, boolean availability, String genre, String desc, String cond, String location, User owner) {
        this.title = title;
        this.author = author;
        this.availability = availability;
        this.genre = genre;
        this.desc = desc;
        this.cond = cond;
        this.location = location;
        this.owner = owner;
        this.borrower = null;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return desc; }
    public void setDescription(String desc) { this.desc = desc; }

    public String getCondition() { return cond; }
    public void setCondition(String cond) { this.cond = cond; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean getAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    public User getOwner() { return owner; }

    public int getBookId() { return book_id; }
    public void setBookId(int id) { this.book_id = id; }

    public Color getRowColor() { return rowColor; }
    public void setRowColor(Color color) { this.rowColor = color; }

    public boolean isLoaned() {
        return borrower != null;
    }

    public User getBorrower() {
        return borrower;
    }

    public void loanTo(User user) {
        this.borrower = user;
    }

    public void returnBook() {
        this.borrower = null;
    }
}
