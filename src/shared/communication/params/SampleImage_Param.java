package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:07 PM
 */
public class SampleImage_Param {

    private String username;
    private String password;
    private int projectId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public static SampleImage_Param serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("user", SampleImage_Param.class);
        xstream.alias("sampleImage", SampleImage_Param.class);
        xstream.alias("sampleimage", SampleImage_Param.class);
        xstream.alias("sample", SampleImage_Param.class);

        return (SampleImage_Param)xstream.fromXML(xml);
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("sampleImage", SampleImage_Param.class);
        return xstream.toXML(this);
    }
}
