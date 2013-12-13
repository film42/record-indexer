package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.modules.spellChecker.KnownData;
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

    public boolean hasSuggestion(String value, int column) {

        if(value.equals("")) return false;

        KnownData knownData = imageState.getKnownDataValues().get(column);

        for(String val : knownData.getWords()) {
            if(val.toLowerCase().equals(value.toLowerCase())) return false;
        }

        return true;
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
            cell.setField(column - 1);
            imageState.setSelectedCell(cell);
        }

        if(hasSuggestion((String)value, column - 1)) {
            this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            this.setBackground(Color.RED);
        }

        return this;
    }

}
