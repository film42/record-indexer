package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:06 PM
 */
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
}
