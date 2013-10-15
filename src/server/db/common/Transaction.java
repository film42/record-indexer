package server.db.common;

import shared.models.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/13/13
 * Time: 7:29 PM
 */
public class Transaction {

    /**
     *
     * This is for perhaps all database transactions.
     * For example, when a database calls save or find,
     * we need to process those transactions, and we do
     * so here in an easy manner.
     *
     */

    //
    // Save
    //
    public static boolean save(Image image) {
        return false;
    }

    public static boolean save(User user) {
        return false;
    }

    public static boolean save(Field field) {
        return false;
    }

    public static boolean save(Record record) {
        return false;
    }

    public static boolean save(Value value) {
        return false;
    }


    //
    // Destroy
    //
    public static boolean destroy(Image image) {
        return false;
    }

    public static boolean destroy(User user) {
        return false;
    }

    public static boolean destroy(Field field) {
        return false;
    }

    public static boolean destroy(Record record) {
        return false;
    }

    public static boolean destroy(Value value) {
        return false;
    }

}
