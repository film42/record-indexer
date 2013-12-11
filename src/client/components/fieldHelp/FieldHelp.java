package client.components.fieldHelp;

import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.ImageStateListener;

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

    public FieldHelp(ImageState imageState) {
        this.imageState = imageState;
        this.currentColumn = 0;
        this.columns = imageState.getColumnNames();

        setupView();

        this.imageState.addListener(imageStateListener);
    }

    private void setupView() {
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

//        FOR LATER ;)
//        try {
//            editorPane.setPage("http://google.com/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }

    private void updateView() {
        String html = "<!DOCTYPE html><html><body><h1>"
                     +// + columns[currentColumn] +
                      "</h1>This should be helpful to you.</body></html>";
        editorPane.setText(html);
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
