package client.components.imagePanel;

import client.components.imagePanel.listeners.ImageControlsListener;
import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.ImageStateListener;
import client.persistence.SyncContext;

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

    private ImageState imageState;

    public ImagePanel(ImageState imageState) {
        this.imageState = imageState;

        setupView();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY);

        String path = "db/statics/images/1890_image0.png";
        scalableImage = new ScalableImage(imageState, path);
        ImageControlsListener imageControlsListener = scalableImage.getImageControlsListener();

        imageControl = new ImageControl();
        imageControl.addControlsListener(imageControlsListener);

        this.add(imageControl, Component.LEFT_ALIGNMENT);

        this.add(scalableImage, BorderLayout.CENTER);
    }

}
