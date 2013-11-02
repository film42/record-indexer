package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.common.Tuple;
import shared.communication.params.Search_Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:27 PM
 */
public class Search_Res {

    private List<Tuple> tuples;

    public List<Tuple> getSearchResults() {
        return tuples;
    }


    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("search", Search_Res.class);

        return xstream.toXML(this);
    }

    public static Search_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("search", Search_Res.class);

        return  (Search_Res)xstream.fromXML(xml);
    }
}
