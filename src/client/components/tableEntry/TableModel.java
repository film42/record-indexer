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

    private boolean deactivated = false;

    public TableModel(ImageState imageState) {

        this.imageState = imageState;
        this.model = this.imageState.getModel();
        this.columnNames = this.imageState.getColumnNames();

        this.imageState.addListener(imageStateListener);

        if(!this.imageState.isHasImage()) return;
        overrideTableModel();

    }

    private void overrideTableModel() {
        String[] imageStateColumns = this.imageState.getColumnNames();
        int width = imageStateColumns.length;

        String[][] imageStateModel = this.imageState.getModel();

        this.model = new String[imageStateModel.length][width + 1];

        this.columnNames = new String[width + 1];
        this.columnNames[0] = "Record Number";


        // Copy Column names from image state so we can have record number
        for(int i = 0; i < width; i++) {
            this.columnNames[i+1] = imageStateColumns[i];
        }

        // Copy Model values
        for(int x = 0; x < imageStateModel.length; x ++) {
            // Set row number first
            this.model[x][0] = Integer.toString(x + 1);

            // Copy the values with the new offset
            for(int i = 0; i < width; i++) {
                this.model[x][i+1] = imageStateModel[x][i];
            }
        }
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
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
        if(quiet || deactivated) return;

        model[row][column] = (String)value;

        updating = true;
        Cell cell = new Cell();
        cell.setField(column - 1);

        // Check for record number col which is 0
        if(column == 0) {
            cell.setField(0);
        }

        cell.setRecord(row);
        imageState.setValue(cell, (String)value);
        updating = false;
    }


    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            if(updating || deactivated) return;

            model[cell.getRecord()][cell.getField() + 1] = newValue;
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            if(updating) return;
        }
    };

    public void setValueQuiet(String newValue, int row, int column) {
        if(deactivated) return;

        model[row][column] = newValue;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }
}
