package client.components.imagePanel;

import client.components.imagePanel.ImageCell;
import client.persistence.Cell;
import client.persistence.ImageState;
import client.persistence.ImageStateListener;
import client.persistence.SyncContext;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 10:29 PM
 */
public class ImageTable {

    private int recordsPerImage;
    private int firstYCoord;
    private int recordHeight;
    private int columnCount;

    private ArrayList<Integer> fieldXValues;
    private ArrayList<Integer> fieldWidthValues;

    private ImageCell[][] model;

    private Rectangle2D.Double tableBoundaries;
    private boolean highlightsEnabled;

    private ImageCell currentSelected;

    private ImageState imageState;
    private boolean deactivated = false;

    public ImageTable(ImageState imageState) {

        this.imageState = imageState;

        this.recordsPerImage = imageState.getRecordsPerImage();
        this.firstYCoord = imageState.getFirstYCoord();
        this.recordHeight = imageState.getRecordHeight();
        this.columnCount = imageState.getColumnCount();
        this.fieldXValues = imageState.getFieldXValues();
        this.fieldWidthValues = imageState.getFieldWidthValues();

        // Note we go [y][x]
        model = new ImageCell[recordsPerImage][columnCount];

        highlightsEnabled = imageState.getSettings().isImageHighlights();

        if(!imageState.isHasImage()) return;

        generateModel();
        generateTableBoundaries();
    }

    private void generateModel() {
        for(int y = 0; y < recordsPerImage; y++) {
            for(int x = 0; x < columnCount; x++) {
                Rectangle2D.Double rect = new Rectangle2D.Double();

                int rectY = firstYCoord + (y * recordHeight); // y starts at 0.
                int rectX = fieldXValues.get(x);
                int width = fieldWidthValues.get(x);
                int height = recordHeight;

                rect.setRect(rectX, rectY, width, height);

                model[y][x] = new ImageCell(rect);
            }
        }
    }

    public void paint(Graphics2D g2) {
        for(int y = 0; y < recordsPerImage; y++) {
            for(int x = 0; x < columnCount; x++) {
                ImageCell imageCell = model[y][x];

                if(imageCell == currentSelected && highlightsEnabled) {
                    model[y][x].paint(g2, true);
                } else {
                    model[y][x].paint(g2, false);
                }
            }
        }
    }

    public void contains(int worldX, int worldY) {
        // Make sure in table boundaries first.
        if(!tableBoundaries.contains(worldX, worldY)) {
            return;
        }

        for(int y = 0; y < recordsPerImage; y++) {
            for(int x = 0; x < columnCount; x++) {
                ImageCell imageCell = model[y][x];
                if(imageCell.contains(worldX, worldY)) {
                    // Laws of math state there is only ever one.
                    this.currentSelected = imageCell;
                    setCurrentCell(x, y);

                    Cell cell = new Cell();
                    cell.setField(x);
                    cell.setRecord(y);
                    this.imageState.setSelectedCell(cell);
                }
            }
        }
    }

    public void setCurrentCell(int x, int y) {
        if(model.length == 0 || deactivated) return;

        // TODO: Fix array index out of bounds error on new batch

        this.currentSelected = model[y][x];
    }

    private void generateTableBoundaries() {

        int y = firstYCoord;
        int x = fieldXValues.get(0);

        int width = 0;
        for(int fw : fieldWidthValues) {
            width += fw;
        }

        int height = recordsPerImage * recordHeight;

        tableBoundaries = new Rectangle2D.Double(x, y, width, height);
    }

    public void enableHighlights(boolean value) {
        this.highlightsEnabled = value;

        imageState.getSettings().setImageHighlights(value);
    }

    public boolean isHighlightsEnabled() {
        return highlightsEnabled;
    }


    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public boolean isDeactivated() {
        return deactivated;
    }
}
