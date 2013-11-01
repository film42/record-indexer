package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.responses.ValidateUser_Res;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:06 PM
 */
@XStreamAlias("user")
public class ValidateUser_Param {

    private String username;
    private String password;

    /**
     * Serializer from xml
     *
     * @param xml
     * @return
     */
    public static ValidateUser_Param serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("user", ValidateUser_Param.class);

        return (ValidateUser_Param)xstream.fromXML(xml);
    }

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

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("user", ValidateUser_Res.class);

        return xstream.toXML(this);
    }
}
