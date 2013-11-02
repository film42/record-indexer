package factories;

import server.db.*;

/**
 * Created with IntelliJ IDEA.
 * Factories: film42
 * Date: 10/29/13
 * Time: 3:36 PM
 */
public class Factories {

    public static UserAccessor sampleUser() {
        UserAccessor userAccessor = new UserAccessor();

        userAccessor.setFirstName("Garrett");
        userAccessor.setLastName("Thornburg");
        userAccessor.setUsername("james");
        userAccessor.setIndexedRecords(0);
        userAccessor.setPassword("apple");
        userAccessor.setEmail("film42@gmail.com");

        return userAccessor;
    }

    public static ValueAccessor sampleValue() {
        ValueAccessor valueAccessor = new ValueAccessor();

        valueAccessor.setType("string");
        valueAccessor.setValue("testing");

        return valueAccessor;
    }

    public static ProjectAccessor sampleProject() {
        ProjectAccessor projectAccessor = new ProjectAccessor();

        projectAccessor.setFirstYCoord(222);
        projectAccessor.setRecordHeight(111);
        projectAccessor.setRecordsPerImage(333);
        projectAccessor.setTitle("Some title");

        return projectAccessor;
    }

    public static ImageAccessor sampleImage() {
        ImageAccessor imageAccessor = new ImageAccessor();

        imageAccessor.setFile("some/path.png");

        return imageAccessor;
    }

    public static RecordAccessor sampleRecord() {
        RecordAccessor recordAccessor = new RecordAccessor();

        return recordAccessor;
    }

    public static FieldAccessor sampleField() {
        FieldAccessor fieldAccessor = new FieldAccessor();

        fieldAccessor.setHelpHtml("help.html");
        fieldAccessor.setKnownData("knownData.html");
        fieldAccessor.setTitle("My Title");
        fieldAccessor.setWidth(333);
        fieldAccessor.setxCoord(222);
        fieldAccessor.setPosition(1);

        return fieldAccessor;
    }
}
