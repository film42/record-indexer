package client.components.loginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/11/13
 * Time: 1:19 AM
 */
public class LoginWindow extends JFrame {

    public LoginWindow() {
        setupView();
    }

    private void setupView() {
        this.setTitle("Login to Indexer");
        this.setSize(350, 130);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username: ");
        this.add(usernameLabel);
        JTextField userTextField = new JTextField();
        userTextField.setPreferredSize(new Dimension(250, 30));
        this.add(userTextField);

        JLabel passwordLabel = new JLabel("Password: ");
        this.add(passwordLabel);
        JTextField passwordTextField = new JTextField();
        passwordTextField.setPreferredSize(new Dimension(250, 30));
        this.add(passwordTextField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(loginListener);
        this.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitListener);
        this.add(exitButton);
    }

    private ActionListener loginListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private ActionListener exitListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
}
