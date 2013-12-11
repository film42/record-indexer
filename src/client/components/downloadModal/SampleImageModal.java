package client.components.downloadModal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 10:16 PM
 */
public class SampleImageModal extends JDialog {

    public SampleImageModal() {
        setupView();
    }

    private void setupView() {
        //this.setLayout(new GridLayout(2,1));

        this.setTitle("Sample Image from XXXXXXX");
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setSize(500, 410);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        String path = "db/statics/images/1890_image0.png";
        SampleImage sampleImage = new SampleImage(path);

        this.add(sampleImage, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(closeListener);
        this.add(closeButton, BorderLayout.SOUTH);

    }

    private ActionListener closeListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispatchEvent(new WindowEvent(SampleImageModal.this, WindowEvent.WINDOW_CLOSING));
        }
    };
}
