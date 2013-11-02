package server.db.importer;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import server.db.*;
import server.db.common.Database;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/24/13
 * Time: 2:34 PM
 */
public class Importer {

    public static void main(String[] args) throws Exception {
        Database.init(Database.PRODUCTION_MODE);

        Database.erase();

        new Importer().run();
    }

    public void run() throws Exception {
        // Copy Files
        String pathToXML = "demo/indexer_data/Records/Records.xml";
        String pathToImages = "demo/indexer_data/Records/images/";
        String pathToKnownData = "demo/indexer_data/Records/knowndata/";
        String pathToFieldHelp = "demo/indexer_data/Records/fieldhelp/";


        File destinationPath = new File("db/statics/");
        // Copy images to statics/images
        FileUtils.copyDirectoryToDirectory(new File(pathToImages), destinationPath);
        // Copy known_data to statics/knownData
        FileUtils.copyDirectoryToDirectory(new File(pathToKnownData), destinationPath);
        // Copy field_help tp statics/fieldHelp
        FileUtils.copyDirectoryToDirectory(new File(pathToFieldHelp), destinationPath);


        // Import XML
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        File xmlFile = new File(pathToXML);
        Document document = builder.parse(xmlFile);

        /**
         * Get Users
         */
        NodeList usersList = document.getElementsByTagName("user");
        for(int i = 0; i < usersList.getLength(); i++) {
            // Get the the element
            Element userElement = (Element)usersList.item(i);

            parseUserBlock(userElement);
        }

        /**
         * This will nest all the way down from Projects
         */
        NodeList projectsList = document.getElementsByTagName("project");
        for(int i = 0; i < projectsList.getLength(); i++) {
            // Get the the element
            Element projectElement = (Element)projectsList.item(i);

            parseProjectBlock(projectElement);

            /**
             * Fields
             */
            NodeList fieldsList = projectElement.getElementsByTagName("field");
            for(int j = 0; j < fieldsList.getLength(); j++) {
                Element fieldElement = (Element)fieldsList.item(j);

                parseFieldBlock(fieldElement, (j+1));
            }

            /**
             * Images
             */
            NodeList imagesList = projectElement.getElementsByTagName("image");
            for(int j = 0; j < imagesList.getLength(); j++) {
                Element imageElement = (Element)imagesList.item(j);

                parseImageBlock(imageElement);

                iteratingThroughRecords(imageElement);
            }
        }
    }

    public void iteratingThroughRecords(Element imageElement) throws Exception {
        /**
         * Records
         */
        NodeList recordList = imageElement.getElementsByTagName("record");
        for(int k = 0; k < recordList.getLength(); k++) {
            Element recordElement = (Element)recordList.item(k);

            parseRecordBlock(recordElement, (k + 1));

            /**
             * Values
             */
            NodeList valueList = recordElement.getElementsByTagName("value");
            for(int l = 0; l < valueList.getLength(); l++) {
                Element valueElement = (Element)valueList.item(l);

                parseValueBlock(valueElement, (l+1));
            }
        }
    }

    public UserAccessor parseUserBlock(Element userElement) throws Exception {
        System.out.print("Parsing User Block ------- ");

        UserAccessor userAccessor = new UserAccessor();

        String username = userElement.getElementsByTagName("username").item(0).getTextContent();
        String password = userElement.getElementsByTagName("password").item(0).getTextContent();
        String firstname = userElement.getElementsByTagName("firstname").item(0).getTextContent();
        String lastname = userElement.getElementsByTagName("lastname").item(0).getTextContent();
        String email = userElement.getElementsByTagName("email").item(0).getTextContent();
        String indexedrecords = userElement.getElementsByTagName("indexedrecords")
                                                                        .item(0).getTextContent();

        userAccessor.setUsername(username);
        userAccessor.setPassword(password);
        userAccessor.setFirstName(firstname);
        userAccessor.setLastName(lastname);
        userAccessor.setEmail(email);
        userAccessor.setIndexedRecords(Integer.parseInt(indexedrecords));

        if(userAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the user: " + userAccessor.getUsername());
        }

        return userAccessor;
    }

    public ProjectAccessor parseProjectBlock(Element projectElement) throws Exception {
        System.out.print("Parsing Project Block ------- ");

        ProjectAccessor projectAccessor = new ProjectAccessor();

        String title = projectElement.getElementsByTagName("title").item(0).getTextContent();
        String recordsperimage = projectElement.getElementsByTagName("recordsperimage")
                                                                    .item(0).getTextContent();
        String firstycoord = projectElement.getElementsByTagName("firstycoord")
                                                                    .item(0).getTextContent();
        String recordheight = projectElement.getElementsByTagName("recordheight")
                                                                    .item(0).getTextContent();

        projectAccessor.setTitle(title);
        projectAccessor.setRecordsPerImage(Integer.parseInt(recordsperimage));
        projectAccessor.setRecordHeight(Integer.parseInt(recordheight));
        projectAccessor.setFirstYCoord(Integer.parseInt(firstycoord));

        if(projectAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + projectAccessor.getTitle());
        }

        return projectAccessor;
    }

    public FieldAccessor parseFieldBlock(Element fieldElement, int number) throws Exception {
        System.out.print("  Parsing Field Block ------- ");

        FieldAccessor fieldAccessor = new FieldAccessor();

        String title = fieldElement.getElementsByTagName("title").item(0).getTextContent();
        String xcoord = fieldElement.getElementsByTagName("xcoord").item(0).getTextContent();
        String width = fieldElement.getElementsByTagName("width").item(0).getTextContent();
        String helphtml = fieldElement.getElementsByTagName("helphtml").item(0).getTextContent();

        String knowndata = null;
        if(fieldElement.getElementsByTagName("knowndata").item(0) != null)
            knowndata = fieldElement.getElementsByTagName("knowndata").item(0).getTextContent();

        fieldAccessor.setTitle(title);
        fieldAccessor.setxCoord(Integer.parseInt(xcoord));
        fieldAccessor.setWidth(Integer.parseInt(width));
        fieldAccessor.setHelpHtml(helphtml);
        fieldAccessor.setKnownData(knowndata);
        fieldAccessor.setPosition(number);

        if(fieldAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + fieldAccessor.getTitle());
        }

        return fieldAccessor;
    }

    public ImageAccessor parseImageBlock(Element imageElement) throws Exception {
        System.out.print("  Parsing Image Block ------- ");

        ImageAccessor imageAccessor = new ImageAccessor();

        String file = imageElement.getElementsByTagName("file").item(0).getTextContent();

        imageAccessor.setFile(file);

        if(imageAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + imageAccessor.getFile());
        }

        return imageAccessor;
    }

    public RecordAccessor parseRecordBlock(Element recordElement, int number) throws Exception {
        System.out.print("    Parsing Record Block ------- ");

        RecordAccessor recordAccessor = new RecordAccessor();
        recordAccessor.setPosition(number);
        if(recordAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + recordAccessor.getId());
        }

        return recordAccessor;
    }

    public ValueAccessor parseValueBlock(Element valueElement, int number) throws Exception {
        System.out.print("      Parsing Value Block ------- ");

        ValueAccessor valueAccessor = new ValueAccessor();

        String value = valueElement.getTextContent();
        //String type = valueElement.getElementsByTagName("type").item(0).getTextContent();

        valueAccessor.setValue(value);
        valueAccessor.setType("String");
        valueAccessor.setPosition(number);

        if(valueAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + valueAccessor.getId());
        }

        return valueAccessor;
    }
}
