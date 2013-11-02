package server.db.common;

import server.errors.ServerException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:44 PM
 */
public class Database {

    public static final boolean AUTO_PRIMARY_KEY = true;
    public static final boolean SPECIFIED_PRIMARY_KEY = false;
    public static final boolean PRODUCTION_MODE = true;
    public static final boolean DEVELOPMENT_MODE = true;
    public static final boolean TEST_MODE = false;

    /**
     * Logging for when implemented.
     */
    private static Logger logger;

    private Connection connection;

    private static String dbUrl = "";

    /**
     * We can load the DB driver here.
     */
    public static void init(boolean production) throws ServerException {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);

            if(production) dbUrl = "jdbc:sqlite:db/database.sqlite3";
            else dbUrl = "jdbc:sqlite:db/test.sqlite3";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServerException("SQLite was not found");
        }
    }

    public static void erase() throws ServerException, SQLException {
        Database database = new Database();
        database.openConnection();
        database.voidQuery("delete from users;");
        database.voidQuery("delete from projects;");
        database.voidQuery("delete from records;");
        database.voidQuery("delete from images;");
        database.voidQuery("delete from 'values';");
        database.voidQuery("delete from fields;");
        database.commit();
        database.closeConnection();
    }

     /* ********************************************
               Basic Connection Handling
     ******************************************** */

    /**
     * Create a new connection for committing
     *
     * @throws ServerException
     */
    public void openConnection() throws ServerException {
        try {
            // Ensure we don't hit a collision
            if(connection != null) return;

            connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new ServerException("Error establishing connection to: " + dbUrl);
        }
    }

    /**
     * Close the DB connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }

    /**
     * Commit query list to the database
     *
     * @throws ServerException
     */
    public void commit() throws ServerException, SQLException {
        try {
            for(PreparedStatement query : queryBatch) {
                int response = 0;
                response = query.executeUpdate();
                if(response != 1) {
                    connection.rollback();
                    throw new ServerException("Bad query update");
                }
            }
            try {
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new ServerException(String.format("Error committing %d queries, rolling back!",
                    queryBatch.size()));
        } finally {
            queryBatch.clear();
        }
    }

    /**
     * query to the database
     *
     * @return ResultSet for Accessor to interpret.
     * @throws ServerException
     */
    public ResultSet query(String sql) throws ServerException, SQLException {
        PreparedStatement preparedStatement;
        try {

            preparedStatement = connection.prepareStatement(sql);

            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerException("Error running query!");
        }
    }

    /**
     * query to the database
     *
     * @throws ServerException
     */
    public void voidQuery(String sql) throws ServerException, SQLException {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.addBatch(sql);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerException("Error running query!");
        }
    }

    /* ********************************************
        Adding a SQL option to pending commits
     ******************************************** */

    private List<PreparedStatement> queryBatch = new ArrayList<PreparedStatement>();

    /**
     * Allows a sugar coated interface to the database.
     *
     * @param sql A String sql command that each accessor can pass in as it builds
     */
    public void addQuery(String sql) throws SQLException {
        PreparedStatement preparedStatement;

        preparedStatement = connection.prepareStatement(sql);
        queryBatch.add(preparedStatement);
    }

    /**
     * Grab the last ID provided some table name.
     *
     * @param table
     * @return the num if valid, otherwise -1.
     * @throws SQLException
     * @throws ServerException
     */
    public int getLastIdForTable(String table) throws SQLException, ServerException {
        ResultSet resultSet = query("SELECT MAX(ID) FROM '" +table+ "';" );
        if(resultSet.next()) return resultSet.getInt(1);

        return -1;
    }


    /* ********************************************
                Common SQL statements
     ******************************************** */

    public String LAST_PROJECT = "(SELECT MAX(ID) FROM projects)";
    public String LAST_IMAGE = "(SELECT MAX(ID) FROM images)";
    public String LAST_RECORD = "(SELECT MAX(ID) FROM records)";
    public String LAST_FIELD = "(SELECT MAX(ID) FROM fields)";
    public String LAST_USER = "(SELECT MAX(ID) FROM users)";
    public String LAST_VALUE = "(SELECT MAX(ID) FROM \'values\')";

}
