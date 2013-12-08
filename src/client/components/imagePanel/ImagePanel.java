package client.components.imagePanel;

import client.components.imagePanel.listeners.ImageControlsListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:01 AM
 */
public class ImagePanel extends JPanel {

    private ImageControl imageControl;
    private ScalableImage scalableImage;

    public ImagePanel() {
        setupView();
        setupImageControl();
        setupImagePanel();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY);

        String path = "db/statics/images/1890_image0.png";
        scalableImage = new ScalableImage(path);
        ImageControlsListener imageControlsListener = scalableImage.getImageControlsListener();

        imageControl = new ImageControl();
        imageControl.addControlsListener(imageControlsListener);
        this.add(imageControl, Component.LEFT_ALIGNMENT);

        this.add(scalableImage, BorderLayout.CENTER);
    }

    private void setupImageControl() {


        //this.add(controls, BorderLayout.NORTH);

        //this.getContentPane().add(imageControl, c1);
        // /db/statics/images/1890_image0.png
    }

    private void setupImagePanel() {

    }

}
