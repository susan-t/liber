package liber;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Home_GUI extends JFrame {

    private JButton home_btn;
    private JButton my_books_btn;
    private JButton logout_btn;

    private JLabel liber_title;
    private JTextField search_bar;

    private JPanel display_books;
    private JLabel emptyLabel;

    private User currentUser;

    public Home_GUI(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Liber");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        topBar.setBackground(Color.LIGHT_GRAY);

        home_btn = new JButton("Home");
        my_books_btn = new JButton("My Books");
        logout_btn = new JButton("Logout");

        topBar.add(home_btn);
        topBar.add(my_books_btn);
        topBar.add(logout_btn);
        add(topBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BoxLayout(titleBar, BoxLayout.Y_AXIS));
        titleBar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        liber_title = new JLabel("Liber");
        liber_title.setFont(new Font("Verdana", Font.BOLD, 26));
        liber_title.setAlignmentX(Component.CENTER_ALIGNMENT);

        search_bar = new JTextField();
        search_bar.setMaximumSize(new Dimension(300, 30));

        JButton search_btn = new JButton("Search");
        search_btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleBar.add(liber_title);
        titleBar.add(Box.createRigidArea(new Dimension(0, 10)));
        titleBar.add(search_bar);
        titleBar.add(Box.createRigidArea(new Dimension(0, 8)));
        titleBar.add(search_btn);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        JPanel booksContainer = new JPanel(new BorderLayout());
        booksContainer.setBackground(Color.WHITE);

        booksContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(Color.GRAY),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15) 
                )
        );

        JLabel booksHeader = new JLabel("Available Books", SwingConstants.CENTER);
        booksHeader.setFont(new Font("Arial", Font.BOLD, 14));
        booksHeader.setOpaque(true);
        booksHeader.setBackground(new Color(220, 220, 220));
        booksHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        display_books = new JPanel();
        display_books.setLayout(new BoxLayout(display_books, BoxLayout.Y_AXIS));
        display_books.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        emptyLabel = new JLabel("No books in the collection", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        emptyLabel.setForeground(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(display_books);
        scrollPane.setBorder(null);

        booksContainer.add(booksHeader, BorderLayout.NORTH);
        booksContainer.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(booksContainer, BorderLayout.CENTER);

        loadBooks();

        home_btn.addActionListener(e -> refresh());
        my_books_btn.addActionListener(e -> go_to_myBook());
        search_btn.addActionListener(e -> search(search_bar.getText()));
        logout_btn.addActionListener(e -> logout());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBookRow(Book book) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (book.getRowColor() == null) {
            Random rand = new Random();
            float r = 0.5f + rand.nextFloat() * 0.5f;
            float g = 0.5f + rand.nextFloat() * 0.5f;
            float b = 0.5f + rand.nextFloat() * 0.5f;
            book.setRowColor(new Color(r, g, b));
        }
        row.setBackground(book.getRowColor());

        String author = book.getAuthor();
        if (author == null || author.isBlank()) {
            author = "Unknown author";
        }

        JLabel titleLabel =
                new JLabel(book.getTitle() + " by " + author);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        row.add(titleLabel, BorderLayout.CENTER);

        row.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_book(book);
            }
        });

        return row;
    }

    private void loadBooks() {
        display_books.removeAll();

        List<Book> books = BookStore.getAvailableBooks(currentUser);

        if (books.isEmpty()) {
            display_books.setLayout(new BorderLayout());
            display_books.add(emptyLabel, BorderLayout.CENTER);
        } else {
            display_books.setLayout(new BoxLayout(display_books, BoxLayout.Y_AXIS));
            for (Book book : books) {
                display_books.add(createBookRow(book));
                display_books.add(Box.createRigidArea(new Dimension(0, 5))); // spacing between rows
            }
        }

        display_books.revalidate();
        display_books.repaint();
    }

    private void logout() {
        new Login_GUI();
        dispose();
    }

    public void go_to_myBook() {
        new MyBooks_GUI(currentUser);
        dispose();
    }

    public void view_book(Book book) {
        new BookDetailsDialog(this, currentUser, book);
    }

    public void search(String query) {
        if (query == null || query.isBlank()) {
            loadBooks();
            return;
        }

        display_books.removeAll();
        display_books.setLayout(new BoxLayout(display_books, BoxLayout.Y_AXIS));

        String searchLower = query.toLowerCase();
        for (Book book : BookStore.getAvailableBooks(currentUser)) {
            if (book.getTitle().toLowerCase().contains(searchLower)) {
                display_books.add(createBookRow(book));
                display_books.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        if (display_books.getComponentCount() == 0) {
            display_books.setLayout(new BorderLayout());
            display_books.add(emptyLabel, BorderLayout.CENTER);
        }

        display_books.revalidate();
        display_books.repaint();
    }

    public void refresh() {
        new Home_GUI(currentUser);
        dispose();
    }
}
