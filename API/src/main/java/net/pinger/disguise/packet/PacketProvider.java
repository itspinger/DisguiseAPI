package net.pinger.disguise.packet;

import net.pinger.disguise.Skin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

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

}
