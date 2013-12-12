package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.persistence.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:00 AM
 */
public class TableEntry extends JScrollPane {

    private TableModel tableModel;
    private JTable table;

    private ImageState imageState;


    public TableEntry(ImageState imageState) {

        this.imageState = imageState;
        this.tableModel = new TableModel(imageState);

        this.imageState.addNewProjectListener(newProjectListener);

        setupView();

        if(imageState.isHasImage())
            imageState.addListener(imageStateListener);
    }

    private void setupView() {

        table = new JTable(tableModel);

        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tableInit();

    }

    private void tableInit() {
        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 1; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            EntryCellRenderer entryCellRenderer = new EntryCellRenderer(imageState);
            column.setCellRenderer(entryCellRenderer);
            column.setCellEditor(new EntryCellEditor(imageState));
        }
        this.getViewport().add(table.getTableHeader());
        this.getViewport().add(table);

        if(!imageState.isHasImage()) return;

        TableColumn column = columnModel.getColumn(0);
        column.setCellRenderer(new RecordCellRenderer(imageState));
        column.setCellEditor(new RecordCellEditor(imageState));
    }

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            //tableModel.setValueQuiet(newValue, cell.getRecord(), cell.getField() + 1);
            //table.repaint();
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            tableModel.setQuiet(true);

            table.changeSelection(newSelectedCell.getRecord(),
                    newSelectedCell.getField() + 1, false, false);

            table.editCellAt(newSelectedCell.getRecord(), newSelectedCell.getField() + 1);

            tableModel.setQuiet(false);
        }
    };

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {
            tableModel.setDeactivated(true);

            if(imageState.isHasImage())
                imageState.addListener(imageStateListener);

            tableModel = new TableModel(imageState);
            table.setModel(tableModel);

            tableInit();
        }
    };


}
