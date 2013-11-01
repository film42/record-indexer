package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.common.ARecord;
import shared.communication.responses.Fields_Res;
import shared.models.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:58 PM
 */
public class SubmitBatch_Param {

    private String username;
    private String password;
    private int imageId;

    private List<ARecord> recordValues = new ArrayList<ARecord>();

    public SubmitBatch_Param() {}

    public SubmitBatch_Param(String username, String password, int imageId) {
        this.username = username;
        this.password = password;
        this.imageId = imageId;
    }

    public List<ARecord> getRecordValues() {
        return this.recordValues;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getImageId() {
        return imageId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void addRecord(List<Value> values) {
        ARecord aRecord = new ARecord();
        aRecord.setValues(values);
        recordValues.add(aRecord);
    }

    public static SubmitBatch_Param serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("submit", SubmitBatch_Param.class);
        xstream.alias("submitBatch", SubmitBatch_Param.class);

        xstream.alias("value", Value.class);


        return  (SubmitBatch_Param)xstream.fromXML(xml);
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("submitBatch", SubmitBatch_Param.class);

        xstream.alias("value", Value.class);

        return xstream.toXML(this);
    }
}

