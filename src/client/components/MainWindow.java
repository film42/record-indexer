package client.components;

import client.communication.Communicator;
import client.components.downloadModal.DownloadModal;
import client.components.imagePanel.ImagePanel;
import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:05 AM
 */
public class MainWindow extends JFrame {

    public ImageState imageState;

    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel());

    public MainWindow(Communicator communicator, String username, String password) {
        this.imageState = new ImageState(new Settings(), username, password);
        Settings settings = imageState.getSettings();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(settings.getWindowWidth(), settings.getWindowHeight());
        this.setLocation(settings.getWindowPositionX(), settings.getWindowPositionY());

        setupView();

        // TODO: Remove Factory
        Cell initCell = new Cell();
        initCell.setField(0);
        initCell.setRecord(0);
        this.imageState.setSelectedCell(initCell);
        this.addWindowListener(windowListener);
    }

    private void setupView() {
        //this.setLayout(new GridBagLayout());

        setupFileMenu();
        setupImagePanel();
        setupSplitView();

        this.add(body, BorderLayout.CENTER);

        body.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                int dividerLocation = body.getDividerLocation();

                imageState.getSettings().setBaseSplitY(dividerLocation);
            }
        });
    }

    private void setupFileMenu() {
        // Setup File Menu
        this.add(new FileMenu(this), BorderLayout.NORTH);
    }

    private void setupImagePanel() {
        body.setTopComponent(new ImagePanel(imageState));
    }

    private void setupSplitView() {
        SplitBase splitBase = new SplitBase(imageState);

        body.setBottomComponent(splitBase);
        body.setBorder(null);
        body.setDividerLocation(imageState.getSettings().getBaseSplitY());

        //body.setPreferredSize(new Dimension(800, 100));
    }

    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            imageState.getSettings().setWindowHeight(getHeight());
            imageState.getSettings().setWindowWidth(getWidth());

            Point point = getLocationOnScreen();
            imageState.getSettings().setWindowPositionX((int) point.getX());
            imageState.getSettings().setWindowPositionY((int) point.getY());
            imageState.save();
        }
    };
}
