package client.components.menus;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 7:16 PM
 */
public class SpellCheckPopup extends JPopupMenu {

    private JMenuItem show;

    public SpellCheckPopup() {
        setupView();
    }

    private void setupView() {
        show = new JMenuItem("See Suggestions?");
        this.add(show);
    }

    public void addShowAction(ActionListener actionListener) {
        show.addActionListener(actionListener);
    }
}
