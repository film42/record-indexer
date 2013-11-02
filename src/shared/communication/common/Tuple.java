package shared.communication.common;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:32 PM
 */
public class Tuple {

    private int batchId;
    private String imageUrl;
    private int recordNumber;
    private int fieldId;

    /**
     * A search result Tuple (name pending)
     *
     * @param batchId
     * @param imageUrl
     * @param recordNumber
     * @param fieldId
     */
    public Tuple(int batchId, String imageUrl,
                 int recordNumber, int fieldId) {

        this.batchId = batchId;
        this.imageUrl = imageUrl;
        this.recordNumber = recordNumber;
        this.fieldId = fieldId;

    }

    public Tuple(){

    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

}
