package client;

import client.components.MainWindow;
import client.components.downloadModal.DownloadModal;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/28/13
 * Time: 2:15 PM
 */
public class Client {

    public static void main(String[] args) {

        // Create Window

        // Setup Listeners

        // Run
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow frame = new MainWindow();
                frame.setVisible(true);
            }
        });

        // Save before close

        // Close

    }

}
