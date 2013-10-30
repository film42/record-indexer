package server.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.* ;
import server.db.common.Database;
import server.db.common.SQL;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * Factories: film42
 * Date: 10/29/13
 * Time: 11:24 AM
 */
public class Common {

    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
        Database.erase();
    }

    @Test
    public void testSQLFormat() {
        assertEquals("\'test\'", SQL.format("test"));
        assertEquals(null, SQL.format(null));
    }
}
