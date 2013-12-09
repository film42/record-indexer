package client.components.formEntry;

import client.components.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/8/13
 * Time: 1:57 PM
 */
public class FormTable extends JPanel {

    private String[] fieldNames;
    private String[][] values;

    private int currentRow;

    public FormTable(String[] fieldNames, String[][] values) {
        this.fieldNames = fieldNames;
        this.values = values;

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
            textField.setPreferredSize(new Dimension(150, 30));
            formContainer.add(textField, BorderLayout.CENTER);

            this.add(formContainer);
        }

    }

    private void updateView() {
        for(int i = 0; i < this.getComponents().length; i++) {
            JPanel formSet = (JPanel)this.getComponent(i);
            JTextField form = (JTextField)formSet.getComponent(1);
            form.setText(values[currentRow][i]);
        }
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

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                form.requestFocus();
            }
        });
    }
}
