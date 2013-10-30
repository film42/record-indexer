package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.FieldAccessor;
import server.db.ImageAccessor;
import server.db.common.Database;

import java.util.List;

import static org.junit.Assert.* ;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 7:38 PM
 */
public class FieldAccessorTest {

    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Database.erase();
    }

    @Test
    public void testAll() throws Exception {
        FieldAccessor fieldAccessor = Factories.sampleField();
        assertEquals(true, fieldAccessor.save());
        FieldAccessor fieldAccessor1 = Factories.sampleField();
        assertEquals(true, fieldAccessor1.save());
        FieldAccessor fieldAccessor2 = Factories.sampleField();
        assertEquals(true, fieldAccessor2.save());

        List<FieldAccessor> fieldAccessorList = FieldAccessor.all();

        assertEquals(3, fieldAccessorList.size());
    }

    @Test
    public void testCannotSaveNull() throws  Exception{
        FieldAccessor fieldAccessor = new FieldAccessor();
        assertEquals(false, fieldAccessor.save());
    }

    @Test
    public void testSave() throws Exception {
        FieldAccessor fieldAccessor = Factories.sampleField();
        assertEquals(true, fieldAccessor.save());

    }

    @Test
    public void testFind() throws Exception {
        FieldAccessor fieldAccessor = Factories.sampleField();
        assertEquals(true, fieldAccessor.save());

        assertNotSame(0, fieldAccessor.getId());

        assertNotNull(FieldAccessor.find(fieldAccessor.getId()));
    }

    @Test
    public void testToSQL() throws Exception {

    }
}

