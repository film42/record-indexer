package client.components.downloadModal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 10:20 PM
 */
public class SampleImage extends JPanel {

    String path;
    BufferedImage image;

    public SampleImage(String path) {
        this.path = path;
        setupView();
    }

    private void setupView() {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.scale(0.7, 0.7);
        g2.drawImage(image, 0, 0, null);
    }

}
