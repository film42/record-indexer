package server.db;

import server.db.common.Database;
import server.db.common.DatabaseAccessor;
import server.db.common.SQL;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.models.Image;
import shared.models.Value;

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
public class ImageAccessor extends Image implements DatabaseAccessor {

    /**
     * Essential database backbone class
     */
    private Database database = new Database();

    /**
     * Make ImageAccessor wrapper around Image model
     *
     * @param image - an Image model
     */
    public ImageAccessor(Image image) {
        super();
    }

    public ImageAccessor() {}

    /**
     * Get all the available images, that is, given a project, it will return a list
     * of images that haven't been claimed by other users.
     *
     * @return List of ImageAccessors
     */
    public static List<ImageAccessor> allAvailible(int projectId) {
        final Database database = new Database();
        final int projectIdF = projectId;

        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> accessorList = new ArrayList<Object>();

                String query;
                query = "select * from images where project_id = "+projectIdF+" AND id IS NOT "
                        + "(select image_id from users where image_id is not null);";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    accessorList.add(buildFromResultSet(resultSet));
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

    /**
     * Find an Image with id
     *
     * @param id - and Image id
     * @return ImageAccessor(image)
     */
    public static ImageAccessor find(int id) {
        final Database database = new Database();
        final int imageId = id;

        return (ImageAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'images' where id = " + imageId + ";";
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
     * Get a project for a user with project_id from Image model
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

    private UserAccessor userAccessor = null;

    /**
     * Get a project for a user with project_id from Image model
     *
     * @return new ProjectAccessor or null
     */
    public UserAccessor getUser() {
        if(userAccessor != null) return userAccessor;
        else {
            userAccessor = UserAccessor.findByImageId(getId());
        }

        return userAccessor;
    }

    /**
     * You can now add a record to an image by simple passing in its values.
     * This makes life SOOO nice.
     *
     * @param valueList a list of Value models
     * @param position
     */
    public void addRecord(List<Value> valueList, int position) {
        try {
            database.openConnection();

            RecordAccessor recordAccessor = new RecordAccessor();
            recordAccessor.setPosition(position);
            recordAccessor.setImageId(this.getId());

            database.addQuery(recordAccessor.toSQL(Database.SPECIFIED_PRIMARY_KEY));

            for(int i = 0; i < valueList.size(); i++) {
                Value value = valueList.get(i);
                ValueAccessor valueAccessor = new ValueAccessor(value);
                valueAccessor.setPosition((i+1));
                // Will auto add the record added just above this loop
                database.addQuery(valueAccessor.toSQL(Database.AUTO_PRIMARY_KEY));
            }

            // We don't call commit because save() will allow us to batch commit.
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

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
                if(isNew()) setId(database.getLastIdForTable("images"));

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
        String newBase = "insert into 'images' (";
        String updateBase = "insert or replace into 'images' (";
        String newColumns = "file, project_id";
        String updateColumns = "id, " + newColumns;
        String middle = ") SELECT ";

        String primaryKey;
        if(autoPrimaryKey) primaryKey = database.LAST_PROJECT;
        else primaryKey = Integer.toString(getProjectId());

        String newValues = String.format("%s,%s", SQL.format(getFile()), primaryKey);
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

    protected static ImageAccessor buildFromResultSet(ResultSet resultSet) throws SQLException {
        ImageAccessor imageAccessor = new ImageAccessor();

        imageAccessor.setId(resultSet.getInt(1));
        imageAccessor.setFile(resultSet.getString(2));
        imageAccessor.setProjectId(resultSet.getInt(3));

        return imageAccessor;
    }

    public Image getModel() {
        try {
            return (Image)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
