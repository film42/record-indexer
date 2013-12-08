package client.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:36 PM
 */
public class SplitBase extends JSplitPane {

    public SplitBase() {
        setupView();
    }

    private void setupView() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Table Entry", new JPanel());
        tabbedPane.addTab("Form Entry", new JPanel());

        this.setLeftComponent(tabbedPane);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.addTab("Field Help", new JPanel());
        tabbedPane2.addTab("Image Navigator", new JPanel());

        this.setRightComponent(tabbedPane2);
//        this.setBorder(null);

        this.setDividerLocation(250);
    }
}
