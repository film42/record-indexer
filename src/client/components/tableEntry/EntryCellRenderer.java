package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 6:58 PM
 */
public class EntryCellRenderer extends JLabel implements TableCellRenderer {

    private ImageState imageState;

    public EntryCellRenderer(ImageState imageState) {
        this.imageState = imageState;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        this.setText((String)value);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        if(isSelected) {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            Cell cell = new Cell();
            cell.setRecord(row);
            cell.setField(column);
                    imageState.setSelectedCell(cell);
        }

        return this;
    }


}
