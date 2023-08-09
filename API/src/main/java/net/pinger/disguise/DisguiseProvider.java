package net.pinger.disguise;

import net.pinger.disguise.exception.ValidationException;
import net.pinger.disguise.player.info.PlayerUpdateInfo;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.skin.Skin;
import org.bukkit.entity.Player;

public interface DisguiseProvider {

    /**
     * This method updates the player profile by changing the
     * skin and name of the player.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(DisguisePlayer, Skin)} and
     * {@link #updatePlayer(DisguisePlayer, String)}.
     * <p>
     * If this update is not silent, after the player is updated
     * the {@link DisguiseRegistration#onPlayerUpdateInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     */

    void updatePlayer(DisguisePlayer player, Skin skin, String name, boolean silent) throws ValidationException;

    /**
     * This method resets the player profile by reverting to their
     * original skin and name.
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you know for sure that you want to change both of these
     * properties, you can use {@link #resetPlayer(DisguisePlayer)} to change
     * both skin and name.
     * <p>
     * If this update is not silent, after the player is updated
     * the {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin whether the reset the skin
     * @param name whether to reset the name
     */

    void resetPlayer(DisguisePlayer player, boolean skin, boolean name, boolean silent) throws ValidationException;

    /**
     * This method updates the player profile by changing the
     * skin and name of the player (non silently).
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(DisguisePlayer, Skin)} and
     * {@link #updatePlayer(DisguisePlayer, String)}.
     * <p>
     * After the player is updated
     * the {@link DisguiseRegistration#onPlayerUpdateInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     */

    default void updatePlayer(DisguisePlayer player, Skin skin, String name) {
        this.updatePlayer(player, skin, name, false);
    }

    /**
     * This method updates the player profile by changing the
     * skin and name of the player (silently).
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(DisguisePlayer, Skin)} and
     * {@link #updatePlayer(DisguisePlayer, String)}.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     */

    default void updatePlayerSilently(DisguisePlayer player, Skin skin, String name) {
        this.updatePlayer(player, skin, name, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you know for sure that you want to change both of these
     * properties, you can use {@link #resetPlayer(DisguisePlayer)} to change
     * both skin and name.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin whether the reset the skin
     * @param name whether to reset the name
     */

    default void resetPlayer(DisguisePlayer player, boolean skin, boolean name) {
        this.resetPlayer(player, skin, name, false);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you know for sure that you want to change both of these
     * properties, you can use {@link #resetPlayer(DisguisePlayer)} to change
     * both skin and name.
     * <p>
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin whether the reset the skin
     * @param name whether to reset the name
     */

    default void resetPlayerSilently(DisguisePlayer player, boolean skin, boolean name) {
        this.resetPlayer(player, skin, name, true);
    }

    /**
     * This method updates the player profile by changing the
     * skin and name of the player (silently).
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(DisguisePlayer, Skin)} and
     * {@link #updatePlayer(DisguisePlayer, String)}.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     */

    default void updatePlayerSilently(Player player, Skin skin, String name) {
        this.updatePlayer(DisguiseAPI.getDisguisePlayer(player), skin, name, true);
    }

    /**
     * This method updates the player profile by changing the
     * skin and name of the player.
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * The return value of this action determines the success
     * of the attempted update, meaning that if it returns false,
     * then it is likely that an error occurred.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(Player, Skin)} and
     * {@link #updatePlayer(Player, String)}.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     */

    default void updatePlayer(Player player, Skin skin, String name) throws ValidationException {
        this.updatePlayer(DisguiseAPI.getDisguisePlayer(player), skin, name);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name.
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you know for sure that you want to change both of these
     * properties, you can use {@link #resetPlayer(Player)} to change
     * both skin and name.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin whether the reset the skin
     * @param name whether to reset the name
     */

    default void resetPlayer(Player player, boolean skin, boolean name) throws ValidationException {
        this.resetPlayer(DisguiseAPI.getDisguisePlayer(player), skin, name);
    }

    /**
     * This method updates the player by changing their skin.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the player name as well,
     * you can use the {@link #updatePlayer(DisguisePlayer, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin the skin to apply
     */

    default void updatePlayer(DisguisePlayer player, Skin skin) throws ValidationException {
        this.updatePlayer(player, skin, null);
    }

    /**
     * This method updates the player by changing their skin.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the player name as well,
     * you can use the {@link #updatePlayer(Player, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param skin the skin to apply
     */

    default void updatePlayer(Player player, Skin skin) throws ValidationException {
        this.updatePlayer(player, skin, null);
    }

    /**
     * This method updates the specified player by changing their name.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the skin of the player as well,
     * you can use the {@link #updatePlayer(DisguisePlayer, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param name the name to set
     */

    void updatePlayer(DisguisePlayer player, String name, boolean silent) throws ValidationException;

    /**
     * This method updates the specified player by changing their name.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the skin of the player as well,
     * you can use the {@link #updatePlayer(DisguisePlayer, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param name the name to set
     */

    default void updatePlayer(DisguisePlayer player, String name) {
        this.updatePlayer(player, name, false);
    }

    /**
     * This method updates the specified player by changing their name.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the skin of the player as well,
     * you can use the {@link #updatePlayer(DisguisePlayer, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param name the name to set
     */

    default void updatePlayerSilently(DisguisePlayer player, String name) {
        this.updatePlayer(player, name, true);
    }

    /**
     * This method updates the specified player by changing their name.
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * If you need to change the skin of the player as well,
     * you can use the {@link #updatePlayer(Player, Skin, String)}
     * method.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     * @param name the name to set
     */

    default void updatePlayer(Player player, String name) throws ValidationException {
        this.updatePlayer(DisguiseAPI.getDisguisePlayer(player), name);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayer(DisguisePlayer player) throws ValidationException {
        this.resetPlayer(player, true, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (silently).
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerSilently(DisguisePlayer player) throws ValidationException {
        this.resetPlayerSilently(player, true, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayer(Player player) throws ValidationException {
        this.resetPlayer(player, true, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin and name (silently).
     * <p>
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerSilently(Player player) throws ValidationException {
        this.resetPlayerSilently(DisguiseAPI.getDisguisePlayer(player), true, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original name (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerName(DisguisePlayer player) throws ValidationException {
        this.resetPlayer(player, false, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original name (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerName(Player player) throws ValidationException {
        this.resetPlayer(player, false, true);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerSkin(DisguisePlayer player) throws ValidationException {
        this.resetPlayer(player, true, false);
    }

    /**
     * This method resets the player profile by reverting to their
     * original skin (non-silently).
     * If the update of the player failed for any reason,
     * this method will throw a {@link ValidationException}.
     * <p>
     * After the player is updated the
     * {@link DisguiseRegistration#onPlayerResetInfo(PlayerUpdateInfo)} method
     * will be called.
     *
     * @throws ValidationException if validating this update isn't correct
     * @param player the player
     */

    default void resetPlayerSkin(Player player) throws ValidationException {
        this.resetPlayer(player, true, false);
    }

}
