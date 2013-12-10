package client.components;

import client.components.fieldHelp.FieldHelp;
import client.components.formEntry.FormEntry;
import client.persistence.SyncContext;
import client.components.tableEntry.TableEntry;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:36 PM
 */
public class SplitBase extends JSplitPane {

    public final static int DEFAULT_DIVIDER_LOCATION = 500;

    private ImageState imageState;

    private int dividerLocation;

    public SplitBase(int dividerLocation, ImageState imageState) {
        this.dividerLocation = dividerLocation;

        // TODO: Get this away from here
        this.imageState = imageState;

        setupView();
    }

    private void setupView() {
        JTabbedPane tabbedPane = new JTabbedPane();
        TableEntry tableEntry = new TableEntry(imageState);
        tabbedPane.addTab("Table Entry", tableEntry);

        FormEntry formEntry = new FormEntry(imageState);
        tabbedPane.addTab("Form Entry", formEntry);
        //tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(1));

        this.setLeftComponent(tabbedPane);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.addTab("Field Help", new FieldHelp());
        tabbedPane2.addTab("Image Navigator", new JPanel());

        this.setRightComponent(tabbedPane2);
//        this.setBorder(null);


        this.setDividerLocation(dividerLocation);

    }

}
