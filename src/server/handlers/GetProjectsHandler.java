package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.Projects_Param;
import shared.communication.responses.Projects_Res;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 8:24 PM
 */
public class GetProjectsHandler extends BaseHanlder {

    private HttpHandler getProjectsHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            Projects_Param projectsParam = Projects_Param.serialize(request);

            UserAccessor userAccessor = UserAccessor.find(projectsParam.getUsername());

            String response;
            if(userAccessor == null) {
                response = INTERNAL_ERROR;
            } else if(userAccessor.getPassword().equals(projectsParam.getPassword())) {
                Projects_Res projectsRes = new Projects_Res();

                List<ProjectAccessor> projectAccessors = ProjectAccessor.all();
                if(projectAccessors.size() == 0) {
                    writeServerErrorResponse(exchange, INTERNAL_ERROR);
                    return;
                }

                for(ProjectAccessor pA : projectAccessors) {
                    projectsRes.addProject(pA.getId(), pA.getTitle());
                }

                response = projectsRes.toXML();
            } else {
                response = NOT_AUTHORIZED;
            }

            writeSuccessResponse(exchange, response);
        }
    };

    public HttpHandler getHandler() {

        return getProjectsHandler;
    }

}
