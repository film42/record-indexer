package shared.communication.responses;

import server.db.ProjectAccessor;
import server.db.UserAccessor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:27 PM
 */
public class Projects_Res {

    private class Project {

        private int id;
        private String title;

        public Project(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    private List<Project> projectsList;

    public void addProject(int id, String title) {
        projectsList.add(new Project(id, title));
    }

    public List<Project> getProjectsList() {
        return projectsList;
    }
}
