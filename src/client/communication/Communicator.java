package client.communication;

import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import client.communication.modules.HttpClient;
import shared.communication.params.*;
import shared.communication.responses.*;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:34 PM
 */
public class Communicator {

    public Communicator(String serverPath) {
        this.serverPath = serverPath;
    }

    private String serverPath;

    public ValidateUser_Res validateUser(ValidateUser_Param user)
            throws UnauthorizedAccessException, RemoteServerErrorException {

        String resource = "validateUser/";
        String response = HttpClient.post(serverPath+resource, user.toXML());

        if(response == null)
            return null;

        return ValidateUser_Res.serialize(response);
    }

    public Projects_Res getProjects(Projects_Param projects)
            throws UnauthorizedAccessException, RemoteServerErrorException {

        String resource = "getProjects/";
        String response = HttpClient.post(serverPath + resource, projects.toXML());

        if(response == null)
            return null;

        return Projects_Res.serialize(response);
    }

    public SampleImage_Res getSampleImage(SampleImage_Param sampleImage)
            throws UnauthorizedAccessException, RemoteServerErrorException {

        String resource = "getSampleImage/";
        String response = HttpClient.post(serverPath+resource, sampleImage.toXML());

        if(response == null)
            return null;

        return SampleImage_Res.serialize(response);
    }

    public DownloadBatch_Res downloadBatch(DownloadBatch_Param downloadBatch)
            throws UnauthorizedAccessException, RemoteServerErrorException {
        String resource = "downloadBatch/";
        String response = HttpClient.post(serverPath+resource, downloadBatch.toXML());

        if(response == null)
            return null;

        return DownloadBatch_Res.serialize(response);
    }

    public SubmitBatch_Res submitBatch(SubmitBatch_Param submitBatch) {
        return null;
    }

    public Fields_Res getFields(Fields_Param fields) {
        return null;
    }

    public Search_Res search(Search_Param search) {
        return null;
    }

}
