package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.* ;
import server.db.UserAccessor;
import server.db.common.Database;

import static org.junit.Assert.* ;


/**
 * Created with IntelliJ IDEA.
 * Factories: film42
 * Date: 10/27/13
 * Time: 9:02 AM
 */
public class UserAccessorTest {
    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Database.erase();
    }

    @Test
    public void testCannotSaveNull() throws Exception {
        UserAccessor userAccessor = new UserAccessor();
        assertEquals(false, userAccessor.save());
    }

    @Test
    public void testFind() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.save());

        UserAccessor userAccessor1 = UserAccessor.find("james");
        assertNotNull(userAccessor1);

        assertEquals("Garrett", userAccessor1.getFirstName());
        assertEquals("Thornburg", userAccessor1.getLastName());
        assertEquals("james", userAccessor1.getUsername());
        assertEquals(0, userAccessor1.getIndexedRecords());
        assertEquals("apple", userAccessor1.getPassword());
        assertEquals("film42@gmail.com", userAccessor1.getEmail());
        assertEquals(0, userAccessor1.getImageId());
    }

    @Test
    public void testFindByImageId() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();
        userAccessor.setImageId(1);
        assertEquals(true, userAccessor.save());

        assertNull(userAccessor.getImage());
    }

    @Test
    public void testGetImage() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.save());
    }

    @Test
    public void testSave() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.save());
    }

    @Test
    public void testToSQL() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();

        String sql = "insert into 'users' (username,password,first_name,last_name,email,indexed_records,"
                     + "image_id) SELECT 'james','apple','Garrett','Thornburg','film42@gmail.com',0,null;";

        assertEquals(sql, userAccessor.toSQL(false));
    }

    @Test
    public void testIsNew() throws Exception {
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.isNew());
        assertEquals(true, userAccessor.save());
        assertEquals(false, userAccessor.isNew());
    }
}
