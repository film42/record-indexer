package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.RecordAccessor;
import server.db.common.Database;

import java.util.List;

import static org.junit.Assert.* ;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 7:35 PM
 */
public class RecordAccessorTest {

    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Database.erase();
    }

    @Test
    public void testSave() throws Exception {
        RecordAccessor recordAccessor = Factories.sampleRecord();
        assertEquals(true, recordAccessor.save());

    }

    @Test
    public void testFind() throws Exception {
        RecordAccessor recordAccessor = Factories.sampleRecord();
        assertEquals(true, recordAccessor.save());

        assertNotSame(0, recordAccessor.getId());

        assertNotNull(RecordAccessor.find(recordAccessor.getId()));
    }

    @Test
    public void testToSQL() throws Exception {

    }
}
