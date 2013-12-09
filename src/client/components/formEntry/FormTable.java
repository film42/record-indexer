package client.components.formEntry;

import client.persistence.Cell;
import client.persistence.SyncContext;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/8/13
 * Time: 1:57 PM
 */
public class FormTable extends JPanel {

    private String[] fieldNames;
    private String[][] values;

    private boolean updatingCell;

    private SyncContext syncContext;

    private int currentRow;

    public FormTable(SyncContext syncContext, String[] fieldNames, String[][] values) {
        this.fieldNames = fieldNames;
        this.values = values;
        this.syncContext = syncContext;

        this.currentRow = 0;

        setupView();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(int i = 0; i < fieldNames.length; i++) {
            String labelString = fieldNames[i];
            String textFieldString = values[currentRow][i];

            JPanel formContainer = new JPanel();

            JLabel label = new JLabel(labelString);
            label.setPreferredSize(new Dimension(100,30));
            formContainer.add(label, BorderLayout.WEST);
            JTextField textField = new JTextField(textFieldString);
            //textField.addFocusListener(generateFocusListener(textField, i));
            textField.addMouseListener(generateMouseListener(textField, i));
            textField.getDocument().addDocumentListener(generateDocumentListener(textField, i));
            textField.setPreferredSize(new Dimension(150, 30));
            formContainer.add(textField, BorderLayout.CENTER);

            this.add(formContainer);
        }

        currentRow = 2;
        updateCellValue(null, 1);
    }

    public void updateCellValue(JTextField textField, int index) {
        if(updatingCell) return;

        updatingCell = true;

        Cell cell = new Cell();
        cell.setRecord(currentRow);
        cell.setField(index);

        syncContext.onChangeCurrentCell(cell);
    }

//    private FocusListener generateFocusListener(final JTextField textField, final int index) {
//        return new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                updateCellValue(textField, index);
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                updateCellValue(textField, index);
//            }
//        };
//    }

    private MouseListener generateMouseListener(final JTextField textField, final int index) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                updateCellValue(textField, index);
            }
        };
    }

    private DocumentListener generateDocumentListener(final JTextField textField, final int index) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCellValue(textField, index);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCellValue(textField, index);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCellValue(textField, index);
            }
        };
    }

    private void updateView() {
        updatingCell = true;
        for(int i = 0; i < this.getComponents().length; i++) {
            JPanel formSet = (JPanel)this.getComponent(i);
            JTextField form = (JTextField)formSet.getComponent(1);
            form.setText(values[currentRow][i]);
        }
        updatingCell = false;
    }

    public void setValues(String[][] values) {
        // TODO: Become model
        this.values = values;
    }

    public void setValue(String newValue, int row, int column) {
        values[row][column] = newValue;

        this.updateView();
        this.repaint();

        this.setFocus(column);
    }

    public void setCurrentCell(int row, int column) {

        this.currentRow = row;

        this.updateView();
        this.repaint();

        this.setFocus(column);

    }

    public void setFocus(int columnField) {
        // offset is x - 1, cause start at 0.
        int column = columnField;

        // get the column textField and request focus
        JPanel formList = (JPanel)this.getComponent(column);
        final JTextField form = (JTextField)formList.getComponent(1);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                form.requestFocusInWindow();
            }
        });
    }

}
