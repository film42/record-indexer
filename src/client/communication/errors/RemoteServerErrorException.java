package client.communication.errors;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 10/31/13
 * Time: 9:34 PM
 */
public class RemoteServerErrorException extends Exception {

    public RemoteServerErrorException() {
    }

    public RemoteServerErrorException(String message) {
        super(message);
    }

    public RemoteServerErrorException(Throwable throwable) {
        super(throwable);
    }

    public RemoteServerErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
