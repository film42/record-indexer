package search;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/7/13
 * Time: 2:11 PM
 */
public class Search {


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //MainLayout mainLayout = new MainLayout();
                //mainLayout.open();
                Main main = new Main();
                main.setVisible(true);
            }
        });

    }
}
