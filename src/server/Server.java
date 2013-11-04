package server;

import com.sun.net.httpserver.HttpServer;
import server.db.common.Database;
import server.errors.ServerException;
import server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/24/13
 * Time: 2:35 PM
 */
public class Server {

    private static int SERVER_LISTENING_PORT = 8090;
    private static int MAX_WAITING_CONNECTIONS = 10;

    private HttpServer server;

    public void run(int port) throws ServerException {

        /* ***********************************
                   Init Server Args
         *********************************** */
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
        new Server().run(Integer.parseInt(args[0]));
    }
}
