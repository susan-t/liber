package liber;
import java.util.List;

public class User {
    
    private String name;
    private String rating; 
    private List<Book> user_collection;
    private List<Book> books_borrowed;
    private List<Book> books_loaned;
    private boolean loan_max;
    private int user_id;

    public User(String name) {
        this.name = name;
        this.rating = "3.0";
        this.loan_max = false;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating(){
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getUserId(){
        return user_id;
    }

    // public void loanMax() {
    //     if(books_borrowed.size() < 3 ){
    //         loan_max++;
    //     }
    // }

    public boolean getLoanMax(){
        return loan_max;
    }

    public void add_book(String title, String author, boolean availability, String genre, String desc, String cond, String location){
        user_collection.add(new Book(title, author, availability, genre, desc, cond, location));
    }

    public void add_book_borrowed(String title, String author, boolean availability, String genre, String desc, String cond, String location){
        books_borrowed.add(new Book(title, author, availability, genre, desc, cond, location));
    }

    public void add_book_loaned(String title, String author, boolean availability, String genre, String desc, String cond, String location){
        books_loaned.add(new Book(title, author, availability, genre, desc, cond, location));
    }



}



