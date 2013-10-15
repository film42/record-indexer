package server.db;

import server.db.common.DatabaseAccessor;
import shared.models.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:43 PM
 */
public class ProjectAccessor extends Project implements DatabaseAccessor {

    /**
     * Wrap ProjectAccessor with a model Projects_Res
     *
     * @param project using a Projects_Res model
     */
    public ProjectAccessor(Project project) {
        super();
    }

    public static List<ProjectAccessor> all() {
        return new ArrayList<ProjectAccessor>();
    }

    /**
     * Find a Projects_Res with a project_id
     *
     * @param id a Projects_Res's id
     * @return ProjectAccessor(Projects_Res) or null
     */
    public static ProjectAccessor find(int id) {
        Project project = null;
        return new ProjectAccessor(project);
    }

    /**
     * Get the user associated with a project
     *
     * @return UserAccessor(User) or null
     */
    public UserAccessor getUser() {
        return null;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
