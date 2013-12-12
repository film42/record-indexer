package client.components.imagePanel;

import client.components.imagePanel.listeners.ImageControlsListener;
import client.components.listeners.DrawingListener;
import client.persistence.*;

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

    private ImageTable imageTable;

    private double MAX_SCROLL = 1.0f;
    private double MIN_SCROLL = 0.2f;

    private ImageState imageState;


    public ScalableImage(ImageState imageState) {
        this.imageState = imageState;

        this.setBackground(Color.DARK_GRAY);

        initDrag();

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);

        setupView();

        this.imageState.addListener(imageStateListener);
        this.imageState.addNewProjectListener(newProjectListener);

    }

    private void setupView() {
        image = imageState.getImage();

        imageTable = new ImageTable(imageState);

        this.setOrigin(imageState.getSettings().getImageOriginX(),
                       imageState.getSettings().getImageOriginY());

        this.setScale(imageState.getSettings().getImageScaleLevel());

        this.repaint();
    }

    public void refreshImage() {
        imageTable.setDeactivated(true);
        setupView();
    }


    // Right now this will redraw a second time to ensure matrix calc is happy.
    boolean redrawHack = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2.scale(scale, scale);
        g2.translate(-w_originX, -w_originY);
        g2.drawImage(image, 0, 0, null);

        imageTable.paint(g2);

        if(!redrawHack) {
            redrawHack = true;
            // To rematch 0,0 to real origin
            //this.setOrigin(this.getWidth()/2, this.getHeight()/2);
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

        // Toggle whether it's set to invert or not
        boolean current = imageState.getSettings().isImageInverted();
        imageState.getSettings().setImageInverted(!current);

        redrawHack = false;
        this.repaint();
    }

    public void setValue(Cell cell, String value) {
        imageTable.setCurrentCell(cell.getField(), cell.getRecord());
        this.repaint();
    }

    public void setCurrentCell(Cell cell) {
        imageTable.setCurrentCell(cell.getField(), cell.getRecord());
        this.repaint();
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

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            // Do we actually have a real image?
            if(!imageState.isHasImage()) return;

            int d_X = e.getX();
            int d_Y = e.getY();

            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth()/2, getHeight()/2);
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

            imageTable.contains(w_X, w_Y);

            dragging = true;
            w_dragStartX = w_X;
            w_dragStartY = w_Y;
            w_dragStartOriginX = w_originX;
            w_dragStartOriginY = w_originY;

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging) {
                int d_X = e.getX();
                int d_Y = e.getY();

                AffineTransform transform = new AffineTransform();
                transform.translate(getWidth()/2, getHeight()/2);
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

                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            initDrag();

            imageState.getSettings().setImageOriginX(w_originX);
            imageState.getSettings().setImageOriginY(w_originY);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int rotation = e.getWheelRotation();
            if((rotation > 0)  && scale > MIN_SCROLL ) {
                setScale(scale - 0.02f);
            } else if (scale < MAX_SCROLL) {
                setScale(scale + 0.02f);
            }

            imageState.getSettings().setImageScaleLevel(scale);
        }
    };

    /* ********************************************
                    Listeners
     ******************************************** */

    private ImageStateListener imageStateListener = new ImageStateListener() {
        @Override
        public void valueChanged(Cell cell, String newValue) {
            return;
        }

        @Override
        public void selectedCellChanged(Cell newSelectedCell) {
            imageTable.setCurrentCell(newSelectedCell.getField(), newSelectedCell.getRecord());

            repaint();
        }
    };

    private NewProjectListener newProjectListener = new NewProjectListener() {
        @Override
        public void hasNewProject() {
            refreshImage();
        }
    };

    private ImageControlsListener imageControlsListener = new ImageControlsListener() {
        @Override
        public void onScrollIncrease() {
            if (scale < MAX_SCROLL) {
                setScale(scale + 0.02f);
            }
        }

        @Override
        public void onScrollDecrease() {
            if(scale > MIN_SCROLL ) {
                setScale(scale - 0.02f);
            }
        }

        @Override
        public void onInvertImage() {
            invertImage(image);
        }

        @Override
        public void onToggleHighlights() {
            // Alternate, you know, toggle.
            if(imageTable.isHighlightsEnabled()) {
                imageTable.enableHighlights(false);
            } else {
                imageTable.enableHighlights(true);
            }
            repaint();
        }
    };

    public ImageControlsListener getImageControlsListener() {
        return imageControlsListener;
    }

}
