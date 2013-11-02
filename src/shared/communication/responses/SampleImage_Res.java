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

    @Override
    public String toString() {
        return getUrl() + "\n";
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        return xstream.toXML(this);
    }

    public static SampleImage_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("sampleImage", SampleImage_Res.class);

        return (SampleImage_Res)xstream.fromXML(xml);
    }
}
