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

    // TODO: Make this return the string responses desired
    // TODO: Actually copy fies on import, and routes for those
    // TODO: Test more of the DAs and Server Classes if possible
    // TODO: Implement a logger
    // TODO: Search and Submit
    // TODO: Make sure a user is removed when you do "submitBatch"

    private Communicator communicator = new Communicator("http://localhost:8090/");
    
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

        String output = String.format("TRUE\n%s\n%s\n%d", validateUserRes.getFirstName(),
                                                        validateUserRes.getLastName(),
                                                        validateUserRes.getIndexedRecords());
        getView().setResponse(output);
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

        getView().setResponse(projectsRes.toXML());
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

        getView().setResponse(sampleImageRes.toXML());
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

        getView().setResponse(downloadBatchRes.toXML());
    }
    
    private void getFields() {
        Fields_Param fieldsParam = new Fields_Param();

        String username = getView().getParameterValues()[0];
        String password = getView().getParameterValues()[1];
        String projectId = getView().getParameterValues()[2];

        fieldsParam.setUsername(username);
        fieldsParam.setPassword(password);
        fieldsParam.setProjectId(Integer.parseInt(projectId));

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

        getView().setResponse(fieldsRes.toXML());
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

        getView().setResponse(submitBatchRes.toXML());
    }

    private void search() {
    }

    /* ******************************
                HELPERS
    ******************************* */
    private List<Value> parseValueSet(String valueSet) {
        List<Value> valueList = new ArrayList<Value>();
        String[] values = valueSet.split(",");

        for(String strValue : values) {
            Value value = new Value();
            value.setValue(strValue);
            value.setType("STRING");
            valueList.add(value);
        }

        return valueList;
    }


    private List<List<Value> > parseRecordsList(String data) {

        List<List<Value> > recordList = new ArrayList<List<Value> >();

        for(String valueSet : data.split(";")) {
            //if(valueSet.split(",").length != expectedValuesPerRow)
            //    throw new ServerException("Incorrect values list (records) format");

            List<Value> valueList = parseValueSet(valueSet);
            recordList.add(valueList);
        }

        return recordList;
    }

}

