package client.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 9:09 AM
 */
public class ImageState {

    private String[][] values;
    private String[] columns;

    private Cell selectedCell;
    private List<ImageStateListener> listeners;

    public ImageState(int recordCount, int fieldCount) {
        values = new String[recordCount][fieldCount];

        FACTORY();

        selectedCell = null;
        listeners = new ArrayList<>();
    }

    public void addListener(ImageStateListener imageStateListener) {
        listeners.add(imageStateListener);
    }

    public void setValue(Cell cell, String value) {
        values[cell.getRecord()][cell.getField()] = value;

        for(ImageStateListener isl : listeners) {
            isl.valueChanged(cell, value);
        }
    }

    public String getValue(Cell cell) {
        return values[cell.getRecord()][cell.getField()];
    }

    public void setSelectedCell(Cell cell) {
        this.selectedCell = cell;

        for(ImageStateListener isl : listeners) {
            isl.selectedCellChanged(cell);
        }
    }

    public Cell getSelectedCell() {
        return this.selectedCell;
    }


    private void FACTORY() {

        columns = new String[] {
                "Last Name", "First Name", "Gender", "Age"
        };

        values = new String[][] {
                {"Mooney1", "Dick", "Male", "3"},
                {"Mooney2", "Dickest", "Male", "3"},
                {"Moone3", "Szz", "Malde", "3"},
                {"Moone4", "", "Maldfe", "3"},
                {"Mooney5", "", "Malse", "3"},
                {"Mooney6", "", "Male", "3"},
                {"Mooney7", "Dicker", "Male", "3"},
                {"Mooney8", "", "Male", "3"}
        };
    }

    public String[][] getModel() {
        return values;
    }

    public String[] getColumnNames() {
        return columns;
    }
}
