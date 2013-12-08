package client.components.imagePanel;

import client.components.imagePanel.listeners.ImageControlsListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 2:33 PM
 */
public class ImageControl extends JPanel {

    private ArrayList<ImageControlsListener> imageControlsListeners;

    public ImageControl() {
        setupView();

        imageControlsListeners = new ArrayList<>();
    }

    private void setupView() {
        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(zoomInAction);
        this.add(zoomInButton, BorderLayout.WEST);

        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(zoomOutAction);
        this.add(zoomOutButton, BorderLayout.WEST);

        JButton invertButton = new JButton("Invert");
        invertButton.addActionListener(invertImageAction);
        this.add(invertButton, BorderLayout.WEST);

        JButton toggleHighlightsButton = new JButton("Toggle Highlights");
        toggleHighlightsButton.addActionListener(toggleHighlightsAction);
        this.add(toggleHighlightsButton, BorderLayout.WEST);

        this.add(new JButton("Save"), BorderLayout.WEST);
        this.add(new JButton("Submit"), BorderLayout.WEST);

        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    }

    @Override
    public Dimension getMaximumSize() {
        // TODO Auto-generated method stub
        Dimension dim = super.getMaximumSize();
        dim.height = 60;
        return dim;
    }

    public void addControlsListener(ImageControlsListener imageControlsListener) {
        imageControlsListeners.add(imageControlsListener);
    }

    /* ********************************************
                  Listener Stubs
     ******************************************** */
    private void updateZoomInListeners() {
        for(ImageControlsListener cL : imageControlsListeners) {
            cL.onScrollIncrease();
        }
    }

    private void updateZoomOutListeners() {
        for(ImageControlsListener cL : imageControlsListeners) {
            cL.onScrollDecrease();
        }
    }

    private void updateInvertImageListeners() {
        for(ImageControlsListener cL : imageControlsListeners) {
            cL.onInvertImage();
        }
    }

    private void updateToogleHighlightsListeners() {
        for(ImageControlsListener cL : imageControlsListeners) {
            cL.onToggleHighlights();
        }
    }

    private void updateSaveListeners() {

    }

    private void updateSubmitListeners() {

    }


    /* ********************************************
                     Button Listener
     ******************************************** */

    private ActionListener zoomInAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateZoomInListeners();
        }
    };

    private ActionListener zoomOutAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateZoomOutListeners();
        }
    };

    private ActionListener invertImageAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateInvertImageListeners();
        }
    };

    private ActionListener toggleHighlightsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateToogleHighlightsListeners();
        }
    };

    private ActionListener saveAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateSaveListeners();
        }
    };

    private ActionListener submitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateSubmitListeners();
        }
    };

}
