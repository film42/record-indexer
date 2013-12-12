package client.components;

import client.communication.Communicator;
import client.components.downloadModal.DownloadModal;
import client.components.loginWindow.ErrorLoginDialog;
import client.persistence.ImageState;
import client.persistence.NewProjectListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:18 AM
 */
public class FileMenu extends JMenuBar {
    private MainWindow mainWindow;
    private Communicator communicator;
    private ImageState imageState;
    private JMenuItem eMenuItem1;

    public FileMenu(MainWindow mainWindow, Communicator communicator, ImageState imageState) {
        this.mainWindow = mainWindow;
        this.communicator = communicator;
        this.imageState = imageState;

        this.imageState.addNewProjectListener(newProjectListener);

        setupView();
    }

    private void setupView() {
        // Prevents menu items from filling the whole length
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JMenu file1 = new JMenu("File");

        eMenuItem1 = new JMenuItem("Download Batch");
        eMenuItem1.addActionListener(downloadBatchAction);
        eMenuItem1.setEnabled(!imageState.isHasImage());
        eMenuItem1.setToolTipText("Exit application");

        JMenuItem eMenuItem2 = new JMenuItem("Logout");
        eMenuItem2.addActionListener(logoutAction);
        eMenuItem2.setToolTipText("Exit application");

        JMenuItem eMenuItem3 = new JMenuItem("Exit");
        eMenuItem3.addActionListener(exitAction);
        eMenuItem2.setToolTipText("Exit application");

        file1.add(eMenuItem1);
        file1.add(eMenuItem2);
        file1.add(eMenuItem3);

        // Add to self
        this.add(file1);
        this.setBackground(Color.WHITE);
    }

    private void updateSettings() {
        imageState.getSettings().setWindowHeight(mainWindow.getHeight());
        imageState.getSettings().setWindowWidth(mainWindow.getWidth());

        Point point = mainWindow.getLocationOnScreen();
        imageState.getSettings().setWindowPositionX((int) point.getX());
        imageState.getSettings().setWindowPositionY((int) point.getY());
        imageState.save();
    }


    private ActionListener downloadBatchAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        DownloadModal downloadModal = new DownloadModal(imageState, communicator);
        downloadModal.setVisible(true);
        }
    };

    private ActionListener logoutAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        updateSettings();
        mainWindow.dispose();
        }
    };

    private ActionListener exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        updateSettings();
        System.exit(1);
        }
    };

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {
        boolean status = imageState.isHasImage();
        eMenuItem1.setEnabled(!status);
        }
    };

}
