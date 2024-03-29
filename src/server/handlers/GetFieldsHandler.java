package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.FieldAccessor;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.Fields_Param;
import shared.communication.params.Projects_Param;
import shared.communication.responses.Fields_Res;
import shared.communication.responses.Projects_Res;
import shared.models.Field;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/31/13
 * Time: 11:22 PM
 */
public class GetFieldsHandler extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            Fields_Param fieldsParam = Fields_Param.serialize(request);


            UserAccessor userAccessor = UserAccessor.find(fieldsParam.getUsername());

            if(userAccessor == null) {
                writeServerErrorResponse(exchange);
                return;
            } else if(userAccessor.login(fieldsParam.getPassword())) {
                String response;
                Fields_Res fieldsRes = new Fields_Res();

                List<FieldAccessor> fieldAccessorList;
                ProjectAccessor projectAccessor;
                if(fieldsParam.getProjectId() == -1) {
                    fieldAccessorList = FieldAccessor.all();
                } else {
                    // if there find a project
                    projectAccessor = ProjectAccessor.find(fieldsParam.getProjectId());
                    if(projectAccessor == null) {
                        writeServerErrorResponse(exchange);
                        return;
                    }
                    // Get the projects fields
                    fieldAccessorList = projectAccessor.getFields();
                }

                for(int i = 0; i < fieldAccessorList.size(); i++) {
                    Field field = fieldAccessorList.get(i).getModel();
                    fieldsRes.addField(field, field.getPosition());
                }

                response = fieldsRes.toXML();
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