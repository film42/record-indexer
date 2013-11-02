package shared.communication.params;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.models.Value;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:09 PM
 */
public class Search_Param {

    private String username;

    private String password;
    private Set<Integer> fieldsIds = new TreeSet<Integer>();
    private Set<String> searchParams = new TreeSet<String>();
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

    public Set<Integer> getFieldsIds() {
        return fieldsIds;
    }

    public Set<String> getSearchParams() {
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
