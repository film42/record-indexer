package server.errors;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/25/13
 * Time: 6:40 PM
 *
 * This is our generic server exception. Iteration upon this
 * for specifics is no doubt welcomed.
 *
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {

    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable throwable) {
        super(throwable);
    }

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
