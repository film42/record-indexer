package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import shared.communication.common.Fields;
import shared.models.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:08 PM
 */
@XStreamAlias("fields")
public class Fields_Res {

    @XStreamImplicit()
    private List<Fields> fields = new ArrayList<Fields>();

    private int projectId;

    public void addField(Field field, int position) {

        Fields response = null;
        response = new Fields(field.getId(), position, field.getTitle(),
                              field.getHelpHtml(), field.getxCoord(),
                              field.getWidth(), field.getKnownData());
        fields.add(response);

    }

    /**
     *
     * @return A List of common.Field for params
     */
    public List<Fields> getFields() {
        return fields;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Fields field : fields) {
            stringBuilder.append(getProjectId() + "\n");
            stringBuilder.append(field.getId() + "\n");
            stringBuilder.append(field.getTitle() + "\n");
        }

        return stringBuilder.toString();
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);

        return xstream.toXML(this);
    }

    public static Fields_Res serialize(String xml) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);
        xstream.alias("fields", Fields_Res.class);

        return  (Fields_Res)xstream.fromXML(xml);
    }
}
