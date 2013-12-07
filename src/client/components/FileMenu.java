package client.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:18 AM
 */
public class FileMenu extends JMenuBar {

    public FileMenu() {
        setupView();
    }

    private void setupView() {
        // Prevents menu items from filling the whole length
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JMenu file1 = new JMenu("File");

        JMenuItem eMenuItem1 = new JMenuItem("Download Batch");
        eMenuItem1.setToolTipText("Exit application");

        JMenuItem eMenuItem2 = new JMenuItem("Save");
        eMenuItem1.setToolTipText("Exit application");

        JMenuItem eMenuItem3 = new JMenuItem("Exit");
        eMenuItem1.setToolTipText("Exit application");

        file1.add(eMenuItem1);
        file1.add(eMenuItem2);
        file1.add(eMenuItem3);

        // Add to self
        this.add(file1);
        this.setBackground(Color.WHITE);
    }
}
