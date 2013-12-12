package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/10/13
 * Time: 11:17 PM
 */
public class RecordCellEditor extends AbstractCellEditor implements TableCellEditor {

    private String currentValue;
    private JTextField textField;

    private ImageState imageState;

    public RecordCellEditor(ImageState imageState) {
        this.imageState = imageState;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {

        textField = new JTextField();
        currentValue = (String)value;

        textField.setText(currentValue);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        textField.setEnabled(false);

        textField.addMouseListener(generateMouseListener(row, column));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                System.out.println("focs");
            }
        });

        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    private MouseListener generateMouseListener(final int row, final int column){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);

                Cell cell = new Cell();
                cell.setRecord(row);
                cell.setField(0);
                imageState.setSelectedCell(cell);
            }
        };
    }
}
