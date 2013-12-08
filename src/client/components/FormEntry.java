package client.components;

import javax.swing.*;
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

    public FormEntry() {

        this.listValues = new ArrayList<>();
        FACTORY();

        setupView();
    }

    private void setupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JList list = new JList(listValues.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        this.add(list);
    }

    private void FACTORY() {
        listValues.add("1");
        listValues.add("2");
        listValues.add("3");
        listValues.add("4");
        listValues.add("5");
        listValues.add("6");
    }
}
