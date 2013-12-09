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
        scalableImage = new ScalableImage(syncContext, path);
        ImageControlsListener imageControlsListener = scalableImage.getImageControlsListener();

        imageControl = new ImageControl();
        imageControl.addControlsListener(imageControlsListener);


        imageState.addListener(imageStateListener);

        this.add(imageControl, Component.LEFT_ALIGNMENT);

        this.add(scalableImage, BorderLayout.CENTER);
    }

    private SyncContext syncContext = new SyncContext() {
        @Override
        public void onChangeCurrentCell(Cell cell) {
            imageState.setSelectedCell(cell);
        }

        @Override
        public void onChnageCellValue(Cell cell, String value) {
            imageState.setValue(cell, value);
        }
    };

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            scalableImage.setValue(cell, newValue);
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            scalableImage.setCurrentCell(newSelectedCell);
        }
    };

}
