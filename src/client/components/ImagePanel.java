package client.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:01 AM
 */
public class ImagePanel extends JPanel {

    public ImagePanel() {
        setupView();
        setupImageControl();
        setupImagePanel();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY);
    }

    private void setupImageControl() {
        this.add(new ImageControl(), Component.LEFT_ALIGNMENT);

        //this.add(controls, BorderLayout.NORTH);

        //this.getContentPane().add(imageControl, c1);
        // /db/statics/images/1890_image0.png
    }

    private void setupImagePanel() {
        String path = "db/statics/images/1890_image0.png";
        this.add(new ScalableImage(path), BorderLayout.CENTER);
    }

}
