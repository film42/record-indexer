package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.FieldAccessor;
import server.db.ImageAccessor;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.params.DownloadBatch_Param;
import shared.communication.responses.DownloadBatch_Res;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 8:27 PM
 */
public class DownloadBatchHandler extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            DownloadBatch_Param downloadBatchParam = DownloadBatch_Param.serialize(request);

            UserAccessor userAccessor = UserAccessor.find(downloadBatchParam.getUsername());

            ImageAccessor imageAccessor;
            List<ImageAccessor> availableList;
            if(userAccessor != null) {
                imageAccessor = userAccessor.getImage();
                availableList = ImageAccessor.allAvailible(downloadBatchParam.getProjectId());
            } else {
                writeServerErrorResponse(exchange);
                return;
            }

            if(userAccessor == null || availableList.size() == 0) {
                writeServerErrorResponse(exchange);
            } else if (userAccessor.login(downloadBatchParam.getPassword())) {
                // TODO: Make this not a bug anymore, ok?
                ImageAccessor assignImage = availableList.get(0);
                userAccessor.setImageId(assignImage.getId());

                // Save the new user information
                if(userAccessor.save()) {
                    String response;
                    ProjectAccessor projectAccessor = assignImage.getProject();
                    List<FieldAccessor> fieldAccessorList = projectAccessor.getFields();


                    DownloadBatch_Res downloadBatchRes = new DownloadBatch_Res(assignImage.getModel(),
                            projectAccessor.getModel(), fieldAccessorList.size(), 0);

                    // Add each field
                    for(int i = 0; i < fieldAccessorList.size(); i++)
                        downloadBatchRes.addField(fieldAccessorList.get(i).getModel(), (i+1));

                    response = downloadBatchRes.toXML();
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
