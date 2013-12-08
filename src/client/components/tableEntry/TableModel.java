package client.components.tableEntry;

import javax.swing.table.AbstractTableModel;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 6:15 PM
 */
public class TableModel extends AbstractTableModel {

    private String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

    private Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", "5", "false"},
            {"John", "Doe",
                    "Rowing", "3", "false"},
            {"Sue", "Black",
                    "Knitting", "2", "false"},
            {"Jane", "White",
                    "Speed reading", "20", "true"},
            {"Joe", "Brown",
                    "Pool", "10", "false"}
    };

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        data[row][column] = value;
    }
}
