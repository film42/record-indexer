package client.components;

import sun.misc.JavaLangAccess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
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
    private ArrayList<String> fieldNames;

    public FormEntry() {

        this.listValues = new ArrayList<>();
        this.fieldNames = new ArrayList<>();
        FACTORY();

        setupView();
    }

    private void setupView() {
        //this.setLayout(new FlowLayout());
        //this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setLayout(new GridLayout(1,1));
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(50);
        splitPane.setBorder(null);


        JList list = new JList(listValues.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        //list.setMinimumSize(new Dimension(50,100));
        //list.setMaximumSize(new Dimension(50,100));
        list.setVisibleRowCount(-1);

        splitPane.setLeftComponent(list);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // TODO: This form should be its own class so we can say, .setFocus(3) and .cellDidChange.
        for(String s : fieldNames) {
            JPanel sub = new JPanel();
            JLabel label = new JLabel(s);
            label.setPreferredSize(new Dimension(100,30));
            sub.add(label, BorderLayout.WEST);
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(150,30));
            sub.add(textField, BorderLayout.CENTER);
            form.add(sub);
        }

        splitPane.setRightComponent(new JScrollPane(form));

        this.add(splitPane);

    }

    @Override
    public Dimension getMinimumSize() {
        // TODO Auto-generated method stub
        Dimension dim = super.getMinimumSize();
        dim.width = 350;
        return dim;
    }


    private void FACTORY() {
        listValues.add("1");
        listValues.add("2");
        listValues.add("3");
        listValues.add("4");
        listValues.add("5");
        listValues.add("6");

        fieldNames.add("Last Name");
        fieldNames.add("First Name");
        fieldNames.add("Gender");
        fieldNames.add("Age");

    }
}
