package server.db;

import server.db.common.DatabaseAccessor;

import shared.models.User;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:43 PM
 */
public class UserAccessor extends User implements DatabaseAccessor {

    /**
     * Create an accessor wrapper around a model
     *
     * @param user
     */
    public UserAccessor(User user) {
        super();
    }

    /**
     * Find a User by their username
     *
     * @param username - A User's username
     * @return UserAccessor or null
     */
    public static UserAccessor find(String username) {
        User user = null;
        return new UserAccessor(user);
    }

    /**
     * Get a project for a user with project_id from User model
     *
     * @return new ProjectAccessor or null
     */
    public ProjectAccessor getProject() {
        return ProjectAccessor.find(super.getProjectId());
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
