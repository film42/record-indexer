package search;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/8/13
 * Time: 6:28 PM
 */
public class MainLayout extends JFrame {

    public MainLayout() {
        initView();
    }

    FlowLayout flowLayout = new FlowLayout();

    public void initView() {


        final JPanel component = new JPanel();
        component.setLayout(flowLayout);
    }

    public void open() {
        setVisible(true);
    }
}
