package liber;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NewBook_GUI extends JFrame {

    private JTextField nameField;
    private JTextField authorField;
    private JTextField descField;
    private JComboBox<String> genreBox;
    private JComboBox<String> conditionBox;
    private JComboBox<String> locationBox;
    private MyBooks_GUI parent;

    public NewBook_GUI(MyBooks_GUI parent) {
        this.parent = parent;
        setTitle("New Book");
        setSize(600, 420);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("New Book:");
        title.setFont(new Font("Verdana", Font.BOLD, 26));
        mainPanel.add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        nameField = new JTextField();
        authorField = new JTextField();
        descField = new JTextField();
        genreBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Fantasy", "Sci-Fi", "Romance"});
        conditionBox = new JComboBox<>(new String[]{"Fair", "Good", "Great"});
        locationBox = new JComboBox<>(new String[]{"Shelf", "Living Room", "Bedroom"});

        formPanel.add(new JLabel("Title:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Author:")); formPanel.add(authorField);
        formPanel.add(new JLabel("Genre:")); formPanel.add(genreBox);
        formPanel.add(new JLabel("Description:")); formPanel.add(descField);
        formPanel.add(new JLabel("Condition:")); formPanel.add(conditionBox);
        formPanel.add(new JLabel("Location:")); formPanel.add(locationBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton cancelBtn = new JButton("Cancel");
        JButton addBtn = new JButton("ADD BOOK");
        cancelBtn.addActionListener(e -> dispose());
        addBtn.addActionListener(e -> addBook());
        buttonPanel.add(cancelBtn);
        buttonPanel.add(addBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBook() {
        Book book = new Book(
            nameField.getText(),
            authorField.getText(),
            true,
            genreBox.getSelectedItem().toString(),
            descField.getText(),
            conditionBox.getSelectedItem().toString(),
            locationBox.getSelectedItem().toString()
        );

        // Random pastel color
        Random rand = new Random();
        float r = 0.5f + rand.nextFloat() * 0.5f;
        float g = 0.5f + rand.nextFloat() * 0.5f;
        float b = 0.5f + rand.nextFloat() * 0.5f;
        book.setRowColor(new Color(r, g, b));

        BookStore.addBook(book);
        parent.refreshCollectionUI();
        dispose();
    }
}
