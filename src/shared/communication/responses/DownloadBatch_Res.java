package shared.communication.responses;

import shared.communication.common.Fields;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:35 PM
 */
public class DownloadBatch_Res {

    private int id;
    private int projectId;
    private String imageUrl;
    private int firstYCoord;
    private int recordHeight;
    private int numberOfRecords;
    private int numberOfFields;
    private int recordsPerImage;
    private String knownValuesUrl;
    private List<Fields> fields;


    public DownloadBatch_Res(int id,
                             int projectId,
                             String imageUrl,
                             int firstYCoord,
                             int recordHeight,
                             int numberOfRecords,
                             int numberOfFields,
                             int recordsPerImage,
                             String knownValuesUrl) {

        this.id = id;
        this.projectId = projectId;
        this.imageUrl = imageUrl;
        this.firstYCoord = firstYCoord;
        this.recordHeight = recordHeight;
        this.numberOfRecords = numberOfRecords;
        this.numberOfFields = numberOfFields;
        this.recordsPerImage = recordsPerImage;
        this.knownValuesUrl = knownValuesUrl;

    }

    public void addField(int id, int number,
                         String title, String helpUrl,
                         int xCoord, int pixelWidth) {

        Fields response = null;
        response = new Fields(id, number, title, helpUrl, xCoord, pixelWidth);
        fields.add(response);

    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getFirstYCoord() {
        return firstYCoord;
    }

    public int getRecordHeight() {
        return recordHeight;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public int getRecordsPerImage() {
        return recordsPerImage;
    }

    /**
     *
     * @return A List of common.Field for params
     */
    public List<Fields> getFields() {
        return fields;
    }
}
