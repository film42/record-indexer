package shared.communication.responses;

import shared.communication.common.Fields;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:08 PM
 */
public class Fields_Res {

    private List<Fields> fields;

    public void addField(int id, int number,
                         String title, String helpUrl,
                         int xCoord, int pixelWidth) {

        Fields response = null;
        response = new Fields(id, number, title, helpUrl, xCoord, pixelWidth);
        fields.add(response);

    }

    /**
     *
     * @return A List of common.Field for params
     */
    public List<Fields> getFields() {
        return fields;
    }
}
