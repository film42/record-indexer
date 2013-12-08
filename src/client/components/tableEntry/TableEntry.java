package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:00 AM
 */
public class TableEntry extends JScrollPane {

    TableModel tableModel;
    JTable table;


    public TableEntry() {

        tableModel = new TableModel();
        setupView();
    }

    private void setupView() {

        table = new JTable(tableModel);

        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            EntryCellRenderer entryCellRenderer = new EntryCellRenderer();
            column.setCellRenderer(entryCellRenderer);
            column.setCellEditor(new EntryCellEditor());
        }

        this.getViewport().add(table.getTableHeader());
        this.getViewport().add(table);
    }
}
