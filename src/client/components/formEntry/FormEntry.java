package client.components.formEntry;

import client.modules.spellChecker.KnownData;
import client.persistence.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 10:59 AM
 */
public class FormEntry extends JPanel {

    private JList rowNumberList;
    private FormTable formTable;
    private JSplitPane splitPane;

    private Cell currentCell;
    private String[][] model;
    private String[] columnNames;
    private Integer[] rowIds;

    private ImageState imageState;

    public FormEntry(ImageState imageState) {
        this.imageState = imageState;

        this.model = this.imageState.getModel();
        this.columnNames = this.imageState.getColumnNames();

        this.imageState.addNewProjectListener(newProjectListener);

        setupView();
    }

    private void setupView() {
        this.setLayout(new GridLayout(1,1));

        this.rowIds = new Integer[model.length];
        generateListData();

        splitPane = new JSplitPane();
        splitPane.setDividerLocation(50);
        splitPane.setBorder(null);

        formTable = new FormTable(imageState);

        splitPane.setRightComponent(new JScrollPane(formTable));

        rowNumberList = new JList(rowIds);
        rowNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowNumberList.setLayoutOrientation(JList.VERTICAL);
        rowNumberList.setVisibleRowCount(-1);
        rowNumberList.addListSelectionListener(listSelectionListener);
        splitPane.setLeftComponent(new JScrollPane(rowNumberList));


        this.add(splitPane);

        imageState.addListener(imageStateListener);

    }

    public void generateListData() {
        for(int i = 0; i < rowIds.length; i ++) {
            rowIds[i] = (i+1);
        }
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension dim = super.getMinimumSize();
        dim.width = 350;
        return dim;
    }

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            int row = cell.getRecord();
            int column = cell.getField();

            rowNumberList.setSelectedIndex(row);
            formTable.setValue(newValue, row, column);

            splitPane.repaint();
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            int row = newSelectedCell.getRecord();
            int column = newSelectedCell.getField();

            currentCell = newSelectedCell;

            rowNumberList.setSelectedIndex(row);
            formTable.setCurrentCell(row, column);

            splitPane.repaint();
        }
    };

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {
            model = imageState.getModel();
            columnNames = imageState.getColumnNames();

            formTable.setDeactivated(true);
            formTable = new FormTable(imageState);
            splitPane.setRightComponent(new JScrollPane(formTable));

            rowIds = new Integer[model.length];
            generateListData();
            rowNumberList.setListData(rowIds);
        }
    };

    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // OMG the hackery!!
            if(!getParent().getParent().getParent().isVisible()) return;

            int newRow = rowNumberList.getSelectedIndex();

            Cell cell = new Cell();
            cell.setRecord(newRow);
            cell.setField(currentCell.getField());

            imageState.setSelectedCell(cell);
            repaint();

        }
    };

    public void becameVisible() {
        formTable.setCurrentCellForce();
    }


}
