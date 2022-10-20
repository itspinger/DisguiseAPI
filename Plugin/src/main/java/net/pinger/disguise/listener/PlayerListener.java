package net.pinger.disguise.listener;

import net.pinger.disguise.Disguise;
import net.pinger.disguise.DisguisePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final Disguise disguise;

    public PlayerListener(Disguise disguise) {
        this.disguise = disguise;
    }

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {
        this.disguise.getPlayerManager().createPlayer(event.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        // Call the get method once to retrieve the skin if it already doesn't exist
        // Maybe there is a better way to do this?
        DisguisePlayer player = this.disguise.getPlayerManager().getDisguisePlayer(event.getPlayer());

        // Set default stuff
        player.getDefaultSkin();
        player.setDefaultName(event.getPlayer().getName());
    }

}
