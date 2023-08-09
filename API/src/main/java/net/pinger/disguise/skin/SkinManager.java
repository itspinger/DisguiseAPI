package net.pinger.disguise.skin;

import net.pinger.disguise.exception.UserNotFoundException;
import net.pinger.disguise.response.Response;
import net.pinger.disguise.skin.Skin;

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

    /**
     * This method returns a new {@link Skin} instance based on the provided
     * playerName.
     * <p>
     * Unlike {@link #getFromImage(String, Consumer)}, this method does not
     * run asynchronously.
     * <p>
     * If the call to the REST API is successful, the response will look
     * like this:
     * <pre>
     * {
     *   "id" : "c8809dd609af4706a92b3f89f6433a67",
     *   "name" : "aha",
     *   "properties" : [ {
     *     "name" : "textures",
     *     "value" : "someValue",
     *     "signature" : "someSignature"
     *   } ]
     * }
     * </pre>
     *
     * @param playerName the playerName to catch the skin from
     * @return the new {@link Skin} instance, or null if failed
     * @throws UserNotFoundException if the specified name does not match any users
     */

    Skin getFromMojang(String playerName) throws UserNotFoundException;

    /**
     * This method returns a new {@link Skin} instance based on the provided
     * playerId.
     * <p>
     * Unlike {@link #getFromImage(String, Consumer)}, this method does not
     * run asynchronously.
     * <p>
     * If the call to the REST API is successful, the response will look
     * like this:
     * <pre>
     * {
     *   "id" : "c8809dd609af4706a92b3f89f6433a67",
     *   "name" : "aha",
     *   "properties" : [ {
     *     "name" : "textures",
     *     "value" : "someValue",
     *     "signature" : "someSignature"
     *   } ]
     * }
     * </pre>
     *
     * @param playerId the uuid of the player
     * @return the new {@link Skin} instance, or null if failed
     * @throws UserNotFoundException if the specified id does not match any users
     */

    Skin getFromMojang(UUID playerId) throws UserNotFoundException;
}
