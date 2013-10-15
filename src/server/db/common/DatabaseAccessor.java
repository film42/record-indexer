package server.db.common;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:44 PM
 */
public interface DatabaseAccessor {

    /**
     * Accessor can save, this is instance specific
     *
     * We can just call save if we use a sql statement such as:
     * INERT OR REPLACE INTO table_name;
     *
     * @return true for success; false for failure
     */
    public boolean save();

    /**
     * Accessor can destroy (delete), this is instance specific
     *
     * @return true for success; false for failure
     */
    public boolean destroy();
}
