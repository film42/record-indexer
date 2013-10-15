package shared.communication.params;

import shared.communication.common.Fields;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:58 PM
 */
public class SubmitBatch_Param {

    private String username;
    private String password;
    private int id;
    private List<Fields> fields;

    public void addField(int id, int number,
                         String title, String helpUrl,
                         int xCoord, int pixelWidth) {

        Fields response = null;
        response = new Fields(id, number, title, helpUrl, xCoord, pixelWidth);
        fields.add(response);

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    /**
     *
     * @return A List of common.Field for params
     */
    public List<Fields> getFields() {
        return fields;
    }
}
