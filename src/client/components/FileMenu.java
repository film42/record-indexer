package client.components;

import client.components.downloadModal.DownloadModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        eMenuItem1.addActionListener(actionListener);
        eMenuItem1.setToolTipText("Exit application");

        JMenuItem eMenuItem2 = new JMenuItem("Save");
        eMenuItem2.setEnabled(false);
        eMenuItem2.setToolTipText("Exit application");

        JMenuItem eMenuItem3 = new JMenuItem("Exit");
        eMenuItem2.setToolTipText("Exit application");

        file1.add(eMenuItem1);
        file1.add(eMenuItem2);
        file1.add(eMenuItem3);

        // Add to self
        this.add(file1);
        this.setBackground(Color.WHITE);
    }

    private ActionListener actionListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DownloadModal downloadModal = new DownloadModal();
            downloadModal.setVisible(true);
        }
    };
}
