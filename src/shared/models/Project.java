package shared.models;

import shared.common.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:15 PM
 */
public class Project extends BaseModel {

    private int id;
    private String title;
    private int recordsPerImage;
    private int firstYCoord;
    private int recordHeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRecordsPerImage() {
        return recordsPerImage;
    }

    public void setRecordsPerImage(int recordsPerImage) {
        this.recordsPerImage = recordsPerImage;
    }

    public int getFirstYCoord() {
        return firstYCoord;
    }

    public void setFirstYCoord(int firstYCoord) {
        this.firstYCoord = firstYCoord;
    }

    public int getRecordHeight() {
        return recordHeight;
    }

    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }
}
