package server;

import com.sun.net.httpserver.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import server.db.*;
import server.db.common.Database;
import server.errors.ServerException;
import server.handlers.DownloadBatchHandler;
import server.handlers.GetProjectsHandler;
import server.handlers.GetSampleImageHandler;
import server.handlers.ValidateUserHandler;
import shared.communication.params.DownloadBatch_Param;
import shared.communication.params.Projects_Param;
import shared.communication.params.SampleImage_Param;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.DownloadBatch_Res;
import shared.communication.responses.Projects_Res;
import shared.communication.responses.SampleImage_Res;
import shared.communication.responses.ValidateUser_Res;
import shared.models.Project;
import shared.models.User;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/24/13
 * Time: 2:35 PM
 */
public class Server {

    public void scratch() {
        ValueAccessor valueAccessor = ValueAccessor.find(3);

        List<ValueAccessor> valueAccessorList = ValueAccessor.all();

        for(ValueAccessor vA : valueAccessorList) {
            System.out.println(vA.getId());
        }

        UserAccessor userAccessor1 = UserAccessor.find("film42");
        UserAccessor userAccessor2 = UserAccessor.findByProjectId(1);

        System.out.println(userAccessor2.getProjectId());

        userAccessor1.setEmail("poop@rocks.com");

        System.out.println(userAccessor1.save());

        System.out.println(userAccessor1.getFirstName());


        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        UserAccessor uA = ProjectAccessor.find(14).getUser().getImage().getUser();

        uA.setEmail("test@example.com");
        System.out.println(uA.save());

        System.out.println(ProjectAccessor.find(14).getUser().getImage().getUser().getEmail());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        System.out.println(UserAccessor.find("film42").getImage().getFile());

        UserAccessor userAccessor4 = UserAccessor.find("film42");
        //ProjectAccessor projectAccessor1 = userAccessor4.getImage();
        //projectAccessor1.setFirstYCoord(10000);
        //System.out.println(projectAccessor1.getFirstYCoord());
        System.out.println(userAccessor4.getImage().getFile());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        System.out.println(ProjectAccessor.all().size());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        FieldAccessor fieldAccessor = new FieldAccessor(null);

        fieldAccessor.setKnownData("some path");
        fieldAccessor.setTitle("First Name");
        fieldAccessor.setWidth(200);
        fieldAccessor.setHelpHtml("help.html");
        fieldAccessor.setxCoord(450);

        System.out.println(fieldAccessor.toSQL(false));
        System.out.println(fieldAccessor.save());
        System.out.println(fieldAccessor.getId());
        fieldAccessor.setHelpHtml("some other ting.html");
        System.out.println(fieldAccessor.save());

        FieldAccessor fieldAccessor2 = FieldAccessor.find(fieldAccessor.getId());

        System.out.println(fieldAccessor2.getHelpHtml());
        System.out.println(fieldAccessor2.save());

        System.out.println(FieldAccessor.all().size());

        System.out.println("Getting field accessor (project id): " + FieldAccessor.find(1).getProjectId());
        System.out.println(FieldAccessor.find(1).getProject().getTitle());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        ImageAccessor imageAccessor = new ImageAccessor();
        imageAccessor.setFile("/tmp/base/what????");
        System.out.println(imageAccessor.getProjectId());

        System.out.println(imageAccessor.toSQL(false));
        System.out.println(imageAccessor.save());
        System.out.println(imageAccessor.getId());

        ImageAccessor imageAccessor1 = ImageAccessor.find(imageAccessor.getId());

        System.out.println(imageAccessor1.getFile());
        imageAccessor1.setProjectId(14);
        System.out.println(imageAccessor1.save());
        System.out.println(imageAccessor1.getProject().getTitle());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

        RecordAccessor recordAccessor = new RecordAccessor();
        recordAccessor.setImageId(3);

        System.out.println(recordAccessor.toSQL(false));
        System.out.println(recordAccessor.save());
        System.out.println(recordAccessor.getId());

        RecordAccessor recordAccessor1 = RecordAccessor.find(recordAccessor.getId());
        recordAccessor1.setId(8);
        System.out.println(recordAccessor1.save());

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    }

    public void dataLoaded() {
        UserAccessor userAccessor = UserAccessor.find("sheila");

        System.out.println(userAccessor.getImage().getUser().getEmail());

        XStream xstream = new XStream(new StaxDriver());

        String response = xstream.toXML(userAccessor);

        System.out.println(response);


    }


    private static final int SERVER_PORT_NUMBER = 8090;
    private static final int MAX_WAITING_CONNECTIONS = 10;
    private static final String HOST = "localhost";
    private static String BASE_URL = "http://"+HOST+"/my_base/";

    private HttpServer server;

    public void testingServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                                       MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.setExecutor(null);

        server.createContext("/getProjects",  new GetProjectsHandler().getHandler());
        server.createContext("/getSampleImage", new GetSampleImageHandler().getHandler());
        server.createContext("/validateUser", new ValidateUserHandler().getHandler());
        server.createContext("/downloadBatch", new DownloadBatchHandler().getHandler());

        System.out.println("Starting server on port: " + SERVER_PORT_NUMBER);
        server.start();
    }

    public void run() {
        try {

            Database.init(Database.PRODUCTION_MODE);
            //dataLoaded();
            testingServer();

        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }
}
