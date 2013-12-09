package client.persistence;

import client.persistence.Cell;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/8/13
 * Time: 10:12 PM
 */
public interface SyncContext {

    public void onChangeCurrentCell(Cell cell);

    public void onChnageCellValue(Cell cell, String value);

}
