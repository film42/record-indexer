package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.common.Fields;
import shared.models.Field;
import shared.models.Image;
import shared.models.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:35 PM
 */
@XStreamAlias("downloadBatch")
public class DownloadBatch_Res {

    private int batchId;
    private int projectId;
    private String imageUrl;
    private int firstYCoord;
    private int recordHeight;
    private int numberOfRecords;
    private int numberOfFields;
    private int recordsPerImage;

    private List<Fields> fields = new ArrayList<Fields>();


    public DownloadBatch_Res(Image image, Project project,
                             int fieldCount, int recordCount) {

        this.batchId = image.getId();
        this.imageUrl = image.getFile();

        this.projectId = project.getId();
        this.firstYCoord = project.getFirstYCoord();
        this.recordHeight = project.getRecordHeight();
        this.recordsPerImage = project.getRecordsPerImage();

        this.numberOfRecords = recordCount;
        this.numberOfFields = fieldCount;
    }

    public void addField(Field field) {

        Fields response = null;
        response = new Fields(field.getId(), field.getPosition(), field.getTitle(),
                              field.getHelpHtml(), field.getxCoord(),
                              field.getWidth(), field.getKnownData(),
                              field.getProjectId());
        fields.add(response);

    }

    public int getBatchId() {
        return batchId;
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

    public String toString(String serverPath) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getBatchId() + "\n");
        stringBuilder.append(getProjectId() + "\n");
        stringBuilder.append(serverPath + getImageUrl() + "\n");
        stringBuilder.append(getFirstYCoord() + "\n");
        stringBuilder.append(getRecordHeight() + "\n");
        stringBuilder.append(getNumberOfRecords() + "\n");
        stringBuilder.append(getNumberOfFields() + "\n");

        for(Fields field : fields) {
            stringBuilder.append(field.getId() + "\n");
            stringBuilder.append(field.getNumber() + "\n");
            stringBuilder.append(field.getTitle() + "\n");
            stringBuilder.append(serverPath + field.getHelpUrl() + "\n");
            stringBuilder.append(field.getxCoord() + "\n");
            stringBuilder.append(field.getPixelWidth() + "\n");

            if(field.getKnownData() != null && !field.getKnownData().isEmpty()) {
                stringBuilder.append(serverPath + field.getKnownData() + "\n");
            }
        }

        return stringBuilder.toString();
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }

    public static DownloadBatch_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("downloadBatch", DownloadBatch_Res.class);

        return (DownloadBatch_Res)xstream.fromXML(xml);
    }
}
