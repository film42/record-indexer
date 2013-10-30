package server.db.importer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.db.*;
import server.db.common.Database;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/24/13
 * Time: 2:34 PM
 */
public class Importer {

    public static void main(String[] args) throws Exception {
        Database.init(Database.PRODUCTION_MODE);

        new Importer().run();
    }

    public void run() throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        File xmlFile = new File("demo/indexer_data/Records/Records.xml");
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

                parseFieldBlock(fieldElement);
            }

            /**
             * Images
             */
            NodeList imagesList = projectElement.getElementsByTagName("image");
            for(int j = 0; j < imagesList.getLength(); j++) {
                Element imageElement = (Element)imagesList.item(j);

                parseImageBlock(imageElement);

                /**
                 * Records
                 */
                NodeList recordList = imageElement.getElementsByTagName("record");
                for(int k = 0; k < recordList.getLength(); k++) {
                    Element recordElement = (Element)recordList.item(k);

                    parseRecordBlock(recordElement);

                    /**
                     * Values
                     */
                    NodeList valueList = recordElement.getElementsByTagName("value");
                    for(int l = 0; l < valueList.getLength(); l++) {
                        Element valueElement = (Element)valueList.item(l);

                        parseValueBlock(valueElement);
                    }
                }
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
        String indexedrecords = userElement.getElementsByTagName("indexedrecords").item(0).getTextContent();

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
        String recordsperimage = projectElement.getElementsByTagName("recordsperimage").item(0).getTextContent();
        String firstycoord = projectElement.getElementsByTagName("firstycoord").item(0).getTextContent();
        String recordheight = projectElement.getElementsByTagName("recordheight").item(0).getTextContent();

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

    public FieldAccessor parseFieldBlock(Element fieldElement) throws Exception {
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

    public RecordAccessor parseRecordBlock(Element recordElement) throws Exception {
        System.out.print("    Parsing Record Block ------- ");

        RecordAccessor recordAccessor = new RecordAccessor();
        if(recordAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + recordAccessor.getId());
        }

        return recordAccessor;
    }

    public ValueAccessor parseValueBlock(Element valueElement) throws Exception {
        System.out.print("      Parsing Value Block ------- ");

        ValueAccessor valueAccessor = new ValueAccessor();

        String value = valueElement.getTextContent();
        //String type = valueElement.getElementsByTagName("type").item(0).getTextContent();

        valueAccessor.setValue(value);
        valueAccessor.setType("String");

        if(valueAccessor.save()) System.out.println("Success!");
        else {
            System.out.println("ERROR!");
            throw new Exception("Error saving the: " + valueAccessor.getId());
        }

        return valueAccessor;
    }
}
