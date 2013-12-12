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
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 10:16 PM
 */
public class SampleImageModal extends JDialog {

    BufferedImage image;

    public SampleImageModal(String path) {
        try {
            image = ImageIO.read(new URL(path));
        } catch (Exception e1) {
            return;
        }

        setupView();
    }

    private void setupView() {
        //this.setLayout(new GridLayout(2,1));

        this.setTitle("Sample Image from XXXXXXX");
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setSize(500, 410);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        SampleImage sampleImage = new SampleImage(image);

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
