package client.components.tableEntry;

import client.components.menus.SpellCheckPopup;
import client.components.spellCheck.SpellingModal;
import client.modules.spellChecker.KnownData;
import client.modules.spellChecker.WordSelectedListener;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

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
    private SpellCheckPopup spellCheckPopup;
    private int column;

    public EntryCellEditor(ImageState imageState) {
        this.imageState = imageState;

        this.spellCheckPopup = new SpellCheckPopup();
        this.spellCheckPopup.addShowAction(showSuggestionsListener);
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
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {

        textField = new JTextField();
        currentValue = (String)value;

        textField.setText(currentValue);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

        textField.addMouseListener(generateMouseListener(row, column));

        this.column = column;

        if(isSelected) {
            Cell cell = new Cell();
            cell.setRecord(row);
            cell.setField(column);
        }

        if(hasSuggestion((String)value, column - 1)) {
            textField.setBackground(Color.RED);
            textField.addMouseListener(rightClickPopupAction);
        }

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
                cell.setField(column - 1);
                imageState.setSelectedCell(cell);
            }
        };
    }

    private MouseListener rightClickPopupAction = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.isPopupTrigger()) {;
                spellCheckPopup.show(textField, e.getX(), e.getY());
            }
        }

    };

    private WordSelectedListener wordSelectedListener = new WordSelectedListener() {
        @Override
        public void wordSelected(String word) {
            textField.setText(word);
        }
    };

    private ActionListener showSuggestionsListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SpellingModal spellingModal = new SpellingModal(imageState, textField.getText(),
                                                            column - 1, wordSelectedListener);
            spellingModal.setVisible(true);
        }
    };
}