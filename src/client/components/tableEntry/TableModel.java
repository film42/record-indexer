package client.components.tableEntry;

import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.ImageStateListener;

import javax.swing.table.AbstractTableModel;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 6:15 PM
 */
public class TableModel extends AbstractTableModel {

    private String[] columnNames;

    private String[][] model;

    private ImageState imageState;

    private boolean quiet = false;

    private boolean updating = false;

    public TableModel(ImageState imageState) {

        this.imageState = imageState;


        this.columnNames = this.imageState.getColumnNames();
        this.model = this.imageState.getModel();

        imageState.addListener(imageStateListener);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return model.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        model[row][column] = (String)value;

        updating = true;
        Cell cell = new Cell();
        cell.setField(column);
        cell.setRecord(row);
        if(!quiet) {
            imageState.setValue(cell, (String)value);
        }
        updating = false;
    }


    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            if(updating) return;

            model[cell.getRecord()][cell.getField()] = newValue;
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            if(updating) return;
        }
    };

    public void setValueQuiet(String newValue, int row, int column) {
        model[row][column] = newValue;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }
}
