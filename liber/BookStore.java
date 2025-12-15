package liber;

import java.util.ArrayList;
import java.util.List;

public class BookStore {

    private static final List<Book> books = new ArrayList<>();

    public static void addBook(Book book) { books.add(book); }

    public static List<Book> getBooks() { return books; }

    public static List<Book> getAvailableBooks(User currentUser) {
    return books.stream()
        .filter(b -> b.getBorrower() == null)
        .filter(b -> b.getOwner() != currentUser)
        .toList();
}

    public static List<Book> getAllBooks() {
        return books;
    }
}
