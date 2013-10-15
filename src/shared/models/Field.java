package shared.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserAccessor: film42
 * Date: 10/13/13
 * Time: 5:16 PM
 */
public class Field {

    private int id;
    private String title;
    private int xCoord;
    private int width;
    private String helpHtml;
    private String knownData;
    private int projectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getHelpHtml() {
        return helpHtml;
    }

    public void setHelpHtml(String helpHtml) {
        this.helpHtml = helpHtml;
    }

    public String getKnownData() {
        return knownData;
    }

    public void setKnownData(String knownData) {
        this.knownData = knownData;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
