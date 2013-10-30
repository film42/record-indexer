package shared.communication.responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import shared.communication.common.Project_Res;
import shared.models.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:27 PM
 */
@XStreamAlias("projects")
public class Projects_Res {

    public Projects_Res() {

    }

    @XStreamImplicit
    private List<Project_Res> projectsList = new ArrayList<Project_Res>();

    public void addProject(int id, String title) {
        projectsList.add(new Project_Res(id, title));
    }

    public List<Project_Res> getProjectsList() {
        return projectsList;
    }

    public String toXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.autodetectAnnotations(true);

        return xstream.toXML(this);
    }
}
