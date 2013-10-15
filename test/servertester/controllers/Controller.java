package servertester.controllers;

import java.util.*;

import servertester.views.*;

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
			paramNames.add("DownloadBatch_Res");
			paramNames.add("RecordAccessor Values");
			break;
		case SEARCH:
			paramNames.add("Fields_Param");
			paramNames.add("Search_Res Values");
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
	
	private void validateUser() {
	}
	
	private void getProjects() {
	}
	
	private void getSampleImage() {
	}
	
	private void downloadBatch() {
	}
	
	private void getFields() {
	}
	
	private void submitBatch() {
	}
	
	private void search() {
	}

}

