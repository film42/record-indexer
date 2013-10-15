package shared.communication.responses;


/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 7:10 PM
 */
public class ValidateUser_Res {

    private boolean autenticated;
    private String firstName;
    private String lastName;
    private int indexedRecords;

    public boolean isAutenticated() {
        return autenticated;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getIndexedRecords() {
        return indexedRecords;
    }
}
