package client.components;

import client.communication.Communicator;
import client.components.downloadModal.DownloadModal;
import client.components.imagePanel.ImagePanel;
import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.NewProjectListener;
import client.persistence.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:05 AM
 */
public class MainWindow extends JFrame implements Serializable {

    public ImageState imageState;
    private Communicator communicator;

    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel());

    public MainWindow(Communicator communicator, String username, String password) {
        Settings settings = loadSettings(username);

        this.imageState = loadImageState(username);

        if(this.imageState == null) {
            this.imageState = new ImageState(settings, communicator, username, password);
        } else {
            this.imageState.setCommunicator(communicator);
        }

        this.imageState.setSettings(settings);

        this.imageState.addNewProjectListener(newProjectListener);
        this.communicator = communicator;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(settings.getWindowWidth(), settings.getWindowHeight());
        this.setLocation(settings.getWindowPositionX(), settings.getWindowPositionY());

        setupView();

        this.imageState.initEvents();
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
        this.add(new FileMenu(this, communicator, imageState), BorderLayout.NORTH);
    }

    private void setupImagePanel() {
        body.setTopComponent(new ImagePanel(imageState));
    }

    private void setupSplitView() {
        SplitBase splitBase = new SplitBase(imageState, communicator);

        body.setBottomComponent(splitBase);
        body.setBorder(null);
        body.setDividerLocation(imageState.getSettings().getBaseSplitY());
    }

    public ImageState loadImageState(String username) {
        File dest = new File("profiles/"+username);
        if(dest.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream("profiles/"+username+"/state.ser");
                in = new ObjectInputStream(fis);
                return (ImageState)in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Settings loadSettings(String username) {
        File dest = new File("profiles/"+username);
        if(dest.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream("profiles/"+username+"/settings.ser");
                in = new ObjectInputStream(fis);
                return (Settings)in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return Settings.defaultSettings();
    }

    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

        }

        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);

        }
    };

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {


        }
    };
}
