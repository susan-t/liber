package liber;

import javax.swing.*;
import java.awt.*;

public class Login_GUI extends JFrame {

    private JTextField usernameField;

    public Login_GUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> login());

        add(panel, BorderLayout.CENTER);
        add(loginBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String name = usernameField.getText().trim();
        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Enter a username");
            return;
        }

        User user = UserStore.getOrCreateUser(name);
        new Home_GUI(user);
        dispose();
    }
}
