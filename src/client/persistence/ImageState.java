package client.persistence;

import java.awt.image.BufferedImage;
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

    private String username;
    private String password;

    private int firstYCoord = 199;
    private int recordHeight = 60;
    private int columnCount = 4;
    private int recordsPerImage = 8;

    private ArrayList<Integer> fieldXValues;
    private ArrayList<Integer> fieldWidthValues;

    private Settings settings;

    public ImageState(Settings settings, String username, String password) {
        this.settings = settings;
        this.username = username;
        this.password = password;

        values = new String[0][0];

        values = settings.getValues();
        columns = settings.getColumns();

        selectedCell = null;
        listeners = new ArrayList<>();

        loadFromSettings();
    }

    public void loadFromSettings() {
        firstYCoord = 0;
        recordHeight = 0;
        columnCount = 0;
        recordsPerImage = 0;
//        firstYCoord = 199;
//        recordHeight = 60;
//        columnCount = 4;
//        recordsPerImage = 8;
//
//        fieldXValues.add(60);
//        fieldXValues.add(360);
//        fieldXValues.add(640);
//        fieldXValues.add(845);
//
//        fieldWidthValues.add(300);
//        fieldWidthValues.add(280);
//        fieldWidthValues.add(205);
//        fieldWidthValues.add(120);
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


    public Settings getSettings() {
        return settings;
    }

    public String[][] getModel() {
        return values;
    }

    public String[] getColumnNames() {
        return columns;
    }

    public void save() {

    }

    public ArrayList<Integer> getFieldWidthValues() {
        return fieldWidthValues;
    }

    public ArrayList<Integer> getFieldXValues() {
        return fieldXValues;
    }

    public int getRecordsPerImage() {
        return recordsPerImage;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRecordHeight() {
        return recordHeight;
    }

    public int getFirstYCoord() {
        return firstYCoord;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[][] getValues() {
        return values;
    }

    public BufferedImage getImage() {

        return settings.getImage();

    }
}
