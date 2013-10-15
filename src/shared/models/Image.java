package shared.models;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:16 PM
 */
public class Image {

    private int id;
    private String file;
    private int projectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

}
