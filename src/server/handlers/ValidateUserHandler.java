package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.ValidateUser_Res;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 8:17 PM
 */
public class ValidateUserHandler extends BaseHanlder {

    private HttpHandler validateUserHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            ValidateUser_Param validateUserParam = ValidateUser_Param.serialize(request);

            UserAccessor userAccessor = UserAccessor.find(validateUserParam.getUsername());

            if(userAccessor == null) {
                writeBadAuthenticationResponse(exchange, NOT_AUTHORIZED);
                return;
            } else if(userAccessor.login(validateUserParam.getPassword())) {
                String response;
                ValidateUser_Res validateUserRes;
                validateUserRes = new ValidateUser_Res(true, userAccessor.getModel());
                response = validateUserRes.toXML();

                writeSuccessResponse(exchange, response);
                return;
            }

            writeBadAuthenticationResponse(exchange, NOT_AUTHORIZED);

        }
    };

    public HttpHandler getHandler() {
        return validateUserHandler;
    }
}
