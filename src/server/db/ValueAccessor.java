package server.db;

import server.db.common.Database;

import server.db.common.DatabaseAccessor;
import server.db.common.SQL;
import server.db.common.Transaction;
import server.errors.ServerException;
import shared.common.BaseModel;
import shared.models.User;
import shared.models.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:51 PM
 */
public class ValueAccessor extends Value implements DatabaseAccessor {

    /**
     * Essential database backbone class
     */
    private Database database = new Database();


    public ValueAccessor() {}

    /**
     * Return ValueAccessor around a Value model
     *
     * @param value
     */
    public ValueAccessor(Value value) {
        super();
    }

    /**
     * Return all Values in database as a list
     *
     * @return List of Value
     */
    public static List<ValueAccessor> all() {
        final Database database = new Database();

        List<Object> response =  Transaction.array(new Transaction() {
            @Override
            public List<Object> array() throws SQLException, ServerException {
                database.openConnection();
                List<Object> valueAccessorList = new ArrayList<Object>();

                String query = "select * from 'values';";
                ResultSet resultSet = database.query(query);

                while(resultSet.next()) {
                    ValueAccessor valueAccessor = new ValueAccessor();
                    valueAccessor.setId(resultSet.getInt(1));
                    valueAccessor.setValue(resultSet.getString(2));
                    valueAccessor.setType(resultSet.getString(3));
                    valueAccessor.setRecordId(resultSet.getInt(4));

                    valueAccessorList.add(valueAccessor);
                }
                return valueAccessorList;
            }
        }, database);

        List<ValueAccessor> valueAccessorList = new ArrayList<ValueAccessor>();
        for(Object object : response) {
            valueAccessorList.add((ValueAccessor)object);
        }

        return valueAccessorList;
    }

    /**
     * Find a Value with an id
     *
     * @param id - a Value id
     * @return ValueAccessor(Value) or null
     */
    public static ValueAccessor find(int id) {
        final Database database = new Database();
        final int identity = id;

        return (ValueAccessor)Transaction.object(new Transaction() {
            @Override
            public Object object() throws SQLException, ServerException {
                database.openConnection();

                String query = "select * from 'values' where id = " + identity + ";";
                ResultSet resultSet = database.query(query);

                if(resultSet.next()) {
                    ValueAccessor valueAccessor = new ValueAccessor();

                    valueAccessor.setId(resultSet.getInt(1));
                    valueAccessor.setValue(resultSet.getString(2));
                    valueAccessor.setType(resultSet.getString(3));
                    valueAccessor.setRecordId(resultSet.getInt(4));

                    return valueAccessor;
                }

                return null;
            }
        }, database);
    }

    @Override
    public boolean save() {
        return Transaction.logic(new Transaction() {
            @Override
            public boolean logic() throws SQLException, ServerException {
                database.openConnection();

                if(getRecordId() == 0)
                    database.addQuery(toSQL(Database.AUTO_PRIMARY_KEY));
                else
                    database.addQuery(toSQL(Database.SPECIFIED_PRIMARY_KEY));

                database.commit();

                // Update ID
                if(isNew()) setId(database.getLastIdForTable("values"));

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
        String base = "insert or replace into 'values' (";
        String newColumns = "value, type, record_id";
        String updateColumns = "id, value, type, record_id";
        String middle = ") SELECT ";

        String primaryKey;
        if(autoPrimaryKey) primaryKey = database.LAST_RECORD;
        else primaryKey = Integer.toString(getRecordId());

        String newValues = String.format("%s,%s,%s", SQL.format(getValue()), SQL.format(getType()), primaryKey);
        String updateValues = String.format("%d,%s", getId(), newValues);
        String end = ";";

        if(isNew()) {
            return base + newColumns + middle + newValues + end;
        } else {
            return base + updateColumns + middle + updateValues + end;
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

    public Value getModel() {
        try {
            return (Value)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
