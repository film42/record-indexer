package shared.models;

import shared.common.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:25 PM
 */
public class Value extends BaseModel {

    private int id;
    private String value;
    private String type;
    private int recordId;

    public Value() {
    }

    public Value(int id, String value, String type, int recordId) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.recordId = recordId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
