package client.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 2:33 PM
 */
public class ImageControl extends JPanel {

    public ImageControl() {
        setupView();
    }

    private void setupView() {
        this.add(new JButton("Zoom In"), BorderLayout.WEST);
        this.add(new JButton("Zoom Out"), BorderLayout.WEST);
        this.add(new JButton("Invert Image"), BorderLayout.WEST);
        this.add(new JButton("Toggle Highlights"), BorderLayout.WEST);
        this.add(new JButton("Save"), BorderLayout.WEST);
        this.add(new JButton("Submit"), BorderLayout.WEST);

        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        //this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
    }

    @Override
    public Dimension getMaximumSize() {
        // TODO Auto-generated method stub
        Dimension dim = super.getMaximumSize();
        dim.height = 60;
        return dim;
    }
}
