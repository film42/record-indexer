package client.components.formEntry;

import client.components.menus.SpellCheckPopup;
import client.modules.spellChecker.KnownData;
import client.modules.spellChecker.SpellChecker;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
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

    private ImageState imageState;

    private int currentRow;
    private boolean deactivated = false;

    public FormTable(ImageState imageState) {

        this.imageState = imageState;

        this.fieldNames = this.imageState.getColumnNames();
        this.values = this.imageState.getModel();

        this.currentRow = 0;

        setupView();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        currentRow = 0;

        initForm();

    }

    private void initForm() {
        for(int i = 0; i < fieldNames.length; i++) {
            String labelString = fieldNames[i];
            String textFieldString = values[currentRow][i];

            JPanel formContainer = new JPanel();

            JLabel label = new JLabel(labelString);
            label.setPreferredSize(new Dimension(100,30));
            formContainer.add(label, BorderLayout.WEST);

            JTextField textField = new JTextField(textFieldString);
            textField.addFocusListener(generateFocusListener(textField, i));
            textField.setPreferredSize(new Dimension(150, 30));

            if(hasSuggestion(textField.getText(), i)) {
                textField.setBackground(Color.RED);
                MouseListener listener = generateRightClickPopupAction(textField);
                textField.addMouseListener(listener);
            } else {
                textField.removeMouseListener(generateRightClickPopupAction(textField));
            }

            formContainer.add(textField, BorderLayout.CENTER);

            this.add(formContainer);
        }
    }

    public void updateCurrentCell(JTextField textField, int index) {
        if(updatingCell || deactivated) return;

        Cell cell = new Cell();
        cell.setRecord(currentRow);
        cell.setField(index);

        updatingCell = true;
        imageState.setSelectedCell(cell);
        updatingCell = false;
    }

    private MouseListener generateRightClickPopupAction(final JTextField textField) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()) {;
                    SpellCheckPopup spellCheckPopup = new SpellCheckPopup();
                    spellCheckPopup.show(textField, e.getX(), e.getY());
                }
            }
        };
    }

    public void updateCellValue(JTextField textField, int index) {
        if(updatingCell || deactivated) return;

        Cell cell = new Cell();
        cell.setRecord(currentRow);
        cell.setField(index);

        values[currentRow][index] = textField.getText();

        if(hasSuggestion(textField.getText(), index)) {
            textField.setBackground(Color.RED);
        } else {
            textField.setBackground(Color.WHITE);
        }

        updatingCell = true;
        imageState.setValue(cell, textField.getText());
        updatingCell = false;
    }

    public boolean hasSuggestion(String value, int column) {
        if(value.equals("")) return false;
        KnownData knownData = imageState.getKnownDataValues().get(column);

        String[] words = knownData.getWordArray();

        for(String val : words) {
            if(val.toLowerCase().equals(value.toLowerCase())) return false;
        }

        return true;
    }

    // TODO: Save the text
    // TODO: Make this not freak out when going onFocusWindow to focusOnField, they swap forever
    private FocusListener generateFocusListener(final JTextField textField, final int index) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                updateCurrentCell(textField, index);
            }

            @Override
            public void focusLost(FocusEvent e) {
                updateCellValue(textField, index);
            }
        };
    }

    private void updateView() {
        if(deactivated) return;

        updatingCell = true;
        for(int i = 0; i < this.getComponents().length; i++) {
            JPanel formSet = (JPanel)this.getComponent(i);
            JTextField form = (JTextField)formSet.getComponent(1);
            form.setText(values[currentRow][i]);

            if(hasSuggestion(form.getText(), i)) {
                form.setBackground(Color.RED);
            } else {
                form.setBackground(Color.WHITE);
            }
        }
        updatingCell = false;
    }

    public void setValue(String newValue, int row, int column) {
        if(updatingCell || deactivated) return;

        this.updateView();
        this.repaint();
    }

    public void setCurrentCell(int row, int column) {
        if(updatingCell || deactivated) return;

        this.currentRow = row;

        this.updateView();
        this.repaint();

        this.setFocus(column);
    }

    public void setCurrentCellForce() {
        if(deactivated) return;

        final Cell cell = imageState.getSelectedCell();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setFocus(cell.getField());
            }
        });
    }

    public void setFocus(int columnField) {
        if(deactivated) return;

        // offset is x - 1, cause start at 0.
        int column = columnField;

        if(values.length == 0) return;

        // get the column textField and request focus
        JPanel formList = (JPanel)this.getComponent(column);
        final JTextField form = (JTextField)formList.getComponent(1);

        form.requestFocus();
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

}
