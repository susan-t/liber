package liber;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MyBooks_GUI extends JFrame {

    private JButton home_btn;
    private JButton mybooks_btn;
    private JButton logout_btn;
    private JButton addbook_btn;
    private JButton request_box;

    private JLabel mybooks_title;
    private JLabel loaned_label;
    private JLabel borrowed_label;
    private JLabel mycollection_label;
    private JLabel requests_label;

    private JPanel myCollectionPanel;
    private JPanel loanedPanel;
    private JPanel borrowedPanel;

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

        JPanel leftColumn = new JPanel(new GridLayout(2, 1, 0, 20));

        loanedPanel = new JPanel();
        loanedPanel.setLayout(new BoxLayout(loanedPanel, BoxLayout.Y_AXIS));
        loanedPanel.setBorder(new LineBorder(Color.GRAY));
        loaned_label = new JLabel("Loaned");
        loanedPanel.add(loaned_label, BorderLayout.NORTH);

        borrowedPanel = new JPanel();
        borrowedPanel.setLayout(new BoxLayout(borrowedPanel, BoxLayout.Y_AXIS));
        borrowedPanel.setBorder(new LineBorder(Color.GRAY));
        borrowed_label = new JLabel("Borrowed");
        borrowedPanel.add(borrowed_label, BorderLayout.NORTH);

        leftColumn.add(loanedPanel);
        leftColumn.add(borrowedPanel);

        JPanel rightColumn = new JPanel(new BorderLayout());
        rightColumn.setBorder(new LineBorder(Color.GRAY));

        JPanel collectionHeader = new JPanel(new BorderLayout());
        mycollection_label = new JLabel("My Collection");
        addbook_btn = new JButton("+");
        collectionHeader.add(mycollection_label, BorderLayout.WEST);
        collectionHeader.add(addbook_btn, BorderLayout.EAST);

        myCollectionPanel = new JPanel();
        myCollectionPanel.setLayout(new BoxLayout(myCollectionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(myCollectionPanel);
        rightColumn.add(collectionHeader, BorderLayout.NORTH);
        rightColumn.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(leftColumn);
        contentPanel.add(rightColumn);

        centerPanel.add(contentPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.GRAY));
        bottomPanel.setPreferredSize(new Dimension(0, 60));

        requests_label = new JLabel("Requests:");
        request_box = new JButton("View");
        bottomPanel.add(requests_label, BorderLayout.WEST);
        bottomPanel.add(request_box, BorderLayout.EAST);

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

    public void go_to_requests() {
        new Requests_GUI(currentUser, this); 
        this.setVisible(false);
    }

    public void add_new_book() {
        new NewBook_GUI(this, currentUser);
    }

    public void go_to_home() {
        new Home_GUI(currentUser);
        dispose();
    }

    private JPanel createRow(String text) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        row.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        row.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        row.add(label, BorderLayout.CENTER);
        return row;
    }

    public void refreshCollectionUI() {
        myCollectionPanel.removeAll();
        loanedPanel.removeAll();
        borrowedPanel.removeAll();

        for (Book book : currentUser.getCollection()) {
            JPanel row = new JPanel(new BorderLayout());
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            row.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            Color rowColor = book.getRowColor() != null ? book.getRowColor() : Color.LIGHT_GRAY;
            row.setBackground(rowColor);

            String author = book.getAuthor();
            if (author == null || author.isBlank()) author = "Unknown author";

            JLabel label = new JLabel(book.getTitle() + " by " + author, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            row.add(label, BorderLayout.CENTER);

            myCollectionPanel.add(row);
        }

        for (Book book : currentUser.getCollection()) {
            if (book.isLoaned() && book.getBorrower() != null) {
                loanedPanel.add(createRow(
                        book.getTitle() + " → " + book.getBorrower().getName()
                ));
            }
        }

        for (Book book : BookStore.getAllBooks()) {
            if (book.getBorrower() == currentUser) {
                borrowedPanel.add(createRow(
                        book.getTitle() + " (from " + book.getOwner().getName() + ")"
                ));
            }
        }
        myCollectionPanel.revalidate();
        myCollectionPanel.repaint();
        loanedPanel.revalidate();
        loanedPanel.repaint();
        borrowedPanel.revalidate();
        borrowedPanel.repaint();
    }
}
