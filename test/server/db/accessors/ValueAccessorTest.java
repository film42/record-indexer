package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.ValueAccessor;
import server.db.common.Database;

import java.util.List;
import static org.junit.Assert.* ;

import static org.junit.Assert.* ;

/**
 * Created with IntelliJ IDEA.
 * Factories: film42
 * Date: 10/27/13
 * Time: 12:46 AM
 */
public class ValueAccessorTest {
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
        ValueAccessor valueAccessor = Factories.sampleValue();
        assertEquals(true, valueAccessor.save());
        ValueAccessor valueAccessor1 = Factories.sampleValue();
        assertEquals(true, valueAccessor1.save());
        ValueAccessor valueAccessor2 = Factories.sampleValue();
        assertEquals(true, valueAccessor2.save());

        List<ValueAccessor> valueAccessorList = ValueAccessor.all();

        assertEquals(3, valueAccessorList.size());
    }

    @Test
    public void testCanSaveNull() throws  Exception{
        ValueAccessor valueAccessor = new ValueAccessor();
        assertEquals(true, valueAccessor.save());
    }

    @Test
    public void testSave() throws Exception {
        ValueAccessor valueAccessor = Factories.sampleValue();
        assertEquals(true, valueAccessor.save());
    }

    @Test
    public void testFind() throws Exception {
        ValueAccessor valueAccessor = Factories.sampleValue();
        assertEquals(true, valueAccessor.save());

        assertNotSame(0, valueAccessor.getId());

        assertNotNull(ValueAccessor.find(valueAccessor.getId()));
    }

    @Test
    public void testToSQL() throws Exception {

    }
}
