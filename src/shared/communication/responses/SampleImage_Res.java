package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.models.Image;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:34 PM
 */
@XStreamAlias("sampleImage")
public class SampleImage_Res {

    public SampleImage_Res(Image image) {
        this.url = image.getFile();
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }

}
