package client.components.imagePanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/7/13
 * Time: 10:13 PM
 */
public class ImageCell {

    private double x;
    private double y;
    private double width;
    private double height;
    private boolean isSelected;
    Rectangle2D.Double rectangle2D;

    public ImageCell(Rectangle2D.Double rectangle2D) {
        this.rectangle2D = rectangle2D;

        this.x = rectangle2D.getBounds2D().getX();
        this.y = rectangle2D.getBounds2D().getY();
        this.width = this.x = rectangle2D.getBounds2D().getWidth();
        this.height = this.x = rectangle2D.getBounds2D().getHeight();

        this.isSelected = false;
    }

    public void paint(Graphics2D g2) {
        if(isSelected) {
            g2.setColor(new Color(0,119,204, 150));
        } else {
            g2.setColor(new Color(0,0,0, 0));
        }
        g2.fill(rectangle2D);
    }

    public boolean contains(double x, double y) {
        return rectangle2D.contains(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
