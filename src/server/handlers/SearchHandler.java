package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.Search_Param;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.ValidateUser_Res;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/1/13
 * Time: 4:33 PM
 */
public class SearchHandler extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            Search_Param searchParam = Search_Param.serialize(request);

            UserAccessor userAccessor = UserAccessor.find(searchParam.getUsername());

            if(userAccessor == null) {
                writeBadAuthenticationResponse(exchange);
                return;
            } else if(userAccessor.login(searchParam.getPassword())) {

                // Find fields

                String response;
                ValidateUser_Res validateUserRes;
                validateUserRes = new ValidateUser_Res(true, userAccessor.getModel());
                response = validateUserRes.toXML();

                writeSuccessResponse(exchange, response);
                return;
            }

            writeBadAuthenticationResponse(exchange);

        }
    };

    public HttpHandler getHandler() {
        return handler;
    }
}