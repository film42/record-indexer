package client.components;

import client.components.listeners.DrawingListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 11:56 PM
 */
public class ScalableImage extends JPanel {

    private BufferedImage image;

    private int w_originX;
    private int w_originY;
    private double scale;

    private boolean dragging;
    private int w_dragStartX;
    private int w_dragStartY;
    private int w_dragStartOriginX;
    private int w_dragStartOriginY;

    private ArrayList<DrawingListener> listeners;

    private String path;

    public ScalableImage(String path) {
        this.path = path;
        this.setBackground(Color.DARK_GRAY);

        listeners = new ArrayList<DrawingListener>();

        initDrag();

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);

        setupView();

        this.setScale(0.9f);
    }

    private void setupView() {
        try {
            image = ImageIO.read(new File(path));
            invertImage(image);
        } catch (IOException ex) {
            // handle exception...
        }
    }

    // Right now this will redraw a second time to ensure matrix calc is happy.
    boolean redrawHack = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.translate(this.getWidth()/2, this.getHeight()/2);
        g2.scale(scale, scale);
        g2.translate(-w_originX, -w_originY);
        g2.drawImage(image, -this.getWidth()/2, -this.getHeight()/2, null);

        if(redrawHack == false) {
            redrawHack = true;
            this.repaint();
        }
    }

    public void invertImage(BufferedImage b) {
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int rgba = b.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                b.setRGB(x, y, col.getRGB());
            }
        }
    }

    /* ********************************************
                       Free Code
     ******************************************** */

    private void initDrag() {
        dragging = false;
        w_dragStartX = 0;
        w_dragStartY = 0;
        w_dragStartOriginX = 0;
        w_dragStartOriginY = 0;
    }

    public void setScale(double newScale) {
        scale = newScale;
        this.repaint();
    }

    public void setOrigin(int w_newOriginX, int w_newOriginY) {
        w_originX = w_newOriginX;
        w_originY = w_newOriginY;
        this.repaint();
    }

    public void addDrawingListener(DrawingListener listener) {
        listeners.add(listener);
    }

    private void notifyOriginChanged(int w_newOriginX, int w_newOriginY) {
        for (DrawingListener listener : listeners) {
            listener.originChanged(w_newOriginX, w_newOriginY);
        }
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();

            AffineTransform transform = new AffineTransform();
            transform.scale(scale, scale);
            transform.translate(-w_originX, -w_originY);

            Point2D d_Pt = new Point2D.Double(d_X, d_Y);
            Point2D w_Pt = new Point2D.Double();
            try {
                transform.inverseTransform(d_Pt, w_Pt);
            } catch (NoninvertibleTransformException ex) {
                return;
            }
            int w_X = (int)w_Pt.getX();
            int w_Y = (int)w_Pt.getY();

            dragging = true;
            w_dragStartX = w_X;
            w_dragStartY = w_Y;
            w_dragStartOriginX = w_originX;
            w_dragStartOriginY = w_originY;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging) {
                int d_X = e.getX();
                int d_Y = e.getY();

                AffineTransform transform = new AffineTransform();
                transform.scale(scale, scale);
                transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);

                Point2D d_Pt = new Point2D.Double(d_X, d_Y);
                Point2D w_Pt = new Point2D.Double();
                try {
                    transform.inverseTransform(d_Pt, w_Pt);
                } catch (NoninvertibleTransformException ex) {
                    return;
                }
                int w_X = (int)w_Pt.getX();
                int w_Y = (int)w_Pt.getY();

                int w_deltaX = w_X - w_dragStartX;
                int w_deltaY = w_Y - w_dragStartY;

                w_originX = w_dragStartOriginX - w_deltaX;
                w_originY = w_dragStartOriginY - w_deltaY;

                notifyOriginChanged(w_originX, w_originY);

                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            initDrag();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.getWheelRotation() > 0) {
                setScale(scale - 0.02f);
            } else {
                setScale(scale + 0.02f);
            }
        }
    };
}
