package client.components.downloadModal;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 10:20 PM
 */
public class SampleImage extends JPanel {

    BufferedImage image;

    public SampleImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.scale(0.7, 0.7);
        g2.drawImage(image, 0, 0, null);
    }

}
