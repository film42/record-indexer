package client.components.fieldHelp;

import client.communication.Communicator;
import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.ImageStateListener;
import client.persistence.NewProjectListener;
import shared.communication.common.Fields;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 8:20 PM
 */
public class FieldHelp extends JPanel {

    private ImageState imageState;

    private String[] columns;
    private int currentColumn;
    private JEditorPane editorPane;
    private Communicator communicator;

    public FieldHelp(ImageState imageState, Communicator communicator) {
        this.imageState = imageState;
        this.communicator = communicator;

        this.currentColumn = 0;
        this.columns = imageState.getColumnNames();

        setupView();

        this.imageState.addListener(imageStateListener);
    }

    private void setupView() {
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }

    private void updateView() {
        if(!imageState.isHasImage()) return;

        Fields field = imageState.getFieldsMetaData().get(currentColumn);
        String path = communicator.getServerPath() + field.getHelpUrl();

        try {
            editorPane.setPage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {

        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            currentColumn = newSelectedCell.getField();
            updateView();
        }
    };
}
