package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.ProjectAccessor;
import server.db.common.Database;
import static org.junit.Assert.* ;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 7:14 PM
 */
public class ProjectAccessorTest {

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
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());
        ProjectAccessor projectAccessor1 = Factories.sampleProject();
        assertEquals(true, projectAccessor1.save());
        ProjectAccessor projectAccessor2 = Factories.sampleProject();
        assertEquals(true, projectAccessor2.save());

        List<ProjectAccessor> projectAccessorList = ProjectAccessor.all();

        assertEquals(3, projectAccessorList.size());
    }

    @Test
    public void testCannotSaveNull() throws  Exception{
        ProjectAccessor projectAccessor = new ProjectAccessor();
        assertEquals(false, projectAccessor.save());
    }

    @Test
    public void testSave() throws Exception {
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());

    }

    @Test
    public void testFind() throws Exception {
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());

        assertNotSame(0, projectAccessor.getId());

        assertNotNull(ProjectAccessor.find(projectAccessor.getId()));
    }

    @Test
    public void testToSQL() throws Exception {

    }
}
