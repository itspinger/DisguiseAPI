package net.pinger.disguise.exception;

public class NameFormatException extends RuntimeException {

    /**
     * This constructor creates a new format exception, which indicates an error
     * has occurred when trying to change name of a player.
     *
     * @param message the specific message of this error
     */

    public NameFormatException(String message) {
        super(message);
    }

    /**
     * This constructor creates a new format exception, along with a throwable
     * which stack is supposed to be output to the logger. If you don't have
     * a {@link Throwable} alongside the message, use {@link #NameFormatException(String)}
     * contractor instead.
     *
     * @param message the message to send
     * @param throwable the throwable
     */

    public NameFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
