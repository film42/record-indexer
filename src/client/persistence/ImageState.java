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
    private Cell selectedCell;
    private List<ImageStateListener> listeners;

    public ImageState(int recordCount, int fieldCount) {
        values = new String[recordCount][fieldCount];
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

}
