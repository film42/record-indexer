package server.db.accessors;

import factories.Factories;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.db.*;
import server.db.common.Database;
import shared.models.Project;
import shared.models.Value;

import java.util.ArrayList;
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
        fieldAccessor.setPosition(1);
        assertEquals(true, fieldAccessor.save());
        assertEquals(1, FieldAccessor.find(fieldAccessor.getId()).getPosition());

        FieldAccessor fieldAccessor1 = Factories.sampleField();
        fieldAccessor1.setPosition(2);
        assertEquals(true, fieldAccessor1.save());
        assertEquals(2, FieldAccessor.find(fieldAccessor1.getId()).getPosition());

        FieldAccessor fieldAccessor2 = Factories.sampleField();
        fieldAccessor2.setPosition(3);
        assertEquals(true, fieldAccessor2.save());
        assertEquals(3, FieldAccessor.find(fieldAccessor2.getId()).getPosition());

        List<FieldAccessor> fieldAccessorList = FieldAccessor.all();

        assertEquals(3, fieldAccessorList.size());
    }

    @Test
    public void testGetValuesForAField() throws Exception {
        // create a project
        ProjectAccessor projectAccessor = Factories.sampleProject();
        assertEquals(true, projectAccessor.save());

        // Create an image
        ImageAccessor imageAccessor = Factories.sampleImage();
        assertEquals(true, imageAccessor.save());

        // create some fields
        FieldAccessor fieldAccessor = Factories.sampleField();
        fieldAccessor.setProjectId(projectAccessor.getId());
        fieldAccessor.setPosition(1);
        assertEquals(true, fieldAccessor.save());

        FieldAccessor fieldAccessor1 = Factories.sampleField();
        fieldAccessor1.setProjectId(projectAccessor.getId());
        fieldAccessor1.setPosition(2);
        assertEquals(true, fieldAccessor1.save());

        // Now create some records and values
        List<Value> valueList = new ArrayList<Value>();

        // Add our values
        Value value = new Value();
        value.setPosition(1);
        value.setValue("word1");
        value.setType("STRING");
        valueList.add(value);

        // Add our values
        Value value1 = new Value();
        value1.setPosition(2);
        value1.setValue("word2");
        value1.setType("STRING");
        valueList.add(value1);

        // Add the records to our image
        imageAccessor.addRecord(valueList, 1);
        valueList.get(0).setValue("21");
        valueList.get(1).setValue("10");
        imageAccessor.addRecord(valueList, 2);

        // Save the batch query
        assertEquals(true, imageAccessor.save());

        List<ValueAccessor> valueAccessorList;
        // Now let's find out if this works (reload from db cause we're testing)
        FieldAccessor fieldAccessor2 = FieldAccessor.find(fieldAccessor.getId());
        valueAccessorList = fieldAccessor2.getColumnValues();
        assertEquals(2, valueAccessorList.size());
        assertEquals("word1", valueAccessorList.get(0).getValue());
        assertEquals("21", valueAccessorList.get(1).getValue());
        // And the second one
        FieldAccessor fieldAccessor3 = FieldAccessor.find(fieldAccessor1.getId());
        valueAccessorList = fieldAccessor3.getColumnValues();
        assertEquals(2, valueAccessorList.size());
        assertEquals("word2", valueAccessorList.get(0).getValue());
        assertEquals("10", valueAccessorList.get(1).getValue());

        // WooHoo, good job!
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

