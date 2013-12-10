package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 8:00 PM
 */
public class EntryCellEditor extends AbstractCellEditor implements TableCellEditor {

    private String currentValue;
    private JTextField textField;

    private ImageState imageState;

    public EntryCellEditor(ImageState imageState) {
        this.imageState = imageState;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {

        textField = new JTextField();
        currentValue = (String)value;

        textField.setText(currentValue);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

        textField.addMouseListener(rightClickPopupAction);
        textField.addFocusListener(generateFocusListener(row, column));

        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    private FocusListener generateFocusListener(final int row, final int column) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Cell cell = new Cell();
                cell.setRecord(row);
                cell.setField(column);

                imageState.setSelectedCell(cell);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        };
    }

    private MouseListener rightClickPopupAction = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.isPopupTrigger()) {
                SpellCheckPopup spellCheckPopup = new SpellCheckPopup();
                spellCheckPopup.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };
}