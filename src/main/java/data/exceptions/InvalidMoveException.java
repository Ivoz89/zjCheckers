package data.exceptions;

/**
 * Created by Ivo on 06/05/15.
 */
public class InvalidMoveException extends Exception {

    public InvalidMoveException() {
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}
