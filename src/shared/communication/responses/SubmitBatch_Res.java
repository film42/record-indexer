package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:21 PM
 */
@XStreamAlias("submitImage")
public class SubmitBatch_Res {

    private boolean success;

    public SubmitBatch_Res(boolean success) {
        this.success = success;
    }

    public boolean wasSuccess() {
        return success;
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }

    public static SubmitBatch_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("submitBatch", SubmitBatch_Res.class);
        xstream.alias("submitImage", SubmitBatch_Res.class);
        xstream.alias("submit", SubmitBatch_Res.class);

        return  (SubmitBatch_Res)xstream.fromXML(xml);
    }
}
