package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.ImageAccessor;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.SampleImage_Param;
import shared.communication.responses.SampleImage_Res;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 8:25 PM
 */
public class GetSampleImageHandler extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            SampleImage_Param sampleImageParam = SampleImage_Param.serialize(request);

            UserAccessor userAccessor = UserAccessor.find(sampleImageParam.getUsername());
            ProjectAccessor projectAccessor = ProjectAccessor.find(sampleImageParam.getProjectId());

            if(userAccessor == null || projectAccessor == null) {
                writeServerErrorResponse(exchange);
                return;
            } else if (userAccessor.login(sampleImageParam.getPassword())) {
                String response = "";

                List<ImageAccessor> imageAccessor = projectAccessor.getImages();

                if(imageAccessor.size() > 0) {
                    SampleImage_Res sampleImageRes = new SampleImage_Res(
                                                            imageAccessor.get(0).getModel());
                    response = sampleImageRes.toXML();

                    writeSuccessResponse(exchange, response);
                    return;
                }
            }

            writeServerErrorResponse(exchange);
        }
    };

    public HttpHandler getHandler() {
        return handler;
    }

}
