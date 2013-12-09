package client.components.formEntry;

import client.persistence.Cell;
import client.persistence.ImageStateListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 10:59 AM
 */
public class FormEntry extends JPanel {

    private ArrayList<String> listValues;

    private JList rowNumberList;
    private FormTable formTable;
    private JSplitPane splitPane;

    public FormEntry() {

        this.listValues = new ArrayList<>();

        FACTORY();

        setupView();
    }

    private void setupView() {
        //this.setLayout(new FlowLayout());
        //this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setLayout(new GridLayout(1,1));
        splitPane = new JSplitPane();
        splitPane.setDividerLocation(50);
        splitPane.setBorder(null);


        rowNumberList = new JList(listValues.toArray());
        rowNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowNumberList.setLayoutOrientation(JList.VERTICAL);
        rowNumberList.setVisibleRowCount(-1);

        rowNumberList.addListSelectionListener(listSelectionListener);

        splitPane.setLeftComponent(rowNumberList);

        formTable = new FormTable(columnNames, data);

        splitPane.setRightComponent(new JScrollPane(formTable));

        this.add(splitPane);

    }

    @Override
    public Dimension getMinimumSize() {
        // TODO Auto-generated method stub
        Dimension dim = super.getMinimumSize();
        dim.width = 350;
        return dim;
    }

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            int row = cell.getRecord();
            int column = cell.getField();

            rowNumberList.setSelectedIndex(row);
            formTable.setValue(newValue, row, column);

            splitPane.repaint();
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            int row = newSelectedCell.getRecord();
            int column = newSelectedCell.getField();

            rowNumberList.setSelectedIndex(row);
            formTable.setCurrentCell(row, column);

            splitPane.repaint();
        }
    };

    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int newRow = rowNumberList.getSelectedIndex();

            Cell cell = new Cell();
            cell.setRecord(newRow);
            cell.setField(2);

            imageStateListener.selectedCellChanged(cell);
        }
    };







    private void FACTORY() {
        listValues.add("1");
        listValues.add("2");
        listValues.add("3");
        listValues.add("4");
        listValues.add("5");
        listValues.add("6");
        listValues.add("7");
        listValues.add("8");

    }

    //
    // FACTORY
    //

    private String[] columnNames = {
            "First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"
    };

    private String[][] data = {
            {"Kathy", "Smith", "Snowboarding", "5", "false"},
            {"John", "Doe", "Rowing", "3", "false"},
            {"Sue", "Black", "Knitting", "2", "false"},
            {"Jane", "White", "Speed reading", "20", "true"},
            {"Joe", "Brown", "Pool", "10", "false"},
            {"Sue", "Black", "Knitting", "2", "false"},
            {"Jane", "White", "Speed reading", "20", "true"},
            {"Joe", "Brown", "Pool", "10", "false"}
    };

}
