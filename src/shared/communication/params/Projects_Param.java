package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:07 PM
 */
@XStreamAlias("project")
public class Projects_Param {

    private String username;
    private String password;

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

    public static Projects_Param serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("project", Projects_Param.class);

        // TODO: This is returning exception or something if extra fields exist.

        return (Projects_Param)xstream.fromXML(xml);
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }
}
