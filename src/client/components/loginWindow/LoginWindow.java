package client.components.loginWindow;

import client.communication.Communicator;
import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.ValidateUser_Res;

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

    private Communicator communicator;
    private JPasswordField passwordTextField;
    private JTextField userTextField;
    private JButton loginButton;

    public LoginWindow(Communicator communicator) {
        this.communicator = communicator;

        setupView();
    }

    private void setupView() {
        this.setTitle("Login to Indexer");
        this.setSize(350, 130);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username: ");
        this.add(usernameLabel);
        userTextField = new JTextField();
        userTextField.setPreferredSize(new Dimension(250, 30));
        this.add(userTextField);

        JLabel passwordLabel = new JLabel("Password: ");
        this.add(passwordLabel);
        passwordTextField = new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(250, 30));
        this.add(passwordTextField);

        loginButton = new JButton("Login");
        this.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(exitListener);
        this.add(exitButton);

        //TODO: Remove me (factory)
        this.userTextField.setText("sheila");
        this.passwordTextField.setText("parker");
    }

    public void addLoginListener(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    private ActionListener exitListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    };

    public String getUsername() {
        return this.userTextField.getText();
    }

    public String getPassword() {
        return this.passwordTextField.getText();
    }
}
