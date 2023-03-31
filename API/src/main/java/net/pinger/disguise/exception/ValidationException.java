package net.pinger.disguise.exception;

public class ValidationException extends RuntimeException {

    /**
     * This constructor creates a new {@link ValidationException}
     * with the specified error message.
     *
     * @param message the error message
     */

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
