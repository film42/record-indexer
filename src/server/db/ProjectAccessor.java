package server.db;

import server.db.common.Database;
import server.db.common.DatabaseAccessor;
import server.db.common.SQL;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.models.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Essential database backbone class
     */
    private Database database = new Database();

    private UserAccessor userAccessor = null;

    /**
     * Wrap ProjectAccessor with a model Projects_Res
     *
     * @param project using a Projects_Res model
     */
    public ProjectAccessor(Project project) {
        super();
    }

    public ProjectAccessor() {}

    /**
     * Get all projects in DB, super generic, this.
     *
     * @return List of ProjectAccessors
     */
    public static List<ProjectAccessor> all() {
        final Database database = new Database();

        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> accessorList = new ArrayList<Object>();

                String query = "select * from 'projects';";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    accessorList.add(buildFromResultSet(resultSet));
                }
                return accessorList;
            }
        }, database);

        List<ProjectAccessor> projectAccessorList = new ArrayList<ProjectAccessor>();
        for(Object object : response) {
            projectAccessorList.add((ProjectAccessor)object);
        }

        return projectAccessorList;
    }

    /**
     * Get the fields of a project
     *
     * @return List of FieldAccessors
     */
    public List<FieldAccessor> getFields() {
        final Database database = new Database();

        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> accessorList = new ArrayList<Object>();

                String query = "select * from 'fields' where project_id = "+getId()+";";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    accessorList.add(FieldAccessor.buildFromResultSet(resultSet));
                }
                return accessorList;
            }
        }, database);

        List<FieldAccessor> fieldAccessorList = new ArrayList<FieldAccessor>();
        for(Object object : response) {
            fieldAccessorList.add((FieldAccessor)object);
        }

        return fieldAccessorList;
    }


    /**
     * Find a Projects_Res with a project_id
     *
     * @param id a Projects_Res's id
     * @return ProjectAccessor(Projects_Res) or null
     */
    public static ProjectAccessor find(int id) {
        final Database database = new Database();
        final int projectId = id;

        return (ProjectAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'projects' where id = " + projectId + ";";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    return buildFromResultSet(resultSet);
                }

                return null;
            }
        }, database);
    }

    /**
     * Get the user associated with a project
     *
     * @return UserAccessor(User) or null
     */
    public UserAccessor getUser() {
        if(userAccessor != null) return userAccessor;
        else {
            userAccessor = UserAccessor.findByProjectId(super.getId());
        }

        return userAccessor;
    }

    public List<ImageAccessor> getImages() {
        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> accessorList = new ArrayList<Object>();

                String query = "select * from 'images' where project_id = "+getId()+";";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    accessorList.add(ImageAccessor.buildFromResultSet(resultSet));
                }
                return accessorList;
            }
        }, database);

        List<ImageAccessor> imageAccessorList = new ArrayList<ImageAccessor>();
        for(Object object : response) {
            imageAccessorList.add((ImageAccessor)object);
        }

        return imageAccessorList;
    }

    @Override
    public boolean save() {
        // what about having the user add this as project?
        // ANSWER: user.save => project.toSQL() => not atomic though.

        return Transaction.logic(new Transaction() {
            @Override
            public boolean logic() throws SQLException, ServerException {
                database.openConnection();
                database.addQuery(toSQL(Database.SPECIFIED_PRIMARY_KEY));

                if(userAccessor != null) {
                    database.addQuery(userAccessor.toSQL(Database.SPECIFIED_PRIMARY_KEY));
                }

                database.commit();

                // Update ID
                if(isNew()) setId(database.getLastIdForTable("projects"));

                return true;
            }
        }, database);
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public String toSQL(boolean autoPrimaryKey) {
        String newBase = "insert into 'projects' (";
        String updateBase = "insert or replace into 'projects' (";
        String newColumns = "title, records_per_image, first_y_coord, record_height";
        String updateColumns = "id, title, records_per_image, first_y_coord, record_height";
        String middle = ") SELECT ";
        String newValues = String.format("%s,%d,%d,%d", SQL.format(getTitle()), getRecordsPerImage(),
                                                        getFirstYCoord(), getRecordHeight());
        String updateValues = String.format("%d,%s", getId(), newValues);
        String end = ";";

        if(isNew()) {
            return newBase + newColumns + middle + newValues + end;
        } else {
            return updateBase + updateColumns + middle + updateValues + end;
        }
    }

    /* ********************************************
                        Helpers
     ******************************************** */

    /**
     * Determine if a model is new or not depending on it
     * its id attribute. Aka, have I been saved yet?
     *
     * @return saved status (true/ false).
     */
    public boolean isNew() {
        return getId() == 0;
    }

    protected static ProjectAccessor buildFromResultSet(ResultSet resultSet) throws SQLException{
        ProjectAccessor projectAccessor = new ProjectAccessor();

        projectAccessor.setId(resultSet.getInt(1));
        projectAccessor.setTitle(resultSet.getString(2));
        projectAccessor.setRecordsPerImage(resultSet.getInt(3));
        projectAccessor.setFirstYCoord(resultSet.getInt(4));
        projectAccessor.setRecordHeight(resultSet.getInt(5));

        return projectAccessor;
    }

    public Project getModel() {
        try {
            return (Project)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
