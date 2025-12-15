package liber;

import javax.swing.*;
import java.awt.*;

public class BookDetailsDialog extends JDialog {

    public BookDetailsDialog(JFrame parent, User currentUser, Book book) {
        super(parent, "Book Details", true);
        setSize(500, 500);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Book Details");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 15));

        formPanel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField(book.getTitle());
        titleField.setEditable(false);
        formPanel.add(titleField);

        formPanel.add(new JLabel("Author:"));
        JTextField authorField = new JTextField(book.getAuthor());
        authorField.setEditable(false);
        formPanel.add(authorField);

        formPanel.add(new JLabel("Genre:"));
        JTextField genreField = new JTextField(book.getGenre());
        genreField.setEditable(false);
        formPanel.add(genreField);

        formPanel.add(new JLabel("Condition:"));
        JTextField conditionField = new JTextField(book.getCondition());
        conditionField.setEditable(false);
        formPanel.add(conditionField);

        formPanel.add(new JLabel("Location:"));
        JTextField locationField = new JTextField(book.getLocation());
        locationField.setEditable(false);
        formPanel.add(locationField);

        formPanel.add(new JLabel("Owner:"));
        JTextField ownerField = new JTextField(book.getOwner().getName());
        ownerField.setEditable(false);
        formPanel.add(ownerField);

        formPanel.add(new JLabel("Description:"));
        JTextArea descArea = new JTextArea(book.getDescription());
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setBackground(new Color(245, 245, 245));
        JScrollPane descScroll = new JScrollPane(descArea);
        formPanel.add(descScroll);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        JButton requestBtn = new JButton("Request Book");

        if (book.getOwner().equals(currentUser)) {
            requestBtn.setEnabled(false);
        }

        requestBtn.addActionListener(e -> {
            Request req = new Request(currentUser, book.getOwner(), book);
            RequestStore.addRequest(req);
            JOptionPane.showMessageDialog(this, "Request sent!");

            new Requests_GUI(currentUser, null);
            dispose();
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(requestBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
