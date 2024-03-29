package server.handlers.common;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/29/13
 * Time: 8:18 PM
 */
public class BaseHanlder {

    protected static final String NOT_AUTHORIZED =
            "<error><authenticated>false</authenticated></error>";
    protected static final String INTERNAL_ERROR = "<error>Failed</error>";

    protected String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader =  new BufferedReader(
                new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { bufferedReader.close(); } catch (IOException e) {
                e.printStackTrace(); }
        }

        return stringBuilder.toString();
    }

    protected void writeReponse(int status, HttpExchange exchange, String response)
                                                                                throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    protected void writeSuccessResponse(HttpExchange exchange, String response) throws IOException {
        writeReponse(200, exchange, response);
    }

    protected void writeServerErrorResponse(HttpExchange exchange) throws IOException {
        writeReponse(500, exchange, INTERNAL_ERROR);
    }

    protected void writeBadAuthenticationResponse(HttpExchange exchange) throws IOException {
        writeReponse(401, exchange, NOT_AUTHORIZED);
    }

    protected void writeFileResponse(HttpExchange exchange, File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            writeServerErrorResponse(exchange);
            return;
        }
        OutputStream outputStream = exchange.getResponseBody();

        byte[] bufferArray = new byte[512];

        exchange.sendResponseHeaders(200, file.length());

        int c = 0;
        while ((c = fileInputStream.read(bufferArray, 0, bufferArray.length)) > 0) {
            outputStream.write(bufferArray, 0, c);
            outputStream.flush();
        }

        outputStream.close();
        fileInputStream.close();
    }
}
