package server.db.common;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/25/13
 * Time: 6:53 PM
 */
public interface DatabaseAccessor {

    /**
     * Accessor can save, this is instance specific
     *
     * Ok, also, something else to consider.. this should cascade down.
     *
     * EX: Project
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

    /**
     * Should return a compiled sql pattern that's valid provided
     * including both NEW and UPDATE params.
     *
     * @return compiled sql string, either INSERT or UPDATE.
     * @param autoPrimaryKey
     */
    public String toSQL(boolean autoPrimaryKey);

}
