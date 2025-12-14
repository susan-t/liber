package liber;
import java.awt.Color;


public class Book {

    private String title;
    private String author;
    private Boolean availability; 
    private String genre;
    private String desc;
    private String cond;
    private String location;
    private int book_id;
    private Color rowColor;

    public Book(String title, String author, boolean availability, String genre, String desc, String cond, String location) {
        this.title = title;
        this.author = author;
        this.availability = availability; 
        this.genre = genre;
        this.desc = desc;
        this.cond = cond;
        this.location = location;
        //this.book_id = book_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getCondition() {
        return cond;
    }

    public void setCondition(String condition) {
        this.cond = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }
    public int getBookId(){
        return book_id;
    }

    public void setRowColor(Color color) {
        this.rowColor = color;
    }

    public Color getRowColor() {
        return this.rowColor;
    }
}
