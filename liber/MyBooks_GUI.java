package liber;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MyBooks_GUI extends JFrame {

    private JButton home_btn, mybooks_btn, logout_btn, addbook_btn, request_box;
    private JLabel mybooks_title, requests_label;

    private JPanel myCollectionPanel, loanedPanel, borrowedPanel;
    private User currentUser;

    public MyBooks_GUI(User currentUser) {
        this.currentUser = currentUser;

        setTitle("My Books");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        topBar.setBackground(Color.LIGHT_GRAY);

        home_btn = new JButton("Home");
        mybooks_btn = new JButton("My Books");
        logout_btn = new JButton("Logout");
        topBar.add(home_btn);
        topBar.add(mybooks_btn);
        topBar.add(logout_btn);
        add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        mybooks_title = new JLabel("My Books", SwingConstants.CENTER);
        mybooks_title.setFont(new Font("Verdana", Font.BOLD, 26));
        centerPanel.add(mybooks_title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 30, 0));

        loanedPanel = new JPanel();
        loanedPanel.setLayout(new BoxLayout(loanedPanel, BoxLayout.Y_AXIS));

        borrowedPanel = new JPanel();
        borrowedPanel.setLayout(new BoxLayout(borrowedPanel, BoxLayout.Y_AXIS));

        JPanel loanedSection = createSectionPanel("Loaned", loanedPanel, null);
        JPanel borrowedSection = createSectionPanel("Borrowed", borrowedPanel, null);

        JPanel leftColumn = new JPanel(new GridLayout(2, 1, 0, 20));
        leftColumn.add(loanedSection);
        leftColumn.add(borrowedSection);

        addbook_btn = new JButton("+");
        addbook_btn.setMargin(new Insets(2, 8, 2, 8));
        myCollectionPanel = new JPanel();
        myCollectionPanel.setLayout(new BoxLayout(myCollectionPanel, BoxLayout.Y_AXIS));
        JPanel myCollectionSection = createSectionPanel("My Collection", myCollectionPanel, addbook_btn);

        contentPanel.add(leftColumn);
        contentPanel.add(myCollectionSection);
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.GRAY));
        bottomPanel.setPreferredSize(new Dimension(0, 60));

        requests_label = new JLabel("Requests:");
        request_box = new JButton("View");
        JPanel requestGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        requestGroup.setOpaque(false);
        requestGroup.add(requests_label);
        requestGroup.add(request_box);
        bottomPanel.add(requestGroup, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        home_btn.addActionListener(e -> go_to_home());
        addbook_btn.addActionListener(e -> add_new_book());
        request_box.addActionListener(e -> go_to_requests());
        logout_btn.addActionListener(e -> {
            new Login_GUI();
            dispose();
        });

        refreshCollectionUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSectionPanel(String title, JPanel contentPanel, JButton headerButton) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new LineBorder(Color.GRAY));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(220, 220, 220));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JLabel header = new JLabel(title, SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(header, BorderLayout.CENTER);

        if (headerButton != null) headerPanel.add(headerButton, BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(scroll, BorderLayout.CENTER);
        return container;
    }

    public void go_to_requests() {
        new Requests_GUI(currentUser, this);
        setVisible(false);
    }

    public void add_new_book() {
        new NewBook_GUI(this, currentUser);
    }

    public void go_to_home() {
        new Home_GUI(currentUser);
        dispose();
    }

    public void refreshCollectionUI() {
        myCollectionPanel.removeAll();
        loanedPanel.removeAll();
        borrowedPanel.removeAll();

        for (Book book : currentUser.getCollection()) {
            myCollectionPanel.add(createRow(book.getTitle() + " by " +
                    (book.getAuthor() == null || book.getAuthor().isBlank() ? "Unknown author" : book.getAuthor()),
                    book.getRowColor()));
        }

        for (Book book : currentUser.getCollection()) {
            if (book.isLoaned() || book.isAwaitingRating()) {
                loanedPanel.add(createLoanedRow(book));
            }
        }

        for (Book book : BookStore.getAllBooks()) {
            if (book.getBorrower() == currentUser) {
                borrowedPanel.add(createBorrowedRow(book));
            }
        }

        revalidate();
        repaint();
    }

    private JPanel createRow(String text, Color bg) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        row.setBackground(bg != null ? bg : Color.LIGHT_GRAY);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        row.add(label, BorderLayout.CENTER);
        return row;
    }

    private JPanel createBorrowedRow(Book book) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        row.setBackground(book.getRowColor() != null ? book.getRowColor() : Color.LIGHT_GRAY);

        JLabel label = new JLabel(book.getTitle() + " (from " + book.getOwner().getName() + ")");
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton returnBtn = new JButton("Return");
        returnBtn.addActionListener(e -> returnBook(book));

        row.add(label, BorderLayout.CENTER);
        row.add(returnBtn, BorderLayout.EAST);
        return row;
    }

    private JPanel createLoanedRow(Book book) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        row.setBackground(book.getRowColor() != null ? book.getRowColor() : Color.LIGHT_GRAY);

        String labelText = book.getBorrower() != null
                ? book.getTitle() + " → " + book.getBorrower().getName()
                : book.getTitle() + " → Returned";

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        row.add(label, BorderLayout.CENTER);

        if (book.isAwaitingRating()) {
            JButton rateBtn = new JButton("Rate Borrower");
            rateBtn.addActionListener(e -> rateBorrower(book));
            row.add(rateBtn, BorderLayout.EAST);
        }

        return row;
    }

    private void returnBook(Book book) {
        book.setBorrower(null);          
        book.setAwaitingRating(true);    

        JOptionPane.showMessageDialog(this, "Book returned. You can rate the borrower now.");
        refreshCollectionUI();
    }

    private void rateBorrower(Book book) {
        String ratingStr = JOptionPane.showInputDialog(
                this,
                "Rate the borrower (1-5):",
                "Rate Borrower",
                JOptionPane.PLAIN_MESSAGE
        );

        int rating = 0;
        try {
            rating = Integer.parseInt(ratingStr);
            if (rating < 1 || rating > 5) rating = 0;
        } catch (NumberFormatException ignored) {}

        if (rating > 0) {
            System.out.println("Borrower rated: " + rating);
        }

        book.setLoaned(false);
        book.setAwaitingRating(false);
        book.setBorrower(null);

        JOptionPane.showMessageDialog(this, "Book returned to your collection!");
        go_to_home();
    }
}
