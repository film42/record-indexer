package client.components.loginWindow;

import shared.communication.responses.ValidateUser_Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/11/13
 * Time: 2:41 PM
 */
public class SuccessLoginDialog extends JDialog {

    private ValidateUser_Res validateUserRes;

    public SuccessLoginDialog(ValidateUser_Res validateUserRes) {
        this.validateUserRes = validateUserRes;
        setupView();
    }

    private void setupView() {
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setTitle("Error!");
        this.setSize(230, 100);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        String welcome = "Welcome, " + validateUserRes.getFirstName() + " " +
                         validateUserRes.getLastName() + ".";

        String record = "You have inexed " + validateUserRes.getIndexedRecords() + " records.";


        JLabel label = new JLabel(welcome);
        this.add(label);

        JLabel label2 = new JLabel(record);
        this.add(label2);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(closeListener);
        this.add(closeButton);
    }


    private ActionListener closeListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(SuccessLoginDialog.this, WindowEvent.WINDOW_CLOSED));
        }
    };
}
