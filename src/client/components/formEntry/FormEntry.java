package client.components.formEntry;

import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.SyncContext;
import client.persistence.ImageStateListener;

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

    private String[][] model;
    private String[] columnNames;
    private Integer[] rowIds;

    private ImageState imageState;

    public FormEntry(ImageState imageState) {
        this.imageState = imageState;

        this.model = this.imageState.getModel();
        this.columnNames = this.imageState.getColumnNames();

        this.rowIds = new Integer[model.length];
        generateListData();

        setupView();
    }

    private void setupView() {
        this.setLayout(new GridLayout(1,1));
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
        //rowNumberList.addFocusListener(focusListener);
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
        // TODO Auto-generated method stub
        Dimension dim = super.getMinimumSize();
        dim.width = 350;
        return dim;
    }

    private Cell currentCell;

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

    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // OMG the hackery!!
            if(!formTable.getParent().getParent().getParent().getParent().isVisible()) return;

            int newRow = rowNumberList.getSelectedIndex();

            Cell cell = new Cell();
            cell.setRecord(newRow);
            // TODO: Fix so this wont be a Null Pointer, maybe be setting default to 0,0?
            cell.setField(currentCell.getField());

            // TODO: Fix this to call back all listeners, probably set I.S. global
            //imageStateListener.selectedCellChanged(cell);
            imageState.setSelectedCell(cell);
            repaint();

        }
    };

//    private MouseListener mouseListener = new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            super.mouseClicked(e);
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            super.mouseDragged(e);
//        }
//    };
//
//    // TODO: Swap for something more slide friendly
//    private FocusListener focusListener = new FocusListener() {
//        @Override
//        public void focusGained(FocusEvent e) {
//            int newRow = rowNumberList.getSelectedIndex();
//
//            Cell cell = new Cell();
//            cell.setRecord(newRow);
//            // TODO: Fix so this wont be a Null Pointer, maybe be setting default to 0,0?
//            cell.setField(currentCell.getField());
//
//            // TODO: Fix this to call back all listeners, probably set I.S. global
//            //imageStateListener.selectedCellChanged(cell);
//            imageState.setSelectedCell(cell);
//            repaint();
//        }
//
//        @Override
//        public void focusLost(FocusEvent e) {
//            return;
//        }
//    };

}
