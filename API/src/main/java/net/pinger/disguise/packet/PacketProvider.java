package net.pinger.disguise.packet;

import com.mojang.authlib.GameProfile;
import net.pinger.disguise.skin.Skin;
import net.pinger.disguise.player.update.PlayerUpdate;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public interface PacketProvider {

    /**
     * This method returns the {@link GameProfile} for this player.
     *
     * @param player the player
     * @return the game profile
     */

    GameProfile getGameProfile(Player player);

    /**
     * This method retrieves the {@link Skin} property
     * from the player's GameProfile.
     *
     * @param player the player
     * @return the skin property
     */

    Skin getProperty(Player player);

    /**
     * This method applies a certain property to a player.
     *
     * @param player the player that the skin is applied to
     * @param skin the skin that is being applied
     */

    void updateProperties(Player player, @Nonnull Skin skin);

    /**
     * This method clears every property from the player.
     * It is equal to just clearing the game profile of the player.
     *
     * @param player the player
     */

    void clearProperties(Player player);

    /**
     * Sends a specific packet or multiple packets to this player.
     *
     * @param player the player that the packets are being sent
     * @param packet the packets that are being sent
     */

    void sendPacket(Player player, Object... packet);

    /**
     * This method sends packets to all servers that are
     * currently logged in to the server.
     *
     * @param packet the packets that are sent
     */

    default void sendPacket(Object... packet) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.sendPacket(player, packet);
        }
    }

    /**
     * This method sends all packets that contribute
     * to changing the player properties.
     * <p>
     * The packets that are sent may vary depending on the version.
     *
     * @param player the player
     */

    void sendServerPackets(Player player);

    /**
     * This method creates a new update for the specified player.
     *
     * @param player the player to update
     * @return the update object
     */

    default PlayerUpdate createUpdate(Player player) {
        return new PlayerUpdate(player);
    }

    /**
     * This method sends an update to the specified
     * player.
     *
     * @param update the update
     */

    default void sendUpdate(PlayerUpdate update) {
        update.send();
    }

    /**
     * This method refreshes a player by hiding them and reshowing them
     * to players that have this player in sight.
     *
     * @param player the player to reshow
     * @param plugin the plugin
     */

    static void refreshPlayer(Player player, Plugin plugin) {
        // Skip this action
        // If the plugin is disabled
        // Otherwise an error will be thrown
        if (!plugin.isEnabled()) {
            return;
        }

        // Check if the version is at least 1.16
        // Which changed the way you can hide a player
        // Using "Player#hidePlayer(Plugin, Player)"
        // Instead of "Player#hidePlayer(Player)"
        final boolean version = MinecraftServer.atLeast("1.16");

        // Hide the player
        // To all other players
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(player)) {
                continue;
            }

            if (version) {
                other.hidePlayer(plugin, player);
            } else {
                other.hidePlayer(player);
            }
        }

        // Now loop once again
        // And show the player
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(player)) {
                continue;
            }

            if (version) {
                other.showPlayer(plugin, player);
            } else {
                other.showPlayer(player);
            }
        }
    }

}
