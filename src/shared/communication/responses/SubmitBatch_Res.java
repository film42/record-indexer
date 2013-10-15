package shared.communication.responses;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/14/13
 * Time: 8:21 PM
 */
public class SubmitBatch_Res {

    private boolean success;

    public SubmitBatch_Res(boolean success) {
        this.success = success;
    }

    public boolean wasSuccess() {
        return success;
    }
}
