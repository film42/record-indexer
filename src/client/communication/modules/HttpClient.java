package client.communication.modules;

import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/31/13
 * Time: 7:52 PM
 */
public class HttpClient {

    /**
     * Generic HTTP client goodness in one wonderful bundle
     *
     * @param url
     * @param method
     * @param request
     * @return
     * @throws Exception
     */
    private static String request(String url, String method, String request)
            throws RemoteServerErrorException, UnauthorizedAccessException{

        try {
            URL requestURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

            // We can generalize, whatever
            connection.setDoOutput(true);

            connection.setRequestMethod(method);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(request);
            outputStreamWriter.close();

            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    return inputStreamToString(connection.getInputStream());
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    throw new UnauthorizedAccessException();
                default:
                    throw new RemoteServerErrorException();
            }

        } catch (MalformedURLException e) {
            throw new RemoteServerErrorException();
        } catch (IOException e) {
            throw new RemoteServerErrorException();
        }
    }

    public static String get(String url)
            throws UnauthorizedAccessException, RemoteServerErrorException {

        return request(url, "GET", "");
    }

    public static String post(String url, String request)
            throws UnauthorizedAccessException, RemoteServerErrorException {

        return request(url, "POST", request);
    }

    // Copied from server.handlers.common.BaseHandler.java
    private static String inputStreamToString(InputStream inputStream) {
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

        try {
            inputStream.close();
        } catch (IOException e) {

        }
        return stringBuilder.toString();
    }

}
