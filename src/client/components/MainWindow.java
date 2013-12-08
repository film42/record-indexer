package client.components;

import client.components.imagePanel.ImagePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:05 AM
 */
public class MainWindow extends JFrame {

    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel());

    public MainWindow() {
        this.setSize(1000, 700);
        setupView();
    }

    private void setupView() {
        //this.setLayout(new GridBagLayout());

        setupFileMenu();
        setupImagePanel();
        setupSplitView();

        this.add(body, BorderLayout.CENTER);
    }

    private void setupFileMenu() {
        // Setup File Menu
        this.add(new FileMenu(), BorderLayout.NORTH);
    }

    private void setupImagePanel() {
        body.setTopComponent(new ImagePanel());
    }

    private void setupSplitView() {
        SplitBase splitBase = new SplitBase(SplitBase.DEFAULT_DIVIDER_LOCATION);

        body.setBottomComponent(splitBase);
        body.setBorder(null);
        body.setDividerLocation(450);

        //body.setPreferredSize(new Dimension(800, 100));
    }
}
