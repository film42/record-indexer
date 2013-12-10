package client.components.downloadModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 2:50 AM
 */
public class DownloadModal extends JDialog {

    //private JDialog dialog;
    //private JFrame modalFrame;

    public DownloadModal() {

        setupView();
    }

    private void setupView() {

        JButton jButton = new JButton("Close");
        jButton.addActionListener(actionListener);

        this.add(jButton, BorderLayout.SOUTH);

        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);

        this.setTitle("Download Image");
        this.setSize(300, 300);
        this.setResizable(false);
    }

    private ActionListener actionListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(DownloadModal.this, WindowEvent.WINDOW_CLOSING));
        }
    };

}
