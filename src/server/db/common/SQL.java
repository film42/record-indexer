package server.db.common;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 11:41 AM
 */
public class SQL {

    public static String format(String string) {
        if(string == null) return null;
        return "\'" + string + "\'";
    }

}
