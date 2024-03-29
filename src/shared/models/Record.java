package shared.models;

import shared.common.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:16 PM
 */
public class Record extends BaseModel {

    private int id;
    private int position;
    private int imageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
