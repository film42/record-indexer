package servertester.controllers;

import java.util.*;

import client.communication.Communicator;
import client.communication.errors.RemoteServerErrorException;
import client.communication.errors.UnauthorizedAccessException;
import client.communication.modules.HttpClient;
import server.errors.ServerException;
import server.handlers.GetFieldsHandler;
import servertester.views.*;
import shared.communication.params.*;
import shared.communication.responses.*;
import shared.models.Value;

public class Controller implements IController {

    private IView _view;
    
    public Controller() {
        return;
    }
    
    public IView getView() {
        return _view;
    }
    
    public void setView(IView value) {
        _view = value;
    }
    
    // IController methods
    //
    
    @Override
    public void initialize() {
        getView().setHost("localhost");
        getView().setPort("39640");
        operationSelected();
    }

    @Override
    public void operationSelected() {
        ArrayList<String> paramNames = new ArrayList<String>();
        paramNames.add("UserAccessor");
        paramNames.add("Password");
        
        switch (getView().getOperation()) {
        case VALIDATE_USER:
            break;
        case GET_PROJECTS:
            break;
        case GET_SAMPLE_IMAGE:
            paramNames.add("ProjectAccessor");
            break;
        case DOWNLOAD_BATCH:
            paramNames.add("ProjectAccessor");
            break;
        case GET_FIELDS:
            paramNames.add("ProjectAccessor");
            break;
        case SUBMIT_BATCH:
            paramNames.add("DownloadBatch");
            paramNames.add("RecordAccessor Values");
            break;
        case SEARCH:
            paramNames.add("Fields_Param");
            paramNames.add("Search Values");
            break;
        default:
            assert false;
            break;
        }
        
        getView().setRequest("");
        getView().setResponse("");
        getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
    }

    @Override
    public void executeOperation() {
        switch (getView().getOperation()) {
        case VALIDATE_USER:
            validateUser();
            break;
        case GET_PROJECTS:
            getProjects();
            break;
        case GET_SAMPLE_IMAGE:
            getSampleImage();
            break;
        case DOWNLOAD_BATCH:
            downloadBatch();
            break;
        case GET_FIELDS:
            getFields();
            break;
        case SUBMIT_BATCH:
            submitBatch();
            break;
        case SEARCH:
            search();
            break;
        default:
            assert false;
            break;
        }
    }

    // TODO: Test more of the DAs and Server Classes if possible
    // TODO: Implement a logger
    // TODO: UNCOMMENT: Make sure a user is removed when you do "submitBatch"
    // TODO: Uncomment the download batch debug thing
    // TODO: Test case for valuesColumn and getRecord, look for more

    private String serverPath = "http://"+getView().getHost()+":"+getView().getPort()+"/";
    private Communicator communicator = new Communicator(serverPath);
    
    private void validateUser() {
        ValidateUser_Param validateUserParam = new ValidateUser_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];

        validateUserParam.setUsername(username);
        validateUserParam.setPassword(password);

        getView().setRequest(validateUserParam.toXML());

        ValidateUser_Res validateUserRes = null;

        try {
            validateUserRes = communicator.validateUser(validateUserParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FALSE");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(validateUserRes.toString());
    }
    
    private void getProjects() {
        Projects_Param projectsParam = new Projects_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];

        projectsParam.setUsername(username);
        projectsParam.setPassword(password);

        getView().setRequest(projectsParam.toXML());

        Projects_Res projectsRes = null;

        try {
            projectsRes = communicator.getProjects(projectsParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(projectsRes.toString());
    }
    
    private void getSampleImage() {
        SampleImage_Param sampleImageParam = new SampleImage_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String projectId = getView().getParameterValues()[2];

        sampleImageParam.setUsername(username);
        sampleImageParam.setPassword(password);
        sampleImageParam.setProjectId(Integer.parseInt(projectId));

        SampleImage_Res sampleImageRes = null;
        try {
            sampleImageRes = communicator.getSampleImage(sampleImageParam);

        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(serverPath + sampleImageRes.toString());
    }

    private void downloadBatch() {
        DownloadBatch_Param downloadBatchParam = new DownloadBatch_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String projectId = getView().getParameterValues()[2];

        downloadBatchParam.setUsername(username);
        downloadBatchParam.setPassword(password);
        downloadBatchParam.setProjectId(Integer.parseInt(projectId));

        getView().setRequest(downloadBatchParam.toXML());

        DownloadBatch_Res downloadBatchRes = null;
        try {
            downloadBatchRes = communicator.downloadBatch(downloadBatchParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(downloadBatchRes.toString(serverPath));
    }

    private void submitBatch() {
        SubmitBatch_Param submitBatchParam = new SubmitBatch_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String imageId = getView().getParameterValues()[2];
        String recordValues = getView().getParameterValues()[3];

        submitBatchParam.setUsername(username);
        submitBatchParam.setPassword(password);
        submitBatchParam.setImageId(Integer.parseInt(imageId));

        // Parse Record Params
        for(List<Value> values : parseRecordsList(recordValues)) {
            submitBatchParam.addRecord(values);
        }

        getView().setRequest(submitBatchParam.toXML());

        SubmitBatch_Res submitBatchRes = null;
        try {
            submitBatchRes = communicator.submitBatch(submitBatchParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        // Was success, so:
        getView().setResponse("TRUE");
    }

    private void getFields() {
        Fields_Param fieldsParam = new Fields_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String projectId = getView().getParameterValues()[2];

        fieldsParam.setUsername(username);
        fieldsParam.setPassword(password);
        fieldsParam.setProjectId(Integer.parseInt(projectId));

        getView().setRequest(fieldsParam.toXML());

        Fields_Res fieldsRes = null;
        try {
            fieldsRes = communicator.getFields(fieldsParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(fieldsRes.toString());
    }

    private void search() {
        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String fieldIds = getView().getParameterValues()[2];
        String queryWords = getView().getParameterValues()[3];

        Search_Param searchParam = new Search_Param();
        searchParam.setUsername(username);
        searchParam.setPassword(password);
        // Add FieldIds
        for(String fieldId : parseCommaString(fieldIds)) {
            searchParam.addFieldId(Integer.parseInt(fieldId));
        }
        // Add Query Strings
        for(String query : parseCommaString(queryWords)) {
            searchParam.addSearchParam(query);
        }

        getView().setRequest(searchParam.toXML());

        Search_Res searchRes = null;
        try {
            searchRes = communicator.search(searchParam);
        } catch (UnauthorizedAccessException e) {
            getView().setResponse("FAILED");
            return;
        } catch (RemoteServerErrorException e) {
            getView().setResponse("FAILED");
            return;
        }

        getView().setResponse(searchRes.toString());
    }

    /* ******************************
                HELPERS
    ******************************* */
    // For submitBatch
    private List<Value> parseValueSet(String valueSet) {
        List<Value> valueList = new ArrayList<Value>();
        // Love that -1 for magic hey-oh
        String[] values = valueSet.split(",", -1);

        for(String strValue : values) {
            Value value = new Value();
            value.setValue(strValue);
            value.setType("STRING");
            valueList.add(value);
        }

        return valueList;
    }

    // For submitBatch
    private List<List<Value> > parseRecordsList(String data) {

        List<List<Value> > recordList = new ArrayList<List<Value> >();

        for(String valueSet : data.split(";")) {
            List<Value> valueList = parseValueSet(valueSet);
            recordList.add(valueList);
        }

        return recordList;
    }

    // For search
    private String[] parseCommaString(String stringWithCommas) {
        return stringWithCommas.split(",");
    }

}

