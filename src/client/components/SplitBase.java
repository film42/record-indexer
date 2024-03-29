package client.components;

import client.communication.Communicator;
import client.components.fieldHelp.FieldHelp;
import client.components.formEntry.FormEntry;
import client.persistence.SyncContext;
import client.components.tableEntry.TableEntry;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:36 PM
 */
public class SplitBase extends JSplitPane {

    private ImageState imageState;
    private JTabbedPane tabbedPane;
    private TableEntry tableEntry;
    private FormEntry formEntry;
    private Communicator communicator;

    public SplitBase(ImageState imageState, Communicator communicator) {
        this.imageState = imageState;
        this.communicator = communicator;

        setupView();

    }

    private void setupView() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(changeListener);

        tableEntry = new TableEntry(imageState);
        tabbedPane.addTab("Table Entry", tableEntry);

        formEntry = new FormEntry(imageState);
        tabbedPane.addTab("Form Entry", formEntry);

        this.setLeftComponent(tabbedPane);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.addTab("Field Help", new FieldHelp(imageState, communicator));
        tabbedPane2.addTab("Image Navigator", new JPanel());

        this.setRightComponent(tabbedPane2);

        this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                int dividerLocation = getDividerLocation();

                imageState.getSettings().setBaseSplitX(dividerLocation);
            }
        });


        this.setDividerLocation(imageState.getSettings().getBaseSplitX());

    }

    private ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(tabbedPane.getSelectedIndex() == 1) {
                formEntry.becameVisible();
            }
        }
    };

}
