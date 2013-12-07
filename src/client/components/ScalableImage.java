package client.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:56 PM
 */
public class ScalableImage extends JPanel {

    private BufferedImage image;

    private String path;

    public ScalableImage(String path) {
        this.path = path;
        this.setBackground(Color.DARK_GRAY);

        setupView();

    }

    private void setupView() {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        invertImage(image);
        g.drawImage(image, 10, 10, 200, 100, null);
    }

    public void invertImage(BufferedImage b) {
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int rgba = b.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                b.setRGB(x, y, col.getRGB());
            }
        }
    }
}
