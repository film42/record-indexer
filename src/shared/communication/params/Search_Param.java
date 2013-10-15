package shared.communication.params;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:09 PM
 */
public class Search_Param {

    private String username;
    private String password;
    private List<Integer> fieldsIds;
    private List<String> searchParams;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addSearchParam(String param) {
        searchParams.add(param);
    }

    public void addFieldId(int fieldId) {
        fieldsIds.add(fieldId);
    }

    public List<Integer> getFieldsIds() {
        return fieldsIds;
    }

    public List<String> getSearchParams() {
        return searchParams;
    }
}
