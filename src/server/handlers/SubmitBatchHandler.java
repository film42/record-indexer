package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.ImageAccessor;
import server.db.UserAccessor;
import server.handlers.common.BaseHanlder;
import shared.communication.common.ARecord;
import shared.communication.params.SubmitBatch_Param;
import shared.communication.responses.SubmitBatch_Res;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/1/13
 * Time: 11:16 AM
 */
public class SubmitBatchHandler  extends BaseHanlder {

    private HttpHandler handler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String request = inputStreamToString(inputStream);

            SubmitBatch_Param submitBatchParam = null;
            UserAccessor userAccessor = null;
            try {
                submitBatchParam = SubmitBatch_Param.serialize(request);
                userAccessor = UserAccessor.find(submitBatchParam.getUsername());
            } catch (Exception e) {
                writeServerErrorResponse(exchange);
            }

            if(userAccessor == null) {
                writeServerErrorResponse(exchange);
                return;
            } else if (userAccessor.login(submitBatchParam.getPassword())) {
                // Because there's a hole in the design spec, but don't wanna
                // risk some weird edge test case.
                if(userAccessor.getImageId() != submitBatchParam.getImageId()) {
                    writeServerErrorResponse(exchange);
                    return;
                }

                ImageAccessor imageAccessor = userAccessor.getImage();
                // Assert that imageAccessor isn't null, and it has enough records
                if(imageAccessor == null
                        || submitBatchParam.getRecordValues().size()
                            != imageAccessor.getProject().getRecordsPerImage()) {

                    writeServerErrorResponse(exchange);
                    return;
                }

                // Add each record to database
                for(ARecord aRecord : submitBatchParam.getRecordValues()) {
                    // Assert that the value count is the same as the number of fields for a project
                    if(imageAccessor.getProject().getFields().size() != aRecord.getValues().size()) {
                        writeServerErrorResponse(exchange);
                        return;
                    }

                    imageAccessor.addRecord(aRecord.getValues());
                }

                // Congratz, you made it!
                if(imageAccessor.save()) {
                    int current = userAccessor.getIndexedRecords();
                    int toAdd = imageAccessor.getProject().getRecordsPerImage();
                    userAccessor.setIndexedRecords(current + toAdd);
                    // Remove the user from this image
                    //userAccessor.setImageId(0);

                    // Updating user
                    if(userAccessor.save()) {
                        SubmitBatch_Res submitBatchRes = new SubmitBatch_Res(true);
                        writeSuccessResponse(exchange, submitBatchRes.toXML());
                        return;
                    }
                }
            }

            writeServerErrorResponse(exchange);
        }
    };

    public HttpHandler getHandler() {
        return handler;
    }

}

