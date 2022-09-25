package net.pinger.disguise.player;

import net.pinger.disguise.DisguisePlayer;
import net.pinger.disguise.DisguisePlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {

    private final Map<UUID, DisguisePlayer> players = new HashMap<>();

    public PlayerManagerImpl() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.createPlayer(player.getUniqueId());

            // Get the player and fetch default skin
            DisguisePlayer disguisePlayer = getDisguisePlayer(player);
            disguisePlayer.getDefaultSkin();
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
    public void createPlayer(UUID id) {
        this.players.putIfAbsent(id, new DisguisePlayerImpl(id));
    }
}
