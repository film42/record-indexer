package shared.communication.responses;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.params.DownloadBatch_Param;
import shared.communication.params.ValidateUser_Param;
import shared.models.User;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:10 PM
 */
public class ValidateUser_Res {

    private boolean authenticated;
    private String firstName;
    private String lastName;
    private int indexedRecords;

    public ValidateUser_Res(boolean authenticated, User user) {
        this.authenticated = authenticated;

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.indexedRecords = user.getIndexedRecords();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getIndexedRecords() {
        return indexedRecords;
    }

    @Override
    public String toString() {
        String authenticated = "";
        if(isAuthenticated()) authenticated = "TRUE";
        else authenticated = "FALSE";
        return String.format("%s\n%s\n%s\n%d", authenticated, getFirstName(),
                                               getLastName(), getIndexedRecords());
    }

    public static ValidateUser_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("user", ValidateUser_Res.class);

        return (ValidateUser_Res)xstream.fromXML(xml);
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("user", ValidateUser_Res.class);

        return xstream.toXML(this);
    }
}
