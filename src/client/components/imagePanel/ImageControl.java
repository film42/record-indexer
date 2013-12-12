package client.components.imagePanel;

import client.components.imagePanel.listeners.ImageControlsListener;
import client.persistence.ImageState;
import client.persistence.NewProjectListener;

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

    private ImageState imageState;

    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton invertButton;
    private JButton toggleHighlightsButton;
    private JButton saveButton;
    private JButton submitButton;

    public ImageControl(ImageState imageState) {
        this.imageState = imageState;

        this.imageState.addNewProjectListener(newProjectListener);

        setupView();

        imageControlsListeners = new ArrayList<>();
    }

    private void setupView() {
        boolean enabled = false;

        if(imageState.isHasImage()) enabled = true;

        zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(zoomInAction);
        zoomInButton.setEnabled(enabled);
        this.add(zoomInButton, BorderLayout.WEST);

        zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(zoomOutAction);
        zoomOutButton.setEnabled(enabled);
        this.add(zoomOutButton, BorderLayout.WEST);

        invertButton = new JButton("Invert");
        invertButton.addActionListener(invertImageAction);
        invertButton.setEnabled(enabled);
        this.add(invertButton, BorderLayout.WEST);

        toggleHighlightsButton = new JButton("Toggle Highlights");
        toggleHighlightsButton.addActionListener(toggleHighlightsAction);
        toggleHighlightsButton.setEnabled(enabled);
        this.add(toggleHighlightsButton, BorderLayout.WEST);

        saveButton = new JButton("Save");
        saveButton.setEnabled(enabled);
        saveButton.addActionListener(saveAction);
        this.add(saveButton, BorderLayout.WEST);

        submitButton = new JButton("Submit");
        submitButton.setEnabled(enabled);
        submitButton.addActionListener(submitAction);
        this.add(submitButton, BorderLayout.WEST);

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

    private void updateToggleHighlightsListeners() {
        for(ImageControlsListener cL : imageControlsListeners) {
            cL.onToggleHighlights();
        }
    }

    private void updateSaveListeners() {
        imageState.save();
    }

    private void updateSubmitListeners() {
        imageState.submitProject();
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
            updateToggleHighlightsListeners();
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

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {
            boolean status = imageState.isHasImage();

            zoomInButton.setEnabled(status);
            zoomOutButton.setEnabled(status);
            invertButton.setEnabled(status);
            toggleHighlightsButton.setEnabled(status);
            saveButton.setEnabled(status);
            submitButton.setEnabled(status);
        }
    };

}
