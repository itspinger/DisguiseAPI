package net.pinger.disguise.player;

import net.pinger.disguise.*;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {

    private final Map<UUID, DisguisePlayer> players = new HashMap<>();
    private final PacketProvider provider;

    public PlayerManagerImpl(DisguisePlugin disguise) {
        this.provider = disguise.getPacketProvider();

        // Cache all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            // First refresh the player skin
            // To other players
            PacketProvider.refreshPlayer(player, disguise);

            // Create the player
            this.createPlayer(player);
        }
    }

    @Override
    public DisguisePlayer getDisguisePlayer(Player player) {
        return this.players.get(player.getUniqueId());
    }

    @Override
    public DisguisePlayer getDisguisePlayer(UUID id) {
        return this.players.get(id);
    }

    @Override
    public void createPlayer(Player player) {
        UUID id = player.getUniqueId();
        String name = player.getName();
        Skin skin = this.provider.getProperty(player);

        // Add to the player
        this.players.putIfAbsent(id, new DisguisePlayerImpl(id, skin, name));
    }

    @Override
    public void shutdown() {
        // If the provider happens to be null for some reason
        // Then skip the shutdown
        if (this.provider == null) {
            return;
        }

        // Reset all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            DisguiseAPI.getDefaultProvider().resetPlayerSilently(player);
        }
    }
}
