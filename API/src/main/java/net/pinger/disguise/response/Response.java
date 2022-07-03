package net.pinger.disguise.response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Response<T> {

    /**
     * This enum indicates the response of the request
     */

    public enum ResponseType {
        SUCCESS,
        FAILURE
    }

    private final T response;

    /**
     * This field represents the response type
     * of this response object.
     */

    public final ResponseType type;

    /**
     * This field represents the error message of this object.
     * <p>
     * Note that this method is nullable, so if the {@link #get()} method
     * doesn't return null, this method will.
     */

    public final Throwable error;

    /**
     * This method creates a new response to a certain request.
     *
     * @param response the response if available
     * @param type the type of the response
     * @param error the error, if the response was null
     */

    public Response(@Nullable T response, @Nonnull ResponseType type, @Nullable Throwable error) {
        this.response = response;
        this.type = type;
        this.error = error;
    }

    /**
     * This method creates a new response to a certain request.
     *
     * @param response the response if available
     * @param type the type of the response
     */


    public Response(T response, ResponseType type) {
        this(response, type, null);
    }

    /**
     * This method returns the object that is the
     * success response of this instance.
     * <p>
     * This method may return null if the {@link #response} exists,
     * but it is not a guarantee.
     *
     * @return the success response
     */

    @Nullable
    public T get() {
        return this.response;
    }

    /**
     * This method checks if the operation was successful, or in other words
     * if the response provider was not null.
     *
     * @return the response
     */

    public boolean success() {
        return this.type == ResponseType.SUCCESS;
    }
}
