package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.db.*;
import server.handlers.common.BaseHanlder;
import shared.communication.common.Tuple;
import shared.communication.params.Search_Param;
import shared.communication.params.ValidateUser_Param;
import shared.communication.responses.Search_Res;
import shared.communication.responses.ValidateUser_Res;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 11/1/13
 * Time: 4:33 PM
 */
public class SearchHandler extends BaseHanlder {

    private List<Tuple> search(int fieldId, Set<String> words) {
        // Get the Field
        FieldAccessor fieldAccessor = FieldAccessor.find(fieldId);
        // Return if no field exists
        if(fieldAccessor == null) return new ArrayList<Tuple>();
        // Get the fields associated values
        List<ValueAccessor> valueAccessorList = fieldAccessor.getColumnValues();
        // Parse from words list
        List<ValueAccessor> matchingValueAccessorsList = new ArrayList<ValueAccessor>();
        for(ValueAccessor valueAccessor : valueAccessorList) {
            for(String word : words) {
                word = word.toLowerCase();
                // If both exactly match while lowercase
                if(word.equals(valueAccessor.getValue().toLowerCase())) {
                    matchingValueAccessorsList.add(valueAccessor);
                }
            }
        }
        // Create Tuple with matching data
        List<Tuple> tupleList = new ArrayList<Tuple>();
        for(ValueAccessor valueAccessor : matchingValueAccessorsList) {
            RecordAccessor recordAccessor = valueAccessor.getRecord();
            ImageAccessor imageAccessor = recordAccessor.getImage();

            Tuple tuple = new Tuple();
            tuple.setBatchId(imageAccessor.getId());
            tuple.setImageUrl(imageAccessor.getFile());
            tuple.setRecordNumber(recordAccessor.getPosition());
            tuple.setFieldId(fieldId);
            tupleList.add(tuple);
        }

        return tupleList;
    }

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

                List<Tuple> foundTupleList = new ArrayList<Tuple>();

                // TODO: Make this a set, and strings too
                for(int id : searchParam.getFieldsIds()) {
                    // Look up each id, and return a list of tuples
                    foundTupleList.addAll(search(id, searchParam.getSearchParams()));
                }

                Search_Res searchRes = new Search_Res(foundTupleList);

                writeSuccessResponse(exchange, searchRes.toXML());
                return;
            }

            writeBadAuthenticationResponse(exchange);

        }
    };

    public HttpHandler getHandler() {
        return handler;
    }
}