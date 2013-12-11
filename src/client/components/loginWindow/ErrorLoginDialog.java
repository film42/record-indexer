package client.components.loginWindow;

import client.components.downloadModal.DownloadModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/11/13
 * Time: 2:26 PM
 */
public class ErrorLoginDialog extends JDialog {

    public ErrorLoginDialog() {
        setupView();
    }

    private void setupView() {
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setTitle("Error!");
        this.setSize(270, 80);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        JLabel label = new JLabel("Error, incorrect Username or Password!");
        this.add(label);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(closeListener);
        this.add(closeButton);
    }


    private ActionListener closeListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(ErrorLoginDialog.this, WindowEvent.WINDOW_CLOSING));
        }
    };

}
