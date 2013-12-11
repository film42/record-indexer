package client.persistence;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/11/13
 * Time: 12:36 AM
 */
public class Settings {

    private int baseSplitY;
    private int baseSplitX;
    private int windowHeight;
    private int windowWidth;
    private int windowPositionY;
    private int windowPositionX;
    private double imageScaleLevel;
    private int imageOriginX;
    private int imageOriginY;
    private boolean imageInverted;
    private boolean imageHighlights;
    private BufferedImage image;
    private String[] columns;
    private String[][] values;

    public Settings() {
        FACTORY();
    }

    private void FACTORY() {

        this.windowHeight = 650;
        this.windowWidth = 1000;
        this.windowPositionX = 36;
        this.windowPositionY = 73;
        this.baseSplitY = 400;
        this.baseSplitX = 500;
        this.imageScaleLevel = 0.6f;
        this.imageOriginX = 820;
        this.imageOriginY = 256;
        this.imageHighlights = true;
        this.imageInverted = false;

        columns = new String[] {
                "Last Name", "First Name", "Gender", "Age"
        };

        values = new String[][] {
                {"Mooney1", "Dick", "Male", "3"},
                {"Mooney2", "Dickest", "Male", "3"},
                {"Moone3", "Szz", "Malde", "3"},
                {"Moone4", "", "Maldfe", "3"},
                {"Mooney5", "", "Malse", "3"},
                {"Mooney6", "", "Male", "3"},
                {"Mooney7", "Dicker", "Male", "3"},
                {"Mooney8", "", "Male", "3"}
        };
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[][] getValues() {
        return values;
    }

    public void setValues(String[][] values) {
        this.values = values;
    }

    public int getBaseSplitY() {
        return baseSplitY;
    }

    public void setBaseSplitY(int baseSplitY) {
        this.baseSplitY = baseSplitY;
    }

    public int getBaseSplitX() {
        return baseSplitX;
    }

    public void setBaseSplitX(int baseSplitX) {
        this.baseSplitX = baseSplitX;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowPositionY() {
        return windowPositionY;
    }

    public void setWindowPositionY(int windowPositionY) {
        this.windowPositionY = windowPositionY;
    }

    public int getWindowPositionX() {
        return windowPositionX;
    }

    public void setWindowPositionX(int windowPositionX) {
        this.windowPositionX = windowPositionX;
    }

    public double getImageScaleLevel() {
        return imageScaleLevel;
    }

    public void setImageScaleLevel(double imageScaleLevel) {
        this.imageScaleLevel = imageScaleLevel;
    }

    public boolean isImageInverted() {
        return imageInverted;
    }

    public void setImageInverted(boolean imageInverted) {
        this.imageInverted = imageInverted;
    }

    public boolean isImageHighlights() {
        return imageHighlights;
    }

    public void setImageHighlights(boolean imageHighlights) {
        this.imageHighlights = imageHighlights;
    }

    public int getImageOriginX() {
        return imageOriginX;
    }

    public void setImageOriginX(int imageOriginX) {
        this.imageOriginX = imageOriginX;
    }

    public int getImageOriginY() {
        return imageOriginY;
    }

    public void setImageOriginY(int imageOriginY) {
        this.imageOriginY = imageOriginY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
