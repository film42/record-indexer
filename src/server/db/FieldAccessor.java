package server.db;

import server.db.common.Database;
import server.db.common.DatabaseAccessor;
import server.db.common.SQL;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.models.Field;
import shared.models.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:42 PM
 */
public class FieldAccessor extends Field implements DatabaseAccessor {

    /**
     * Essential database backbone class
     */
    private Database database = new Database();

    /**
     * Return FieldAccessor around a Field model
     *
     * @param field
     */
    public FieldAccessor(Field field) {
        super();
    }

    public FieldAccessor() {}

    /**
     * Return all fields in database as a list
     *
     * @return List of FieldAccessors
     */
    public static List<FieldAccessor> all() {
        final Database database = new Database();

        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> accessorList = new ArrayList<Object>();

                String query = "select * from 'fields';";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    accessorList.add(buildFromResultSet(resultSet));
                }
                return accessorList;
            }
        }, database);

        List<FieldAccessor> projectAccessorList = new ArrayList<FieldAccessor>();
        for(Object object : response) {
            projectAccessorList.add((FieldAccessor)object);
        }

        return projectAccessorList;
    }

    /**
     * Find a field with an id
     *
     * @param id - A Field id
     * @return FieldAccessor(Field) or null
     */
    public static FieldAccessor find(int id) {
        final Database database = new Database();
        final int fieldId = id;

        return (FieldAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'fields' where id = " + fieldId + ";";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    return buildFromResultSet(resultSet);
                }

                return null;
            }

        }, database);
    }

    private ProjectAccessor projectAccessor = null;

    /**
     * Get a project for a user with project_id from Fields model
     *
     * @return new ProjectAccessor or null
     */
    public ProjectAccessor getProject() {
        if(projectAccessor != null) return projectAccessor;
        else {
            projectAccessor = ProjectAccessor.find(getProjectId());
        }

        return projectAccessor;
    }

    public List<ValueAccessor> getValues() {

        // Field => arrayPos && project_id 4
        // Project(4) => Records => ValuesOf(arrayPos)
        // Values => values[all].equals(query1,2,3)
        return null;
    }

    //
    // Required Operations
    //
    @Override
    public boolean save() {
        return Transaction.logic(new Transaction() {
            @Override
            public boolean logic() throws SQLException, ServerException {
                database.openConnection();

                if (getProjectId() != 0 || projectAccessor != null)
                    database.addQuery(toSQL(Database.SPECIFIED_PRIMARY_KEY));
                else
                    database.addQuery(toSQL(Database.AUTO_PRIMARY_KEY));


                database.commit();

                // Update ID
                if(isNew()) setId(database.getLastIdForTable("fields"));

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
        String newBase = "insert into 'fields' (";
        String updateBase = "insert or replace into 'fields' (";
        String newColumns = "title, x_coord, width, help_html, known_data, project_id";
        String updateColumns = "id, " + newColumns;
        String middle = ") SELECT ";

        String primaryKey;
        if(autoPrimaryKey) primaryKey = database.LAST_PROJECT;
        else primaryKey = Integer.toString(getProjectId());

        String newValues = String.format("%s,%d,%d,%s,%s,%s", SQL.format(getTitle()), getxCoord(),
                                                              getWidth(), SQL.format(getHelpHtml()),
                                                              SQL.format(getKnownData()), primaryKey);
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

    protected static FieldAccessor buildFromResultSet(ResultSet resultSet) throws SQLException {
        FieldAccessor fieldAccessor = new FieldAccessor();

        fieldAccessor.setId(resultSet.getInt(1));
        fieldAccessor.setTitle(resultSet.getString(2));
        fieldAccessor.setxCoord(resultSet.getInt(3));
        fieldAccessor.setWidth(resultSet.getInt(4));
        fieldAccessor.setHelpHtml(resultSet.getString(5));
        fieldAccessor.setKnownData(resultSet.getString(6));
        fieldAccessor.setProjectId(resultSet.getInt(7));

        return fieldAccessor;
    }

    public Field getModel() {
        try {
            return (Field)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
