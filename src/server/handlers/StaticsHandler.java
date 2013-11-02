package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.ValidateUser_Res;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/2/13
 * Time: 1:45 PM
 */
public class StaticsHandler extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String prefix = "db/statics";

            // Get the uri
            String uri = exchange.getRequestURI().toString();
            File staticFile;
            try {
                staticFile = new File(prefix+uri);
            } catch (Exception e) {
                writeServerErrorResponse(exchange);
                return;
            }

            writeFileResponse(exchange, staticFile);
        }
    };

    public HttpHandler getHandler() {
        return handler;
    }
}
