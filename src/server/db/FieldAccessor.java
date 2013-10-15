package server.db;

import server.db.common.DatabaseAccessor;
import shared.models.Field;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:42 PM
 */
public class FieldAccessor extends Field implements DatabaseAccessor {

    /**
     * Return FieldAccessor around a Field model
     *
     * @param field
     */
    public FieldAccessor(Field field) {
        super();
    }

    /**
     * Return all fields in database as a list
     *
     * @return List of fields
     */
    public static List<Field> all() {
        return null;
    }

    /**
     * Find a field with an id
     *
     * @param id - A Field id
     * @return FieldAccessor(Field) or null
     */
    public static FieldAccessor find(int id) {
        Field field = null;
        return new FieldAccessor(field);
    }

    //
    // Required Operations
    //
    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
