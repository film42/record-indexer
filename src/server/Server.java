package server;

import com.sun.net.httpserver.*;

import com.sun.org.apache.xml.internal.security.Init;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import server.db.*;
import server.db.common.Database;
import server.errors.ServerException;
import server.handlers.*;
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

    private static int SERVER_LISTENING_PORT = 8090;
    private static int MAX_WAITING_CONNECTIONS = 10;
    private static String HOST = "localhost";

    private HttpServer server;

    public void run(String host, int port) throws ServerException {

        /* ***********************************
                   Init Server Vars
         *********************************** */
        HOST = host;
        SERVER_LISTENING_PORT = port;

        Database.init(Database.PRODUCTION_MODE);

        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_LISTENING_PORT),
                                       MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.setExecutor(null);

        server.createContext("/getProjects",  new GetProjectsHandler().getHandler());
        server.createContext("/getSampleImage", new GetSampleImageHandler().getHandler());
        server.createContext("/validateUser", new ValidateUserHandler().getHandler());
        server.createContext("/downloadBatch", new DownloadBatchHandler().getHandler());
        server.createContext("/getFields",  new GetFieldsHandler().getHandler());
        server.createContext("/submitBatch",  new SubmitBatchHandler().getHandler());
        server.createContext("/search",  new SearchHandler().getHandler());
        server.createContext("/",  new StaticsHandler().getHandler());

        System.out.println("Starting server on port: " + SERVER_LISTENING_PORT);
        server.start();
    }


    public static void main(String[] args) throws ServerException {

       new Server().run(args[0], Integer.parseInt(args[1]));

    }
}
