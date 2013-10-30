package shared.communication.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/28/13
 * Time: 10:38 PM
 */
@XStreamAlias("project")
public class Project_Res {

    private int id;
    private String title;

    public Project_Res(int id, String title) {
        this.id = id;
        this.title = title;
    }
}