package server.db.common;

import server.errors.ServerException;
import shared.models.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/13/13
 * Time: 7:29 PM
 */
public abstract class Transaction {

    /**
     *
     * This is for perhaps all database transactions.
     * For example, when a database calls save or find,
     * we need to process those transactions, and we do
     * so here in an easy manner.
     *
     */

    /**
     * An easy way to use inner classes to remove ugly dupe code.
     *
     * @param transaction
     * @param database
     * @return an Object, so really whatever.
     */
    public static boolean logic(Transaction transaction, Database database) {
        try {
            return transaction.logic();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER LOGIC SAVE FUNCTION ERROR");
        } catch (ServerException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER LOGIC COMMIT FUNCTION ERROR");
        } finally {
            try {
                database.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Object object(Transaction transaction, Database database) {
        try {
            return transaction.object();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER object SAVE FUNCTION ERROR");
        } catch (ServerException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER object COMMIT FUNCTION ERROR");
        } finally {
            try {
                database.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static List<Object> array(Transaction transaction, Database database) {
        try {
            return transaction.array();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER array SAVE FUNCTION ERROR");
        } catch (ServerException e) {
            //e.printStackTrace();
            System.out.println("TODO: SWAP WITH LOGGER array COMMIT FUNCTION ERROR");
        } finally {
            try {
                database.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Object object() throws SQLException, ServerException {
        return null;
    }

    public boolean logic() throws SQLException, ServerException {
        return false;
    }

    public List<Object> array() throws SQLException, ServerException {
        return null;
    }

    public void transaction() throws SQLException, ServerException {
    }

}
