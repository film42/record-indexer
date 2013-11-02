package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.models.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:09 PM
 */
public class Search_Param {

    private String username;

    private String password;
    private List<Integer> fieldsIds = new ArrayList<Integer>();
    private List<String> searchParams = new ArrayList<String>();
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addSearchParam(String param) {
        searchParams.add(param);
    }

    public void addFieldId(int fieldId) {
        fieldsIds.add(fieldId);
    }

    public List<Integer> getFieldsIds() {
        return fieldsIds;
    }

    public List<String> getSearchParams() {
        return searchParams;
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("search", Search_Param.class);

        return xstream.toXML(this);
    }

    public static Search_Param serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("search", Search_Param.class);

        return  (Search_Param)xstream.fromXML(xml);
    }
}
