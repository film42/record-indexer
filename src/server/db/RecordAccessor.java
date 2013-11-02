package server.db;

import server.db.common.Database;
import server.db.common.DatabaseAccessor;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.models.Project;
import shared.models.Record;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:43 PM
 */
public class RecordAccessor extends Record implements DatabaseAccessor {

    /**
     * Essential database backbone class
     */
    private Database database = new Database();

    /**
     * Return RecordAccessor wrapper around record
     *
     * @param record a Record model
     */
    public RecordAccessor(Record record) {
        super();
    }

    public RecordAccessor() {}

    /**
     * Find a Record with an id
     *
     * @param id A records id
     * @return RecordAccessor with Record or null
     */
    public static RecordAccessor find(int id) {
        final Database database = new Database();
        final int recordId = id;

        return (RecordAccessor) Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'records' where id = " + recordId + ";";
                ResultSet resultSet = database.query(query);

                if (resultSet.next()) {
                    RecordAccessor recordAccessor = buildFromResultSet(resultSet);
                    return recordAccessor;
                }

                return null;
            }
        }, database);
    }

    /**
     * Get all values owned by a record
     *
     * @return [ValueAccessor(value)]
     */
    public List<ValueAccessor> getValues() {
        return null;
    }

    private ImageAccessor imageAccessor;

    /**
     * Get a record's image
     *
     * @return ImageAccessor(image) or null
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

                if (getImageId() != 0 || imageAccessor != null)
                    database.addQuery(toSQL(Database.SPECIFIED_PRIMARY_KEY));
                else
                    database.addQuery(toSQL(Database.AUTO_PRIMARY_KEY));

                database.commit();

                // Update ID
                if (isNew()) setId(database.getLastIdForTable("records"));

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
        String newBase = "insert into 'records' (";
        String updateBase = "insert or replace into 'records' (";
        String newColumns = "position, image_id";
        String updateColumns = "id, " + newColumns;
        String middle = ") SELECT ";

        String primaryKey;
        if(autoPrimaryKey) primaryKey = database.LAST_IMAGE;
        else primaryKey = Integer.toString(getImageId());

        String newValues = String.format("%d,%s", getPosition(), primaryKey);
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
     * @throws java.sql.SQLException it can happen, caught by Transaction, it's all good.
     */
    public static RecordAccessor buildFromResultSet(ResultSet resultSet) throws SQLException {
        RecordAccessor recordAccessor = new RecordAccessor();

        recordAccessor.setId(resultSet.getInt(1));
        recordAccessor.setPosition(resultSet.getInt(2));
        recordAccessor.setImageId(resultSet.getInt(3));

        return recordAccessor;
    }

    public Record getModel() {
        try {
            return (Record)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
