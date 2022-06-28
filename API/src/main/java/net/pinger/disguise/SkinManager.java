package net.pinger.disguise;

import net.pinger.disguise.exception.UserNotFoundException;
import net.pinger.disguise.response.Response;

import java.util.UUID;
import java.util.function.Consumer;

public interface SkinManager {

    /**
     * This method returns a new {@link Skin} instance based on the provided
     * image url.
     * <p>
     * Because this method runs asynchronously, we are using a {@link Consumer} for the
     * resulting response.
     * One can determine whether this method ran successfully by checking the
     * {@link Response.ResponseType} or just {@link Response#success()}.
     *
     * <p>
     * As of June 27th 2022, the response (success) format looks like this,
     * where only a url is provided:
     * <pre>
     * {
     *   "id": 0,
     *   "idStr": "string",
     *   "uuid": "string",
     *   "name": "string",
     *   "variant": "classic",
     *   "data": {
     *     "uuid": "string",
     *     "texture": {
     *       "value": "string",
     *       "signature": "string",
     *       "url": "string"
     *     }
     *   },
     *   "timestamp": 0,
     *   "duration": 0,
     *   "account": 0,
     *   "server": "string",
     *   "private": true,
     *   "views": 0,
     *   "nextRequest": 0,
     *   "duplicate": true
     * }
     * </pre>
     *
     * @param imageUrl the url of the image
     * @param response the response
     */

    void getFromImage(String imageUrl, Consumer<Response<Skin>> response);

    Skin getFromMojang(String playerName);

    Skin getFromMojang(UUID playerId) throws UserNotFoundException;
}
