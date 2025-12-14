package liber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Requests_GUI extends JFrame {

    private JButton home_btn;
    private JButton my_books_btn;

    public Requests_GUI() {
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

        JPanel content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        header.setBackground(Color.WHITE);

        JButton backBtn = new JButton("←");
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel title = new JLabel("Request");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        content.add(header, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        listPanel.add(createRequestRow("#18A720", "Request to Borrow", ""));
        listPanel.add(createRequestRow("#ABCHSF", "Request for", "Approved"));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        content.add(scrollPane, BorderLayout.CENTER);

        backBtn.addActionListener(e -> dispose());

        home_btn.addActionListener(e -> {
            new Home_GUI();
            dispose();
        });

        my_books_btn.addActionListener(e -> {
            new MyBooks_GUI();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createRequestRow(String id, String text, String status) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setPreferredSize(new Dimension(0, 55));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        row.setBackground(new Color(230, 230, 230));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel idLabel = new JLabel(id);
        JLabel textLabel = new JLabel(text + " _______________________");
        JLabel statusLabel = new JLabel(status);

        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(new Color(0, 130, 0));

        row.add(idLabel, BorderLayout.WEST);
        row.add(textLabel, BorderLayout.CENTER);
        row.add(statusLabel, BorderLayout.EAST);

        row.addMouseListener(new MouseAdapter() {
            Color base = row.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                row.setBackground(new Color(210, 210, 210));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                row.setBackground(base);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        Requests_GUI.this,
                        "Clicked request " + id,
                        "Request Selected",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        return row;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Requests_GUI::new);
    }
}
