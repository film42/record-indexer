package client;

import client.communication.Communicator;
import client.components.MainWindow;
import client.components.loginWindow.ErrorLoginDialog;
import client.components.loginWindow.LoginWindow;
import client.components.loginWindow.SuccessLoginDialog;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.ValidateUser_Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/28/13
 * Time: 2:15 PM
 */
public class Client {

    private LoginWindow loginWindow;
    private Communicator communicator;

    public Client(Communicator communicator) {
        this.communicator = communicator;
    }

    public void run() {
        loginWindow = new LoginWindow(communicator);
        loginWindow.addLoginListener(loginListener);

        // Run
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginWindow.setVisible(true);

                //MainWindow frame = new MainWindow();
                //frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // Create Window
        String host = args[0];
        String port = args[1];
        String server = "http://"+host+":"+port+"/";
        Communicator communicator = new Communicator(server);

        Client client = new Client(communicator);
        client.run();
    }

    private ActionListener loginListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ValidateUser_Param param = new ValidateUser_Param();
            param.setUsername(loginWindow.getUsername());
            param.setPassword(loginWindow.getPassword());

            try {
                ValidateUser_Res validateUserRes;
                validateUserRes = communicator.validateUser(param);

                loginWindow.setVisible(false);

                SuccessLoginDialog successLoginDialog = new SuccessLoginDialog(validateUserRes);
                successLoginDialog.addWindowListener(openMainWindowListener);
                successLoginDialog.setVisible(true);


            } catch (Exception execption) {
                ErrorLoginDialog errorLoginDialog = new ErrorLoginDialog();
                errorLoginDialog.setVisible(true);
            }
        }
    };

    private WindowListener openMainWindowListener = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);

            // Run
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    MainWindow frame = new MainWindow(communicator, loginWindow.getUsername(),
                                                      loginWindow.getPassword());
                    frame.addWindowListener(logoutListener);
                    frame.setVisible(true);
                }
            });
        }
    };

    private WindowListener logoutListener = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);

            loginWindow = new LoginWindow(communicator);
            loginWindow.addLoginListener(loginListener);

            // Run
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    loginWindow.setVisible(true);
                }
            });
        }
    };



}
