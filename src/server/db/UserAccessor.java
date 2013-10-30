package server.db;

import server.db.common.Database;

import server.db.common.DatabaseAccessor;
import server.db.common.SQL;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.models.Record;
import shared.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:43 PM
 */
public class UserAccessor extends User implements DatabaseAccessor {

    /**
     * Essential database backbone class
     */
    private Database database = new Database();

    /**
     * Create an accessor wrapper around a model
     *
     * @param user
     */
    public UserAccessor(User user) {
        super();
    }

    public UserAccessor() {}

    /**
     * Find a User by their username
     *
     * @param username - A User's username
     * @return UserAccessor or null
     */
    public static UserAccessor find(String username) {
        final Database database = new Database();
        final String usernameF = username;

        return (UserAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'users' where username = '" + usernameF + "';";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    return buildFromResultSet(resultSet);
                }

                return null;
            }
        }, database);
    }

    public static UserAccessor findByProjectId(int id) {
        final Database database = new Database();
        final int projectId = id;

        return (UserAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'users' where project_id = " + projectId + ";";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    return buildFromResultSet(resultSet);
                }

                return null;
            }
        }, database);
    }

    public static UserAccessor findByImageId(int id) {
        final Database database = new Database();
        final int imageId = id;

        return (UserAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'users' where image_id = " + imageId + ";";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    return buildFromResultSet(resultSet);
                }

                return null;
            }
        }, database);
    }

    private ImageAccessor imageAccessor = null;

    /**
     * Get a project's image (batch) for a user with image_id from User model
     *
     * @return new ImageAccessor or null
     */
    public ImageAccessor getImage() {
        if(imageAccessor != null) return imageAccessor;
        else {
            imageAccessor = ImageAccessor.find(getImageId());
        }

        return imageAccessor;
    }

    @Override
    public boolean save() {
        return Transaction.logic(new Transaction() {
            @Override
            public boolean logic() throws SQLException, ServerException {
                database.openConnection();

                database.addQuery(toSQL(Database.SPECIFIED_PRIMARY_KEY));

                database.commit();

                // Update ID
                if(isNew()) setId(database.getLastIdForTable("users"));

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
        String newBase = "insert into 'users' (";
        String updateBase = "insert or replace into 'users' (";
        String newColumns = "username, password, first_name, last_name, email, indexed_records, image_id";
        String updateColumns = "id, " + newColumns;
        String middle = ") SELECT ";

        String primaryKey;
        if(autoPrimaryKey) primaryKey = database.LAST_IMAGE;
        else {
            primaryKey = Integer.toString(getImageId());
            // TODO: What here we should take that out but idk if it helps?
            if(primaryKey.equals("0")) primaryKey = null;
        }
        String newValues = String.format("%s,%s,%s,%s,%s,%d,%s",
                SQL.format(getUsername()), SQL.format(getPassword()),
                SQL.format(getFirstName()), SQL.format(getLastName()),
                SQL.format(getEmail()), getIndexedRecords(), primaryKey);
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

    /**
     * Given some CURRENT resultSet context, create a 1-to-1 mapping for the model.
     *
     * @param resultSet - for current valid context
     * @return UserAccessor filled
     * @throws SQLException it can happen, caught by Transaction, it's all good.
     */
    protected static UserAccessor buildFromResultSet(ResultSet resultSet) throws SQLException{
        UserAccessor userAccessor = new UserAccessor();

        userAccessor.setId(resultSet.getInt(1));
        userAccessor.setUsername(resultSet.getString(2));
        userAccessor.setPassword(resultSet.getString(3));
        userAccessor.setFirstName(resultSet.getString(4));
        userAccessor.setLastName(resultSet.getString(5));
        userAccessor.setEmail(resultSet.getString(6));
        userAccessor.setIndexedRecords(resultSet.getInt(7));
        userAccessor.setImageId(resultSet.getInt(8));

        return userAccessor;
    }

    public User getModel() {
        try {
            return (User)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
