package client.persistence;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/3/13
 * Time: 9:17 AM
 */
public interface ImageStateListener {

    public void valueChanged(Cell cell, String newValue);

    public void selectedCellChanged(Cell newSelectedCell);

}
