package client.components.tableEntry;

import client.modules.spellChecker.KnownData;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/11/13
 * Time: 12:26 AM
 */
public class RecordCellRenderer extends JLabel implements TableCellRenderer {

    private ImageState imageState;

    public RecordCellRenderer(ImageState imageState) {
        this.imageState = imageState;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        this.setText((String)value);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        if(isSelected) {
            Cell cell = new Cell();
            cell.setRecord(row);
            cell.setField(0);
            imageState.setSelectedCell(cell);
        }

        return this;
    }

}