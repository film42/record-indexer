package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.*;
import server.db.common.Database;
import shared.models.Value;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.* ;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 7:24 PM
 */
public class ImageAccessorTest {

    @Before
    public void setUp() throws Exception {
        Database.init(Database.TEST_MODE);
    }

    @After
    public void tearDown() throws Exception {
        Database.erase();
    }

    @Test
    public void testFindAll() throws Exception {
        // Create a project and save
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());

        // Create 3 images and assign to project above; save.
        ImageAccessor imageAccessor = Factories.sampleImage();
        imageAccessor.setProjectId(projectAccessor.getId());
        assertEquals(true, imageAccessor.save());

        ImageAccessor imageAccessor1 = Factories.sampleImage();
        imageAccessor1.setProjectId(projectAccessor.getId());
        assertEquals(true, imageAccessor1.save());

        ImageAccessor imageAccessor2 = Factories.sampleImage();
        imageAccessor2.setProjectId(projectAccessor.getId());
        assertEquals(true, imageAccessor2.save());

        // Make sure there are 3 available for assignment
        List<ImageAccessor> imageAccessorList;
        imageAccessorList = ImageAccessor.allAvailible(projectAccessor.getId());
        assertEquals(3, imageAccessorList.size());

        // Assign image 1 to new user
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.save());
        userAccessor.setImageId(imageAccessor.getId());
        assertEquals(true, userAccessor.save());

        // Make sure there are only 2 available now
        imageAccessorList = ImageAccessor.allAvailible(projectAccessor.getId());
        assertEquals(2, imageAccessorList.size());
    }

    @Test
    public void testCannotSaveNull() throws  Exception{
        ImageAccessor imageAccessor = new ImageAccessor();
        assertEquals(false, imageAccessor.save());
    }

    @Test
    public void testSave() throws Exception {
        ImageAccessor imageAccessor = Factories.sampleImage();
        assertEquals(true, imageAccessor.save());

    }

    @Test
    public void testFind() throws Exception {
        ImageAccessor imageAccessor = Factories.sampleImage();
        assertEquals(true, imageAccessor.save());

        assertNotSame(0, imageAccessor.getId());

        assertNotNull(ImageAccessor.find(imageAccessor.getId()));
    }

    @Test
    public void testAvailImages() throws Exception {

        // Create a project
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());

        // Assert there are no images
        assertEquals(0, ImageAccessor.allAvailible(projectAccessor.getId()).size());

        // Add an image
        ImageAccessor imageAccessor = Factories.sampleImage();
        imageAccessor.setProjectId(projectAccessor.getId());
        assertEquals(true, imageAccessor.save());

        // Assert there is one available image
        assertEquals(1, ImageAccessor.allAvailible(projectAccessor.getId()).size());

        // Create a user
        UserAccessor userAccessor = Factories.sampleUser();
        assertEquals(true, userAccessor.save());

        // Assign that image to a user
        userAccessor.setImageId(imageAccessor.getId());
        assertEquals(true, userAccessor.save());

        // Assert there are no available images
        assertEquals(0, ImageAccessor.allAvailible(projectAccessor.getId()).size());

        // Create a record for that image now
        RecordAccessor recordAccessor = Factories.sampleRecord();
        recordAccessor.setImageId(imageAccessor.getId());
        assertEquals(true, recordAccessor.save());

        // Remove from the user
        userAccessor.setImageId(0);
        assertEquals(true, userAccessor.save());

        // Assert there are still no available images
        assertEquals(0, ImageAccessor.allAvailible(projectAccessor.getId()).size());
    }

    @Test
    public void testSaveRecordValues() throws Exception {
        List<Value> valuesList = new ArrayList<Value>();
        // Add two value models to the list
        Value value = new Value();
        value.setType("STRING");
        value.setValue("Testing 123");
        valuesList.add(value);

        Value value1 = new Value();
        value1.setType("STRING");
        value1.setValue("Testing 456");
        valuesList.add(value1);

        ImageAccessor imageAccessor = Factories.sampleImage();

        // Standard user test
        assertEquals(true, imageAccessor.save());
        assertNotSame(0, imageAccessor.getId());
        assertNotNull(ImageAccessor.find(imageAccessor.getId()));


        // Test to make sure there are no values
        assertEquals(0, ValueAccessor.all().size());

        imageAccessor.addRecord(valuesList, 1);
        assertEquals(true, imageAccessor.save());

        // Let's ensure we have 2 values now
        assertEquals(2, ValueAccessor.all().size());
    }

    @Test
    public void testToSQL() throws Exception {

    }
}
