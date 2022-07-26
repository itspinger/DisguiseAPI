package net.pinger.disguise.packet;

import net.pinger.disguise.Skin;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface PacketProvider {

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
     * This method refreshes a player by hiding them and reshowing them
     * to players that have this player in sight.
     *
     * @param player the player to reshow
     * @param plugin the plugin
     */

    static void refreshPlayer(Player player, Plugin plugin) {
        // Check if the version is at least 1.16
        boolean version = MinecraftServer.atLeast("1.16");

        Bukkit.getScheduler().runTask(plugin, () -> {
            // List of players that can see this player
            List<UUID> see = new ArrayList<>();

            // Loop through all online players
            // And see which one can see this player
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (!otherPlayer.canSee(player))
                    continue;

                see.add(otherPlayer.getUniqueId());

                // Check for version in order to hide
                if (version) {
                    otherPlayer.hidePlayer(plugin, player);
                    continue;
                }

                // Otherwise, hide with the old parameters
                otherPlayer.hidePlayer(player);
            }

            // Now show the player for each one in the list
            for (UUID id : see) {
                // Get the player from the id
                Player otherPlayer = Bukkit.getPlayer(id);

                // Edge case, but will not happen
                if (otherPlayer == null)
                    continue;

                if (version) {
                    otherPlayer.showPlayer(plugin, player);
                    continue;
                }

                otherPlayer.showPlayer(player);
            }
        });
    }

}
