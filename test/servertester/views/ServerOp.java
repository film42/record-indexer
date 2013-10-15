package servertester.views;

public enum ServerOp {
	VALIDATE_USER("Validate UserAccessor"),	// Username, Password (String, String)
	GET_PROJECTS("Get Projects_Res"), // Username, Password (String, String)
	GET_SAMPLE_IMAGE("Get Sample ImageAccessor"), // Username, Password, ProjectAccessor (String, String, int)
	DOWNLOAD_BATCH("Download DownloadBatch_Res"), // Username, Password, ProjectAccessor (String, String, int)
	GET_FIELDS("Get Fields_Param"), // Username, Password, ProjectAccessor(-1) (String, String, int)
	SUBMIT_BATCH("Submit DownloadBatch_Res"), // Username, Password, DownloadBatch_Res, Values (String, String, int, String[])
	SEARCH("Search_Res");	// Username, Password, ProjectAccessor, Fields_Param, Values (int, int[], String[])
	
	private final String _description;

	private ServerOp(String description) {
		_description = description;
	}

	@Override
	public String toString() {
		return _description;
	}

}

