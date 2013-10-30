package server.db.common;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.ProjectAccessor;
import server.errors.ServerException;

import java.sql.SQLException;

import static org.junit.Assert.* ;


/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 7:47 PM
 */
public class DatabaseTest {
    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Database.erase();
    }

    @Test
    public void testInit() throws Exception {
        // Basically if it doesn't throw an exception we're good.
        // It's lame, but I could also just leave this out.
        Database.init(Database.TEST_MODE);
        Database.init(Database.DEVELOPMENT_MODE);
        Database.init(Database.PRODUCTION_MODE);
    }

    @Test
    public void testErase() throws Exception {
        // Create 3 projects
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());
        ProjectAccessor projectAccessor1 = Factories.sampleProject();
        assertEquals(true, projectAccessor1.save());
        ProjectAccessor projectAccessor2 = Factories.sampleProject();
        assertEquals(true, projectAccessor2.save());

        assertEquals(3, ProjectAccessor.all().size());

        // Erase the DB
        Database.erase();

        assertEquals(0, ProjectAccessor.all().size());
    }

    @Test
    public void testConnection() throws Exception {
        Database.init(Database.TEST_MODE);

        Database database = new Database();

        try {
            database.openConnection();
        } catch (ServerException e) {
            fail("Database could not be opened");
        }

        try {
            database.closeConnection();
        } catch (SQLException e) {
            fail("Database could not be closed");
        }

    }

    @Test
    public void testCommit() throws Exception {

    }

    @Test
    public void testQuery() throws Exception {

    }

    @Test
    public void testVoidQuery() throws Exception {
        Database database = new Database();
        database.openConnection();

        try {
            database.voidQuery("delete from users;");
        } catch (ServerException e) {
            fail("Something went wrong running a query");
        } finally {
            database.closeConnection();
        }

    }

    @Test
    public void testAddQuery() throws Exception {

    }

    @Test
    public void testGetLastIdForTable() throws Exception {
        // Create 2 projects
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());
        ProjectAccessor projectAccessor1 = Factories.sampleProject();
        assertEquals(true, projectAccessor1.save());

        Database database = new Database();
        database.openConnection();

        assertEquals(projectAccessor1.getId(), database.getLastIdForTable("projects"));

        database.closeConnection();
    }
}
