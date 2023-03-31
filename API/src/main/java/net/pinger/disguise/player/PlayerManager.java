package net.pinger.disguise.player;

import net.pinger.disguise.DisguisePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerManager {

    /**
     * This method retrieves a specific {@link DisguisePlayer player} that matches
     * the given bukkit {@link Player player}.
     *
     * @param player the player
     * @return the disguise player
     */

    DisguisePlayer getDisguisePlayer(Player player);

    /**
     * This method retrieves a specific {@link DisguisePlayer player} that has
     * the specified id.
     *
     * <p>
     * Note that if you are using the id of a {@link org.bukkit.OfflinePlayer},
     * trying to find a current skin for the player will throw an exception.
     *
     * @see #getDisguisePlayer(Player)
     * @param id the id of the player
     * @return the disguise player
     */

    DisguisePlayer getDisguisePlayer(UUID id);

    /**
     * This method creates a new {@link DisguisePlayer} from the given player id.
     *
     * @param player the player to create
     */

    default void createPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    /**
     * This method shutdowns this manager.
     */

    void shutdown();
}
