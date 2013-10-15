package server.db;

import server.db.common.DatabaseAccessor;

import shared.models.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:51 PM
 */
public class ValueAccessor extends Value implements DatabaseAccessor{


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
    public static List<Value> all() {
        return null;
    }

    /**
     * Find a Value with an id
     *
     * @param id - a Value id
     * @return ValueAccessor(Value) or null
     */
    public static ValueAccessor find(int id) {
        Value value = null;
        return new ValueAccessor(value);
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
