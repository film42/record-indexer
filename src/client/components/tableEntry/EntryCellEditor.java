package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {

        textField = new JTextField();
        currentValue = (String)value;

        textField.setText(currentValue);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

        textField.addMouseListener(rightClickPopupAction);

        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
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