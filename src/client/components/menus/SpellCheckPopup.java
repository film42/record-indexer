package client.components.menus;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 7:16 PM
 */
public class SpellCheckPopup extends JPopupMenu {

    public SpellCheckPopup() {
        setupView();
    }

    private void setupView() {
        JMenuItem eMenuItem1 = new JMenuItem("See Suggestions?");

        this.add(eMenuItem1);
    }
}
