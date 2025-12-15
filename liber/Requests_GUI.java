package liber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Requests_GUI extends JFrame {

    private JButton home_btn;
    private JButton my_books_btn;
    private User currentUser;
    private MyBooks_GUI myBooksGUI;

    public Requests_GUI(User currentUser, MyBooks_GUI myBooksGUI) {
        this.currentUser = currentUser;
        this.myBooksGUI = myBooksGUI;

        setTitle("Requests");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        topBar.setBackground(Color.LIGHT_GRAY);

        home_btn = new JButton("Home");
        my_books_btn = new JButton("My Books");

        topBar.add(home_btn);
        topBar.add(my_books_btn);
        add(topBar, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        JPanel lenderPanel = createLenderPanel();
        JPanel borrowerPanel = createBorrowerPanel();

        tabs.add("Received Requests", lenderPanel);
        tabs.add("Sent Requests", borrowerPanel);

        add(tabs, BorderLayout.CENTER);

        home_btn.addActionListener(e -> {
            new Home_GUI(currentUser);
            dispose();
        });

        my_books_btn.addActionListener(e -> {
            if (myBooksGUI != null) {
                myBooksGUI.setVisible(true);
                myBooksGUI.refreshCollectionUI();
            }
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLenderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<Request> requests = RequestStore.getRequestsFor(currentUser);
        for (Request req : requests) {
            if (req != null && req.getRequester() != null) {
                listPanel.add(createRequestRow(req));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBorrowerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<Request> requests = RequestStore.getRequestsByBorrower(currentUser); 
        for (Request req : requests) {
            if (req != null && req.getLender() != null && req.getBook() != null) {
                listPanel.add(createBorrowerRow(req));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRequestRow(Request req) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setPreferredSize(new Dimension(0, 55));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        row.setBackground(new Color(230, 230, 230));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        User requester = req.getRequester();
        String borrowerName = (requester != null) ? requester.getName() : "Unknown";

        JLabel borrowerLabel = new JLabel("Borrower: " + borrowerName);
        JLabel bookLabel = new JLabel(req.getBook() != null ? req.getBook().getTitle() : "Unknown Book");
        JLabel statusLabel = new JLabel(req.getStatus() != null ? req.getStatus() : "Pending");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        if (req.getApproval() != null) {
            statusLabel.setForeground(req.getApproval() ? new Color(0, 130, 0) : Color.RED);
        }

        row.add(borrowerLabel, BorderLayout.WEST);
        row.add(bookLabel, BorderLayout.CENTER);
        row.add(statusLabel, BorderLayout.EAST);

        row.addMouseListener(new MouseAdapter() {
            Color base = row.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) { row.setBackground(new Color(210, 210, 210)); }
            @Override
            public void mouseExited(MouseEvent e) { row.setBackground(base); }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (req.getLender().equals(currentUser) && req.getApproval() == null) {
                    int choice = JOptionPane.showConfirmDialog(
                            Requests_GUI.this,
                            "Approve request from " + borrowerName + "?",
                            "Approve/Decline",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        req.setApproval(true);
                        req.setStatus("Approved");
                        approveRequest(req);
                    } else {
                        req.setApproval(false);
                        req.setStatus("Declined");
                        refresh();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            Requests_GUI.this,
                            "Request status: " + req.getStatus(),
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        return row;
    }

    private JPanel createBorrowerRow(Request req) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setPreferredSize(new Dimension(0, 50));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        row.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        row.setBackground(new Color(245, 245, 245));

        String bookTitle = req.getBook() != null ? req.getBook().getTitle() : "Unknown Book";
        String lenderName = (req.getLender() != null) ? req.getLender().getName() : "Unknown";
        String status = req.getStatus() != null ? req.getStatus() : "Pending";

        JLabel infoLabel = new JLabel(bookTitle + " → " + lenderName + " [" + status + "]");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        row.add(infoLabel, BorderLayout.CENTER);

        return row;
    }

    private void approveRequest(Request request) {
        Book book = request.getBook();
        User borrower = request.getRequester();

        if (book != null && borrower != null) {
            book.loanTo(borrower);
            RequestStore.removeRequest(request);

            if (myBooksGUI != null) {
                myBooksGUI.refreshCollectionUI();
            }
        }
        refresh();
    }

    private void refresh() {
        dispose();
        new Requests_GUI(currentUser, myBooksGUI);
    }
}
