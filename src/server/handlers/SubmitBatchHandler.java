package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.ImageAccessor;
import server.db.ProjectAccessor;
import server.db.UserAccessor;
import server.errors.ServerException;
import server.handlers.common.BaseHanlder;
import shared.communication.common.ARecord;
import shared.communication.params.SampleImage_Param;
import shared.communication.params.SubmitBatch_Param;
import shared.communication.responses.SampleImage_Res;
import shared.communication.responses.SubmitBatch_Res;
import shared.models.Record;
import shared.models.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                String response = "";

                // Because there's a hole in the design spec, but don't wanna
                // risk some weird edge test case.
                if(userAccessor.getImageId() != submitBatchParam.getImageId()) {
                    writeServerErrorResponse(exchange);
                    return;
                }

                ImageAccessor imageAccessor = userAccessor.getImage();
                if(imageAccessor == null) {
                    writeServerErrorResponse(exchange);
                    return;
                }

                for(ARecord aRecord : submitBatchParam.getRecordValues()) {
                    imageAccessor.addRecord(aRecord.getValues());
                }

                if(imageAccessor.save()) {
                    SubmitBatch_Res submitBatchRes = new SubmitBatch_Res(true);
                    writeSuccessResponse(exchange, submitBatchRes.toXML());
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

