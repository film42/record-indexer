package client.components;

import client.components.fieldHelp.*;
import client.components.fieldHelp.FieldHelp;
import client.components.tableEntry.TableEntry;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:36 PM
 */
public class SplitBase extends JSplitPane {

    public final static int DEFAULT_DIVIDER_LOCATION = 500;

    private int dividerLocation;

    public SplitBase(int dividerLocation) {
        this.dividerLocation = dividerLocation;

        setupView();
    }

    private void setupView() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Table Entry", new TableEntry());
        tabbedPane.addTab("Form Entry", new JPanel());

        this.setLeftComponent(tabbedPane);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.addTab("Field Help", new FieldHelp());
        tabbedPane2.addTab("Image Navigator", new JPanel());

        this.setRightComponent(tabbedPane2);
//        this.setBorder(null);


        this.setDividerLocation(dividerLocation);

    }
}
