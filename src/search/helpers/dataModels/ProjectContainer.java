package search.helpers.dataModels;

import shared.communication.common.Fields;
import shared.communication.common.Project_Res;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/9/13
 * Time: 7:05 PM
 */
public class ProjectContainer extends Project_Res{

    private List<Fields> fieldsList = new ArrayList<Fields>();

    public ProjectContainer(Project_Res projectRes) {
        super(projectRes.getId(), projectRes.getTitle());
    }

    public void addField(Fields field) {
        fieldsList.add(field);
    }

    public List<Fields> getFieldsList() {
        return this.fieldsList;
    }

}
