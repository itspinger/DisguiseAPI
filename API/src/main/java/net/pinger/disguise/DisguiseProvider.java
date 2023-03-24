package net.pinger.disguise;

import org.bukkit.entity.Player;

import javax.annotation.CheckReturnValue;

public interface DisguiseProvider {

    /**
     * This method updates the player profile by changing the
     * skin and name of the player.
     * <p>
     * The return value of this action determines the success
     * of the attempted update, meaning that if it returns false,
     * then it is likely that an error occurred.
     * <p>
     * If you only need to change one of these attributes,
     * you can use {@link #updatePlayer(Player, Skin)} and
     * {@link #updatePlayer(Player, String)}.
     *
     * @param player the player to update
     * @param skin the skin to set
     * @param name the name to set
     * @return the success of this action
     */

    @CheckReturnValue
    boolean updatePlayer(Player player, Skin skin, String name);

    /**
     * This method updates the player by changing their skin.
     * <p>
     * The return value of this method should be used,
     * as it determines the success of the action.
     * <p>
     * If you need to change the player name as well,
     * you can use the {@link #updatePlayer(Player, Skin, String)}
     * method.
     *
     * @param player the player
     * @param skin the skin to apply
     * @return the success of this action
     */

    @CheckReturnValue
    default boolean updatePlayer(Player player, Skin skin) {
        return this.updatePlayer(player, skin, null);
    }

    @CheckReturnValue
    default boolean updatePlayer(Player player, String name) {
        return this.updatePlayer(player, null, name);
    }

}
