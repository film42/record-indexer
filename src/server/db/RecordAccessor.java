package server.db;

import server.db.common.DatabaseAccessor;
import shared.models.Record;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:43 PM
 */
public class RecordAccessor extends Record implements DatabaseAccessor {

    /**
     * Return RecordAccessor wrapper around record
     *
     * @param record a Record model
     */
    public RecordAccessor(Record record) {
        super();
    }

    /**
     * Find a Record with an id
     *
     * @param id A records id
     * @return RecordAccessor with Record or null
     */
    public static RecordAccessor find(int id) {
        Record record = null;
        return new RecordAccessor(record);
    }

    /**
     * Get all values owned by a record
     *
     * @return [ValueAccessor(value)]
     */
    public List<ValueAccessor> getValues() {
        return null;
    }

    /**
     * Get a record's image
     *
     * @return ImageAccessor(image) or null
     */
    public ImageAccessor getImage() {
        return ImageAccessor.find(super.getImageId());
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
