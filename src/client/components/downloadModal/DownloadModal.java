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
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setTitle("Download Image");
        this.setSize(300, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // TODO: Make this block the main window

        JButton jButton = new JButton("Close");
        jButton.addActionListener(closeListener);

        this.add(jButton, BorderLayout.SOUTH);

        String[] things = {"War 2012", "1890 Census"};
        JComboBox batchSelect = new JComboBox(things);

        this.add(batchSelect, BorderLayout.NORTH);

        JButton sampleImageButton = new JButton("Sample Image?");
        sampleImageButton.addActionListener(getSampleImageListener);
        this.add(sampleImageButton);


    }

    private ActionListener getSampleImageListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SampleImageModal sampleImageModal = new SampleImageModal();
            sampleImageModal.setVisible(true);
        }
    };

    private ActionListener closeListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(DownloadModal.this, WindowEvent.WINDOW_CLOSING));
        }
    };

}
