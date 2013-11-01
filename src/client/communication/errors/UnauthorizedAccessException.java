package client.communication.errors;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/31/13
 * Time: 9:32 PM
 */
@SuppressWarnings("serial")
public class UnauthorizedAccessException extends Exception {

    public UnauthorizedAccessException() {
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(Throwable throwable) {
        super(throwable);
    }

    public UnauthorizedAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
