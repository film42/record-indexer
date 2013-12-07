package client.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
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
        this.setSize(800, 400);
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
        body.setBottomComponent(new SplitBase());
        body.setBorder(null);

        //body.setPreferredSize(new Dimension(800, 100));
    }
}
