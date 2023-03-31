package net.pinger.disguise.listener;

import net.pinger.disguise.Disguise;
import net.pinger.disguise.DisguisePlayer;
import net.pinger.disguise.DisguisePlayerImpl;
import net.pinger.disguise.DisguiseProviderImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {

    private final Disguise disguise;

    public PlayerListener(Disguise disguise) {
        this.disguise = disguise;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerLoginEvent event) {
        // Set the priority to lowest
        // To make sure this one gets registered first
        // Make sure that even when this is in offline mode
        // That the default skin is not null
        // And in fact is fetched for the wanted player
        this.disguise.getPlayerManager().createPlayer(event.getPlayer());
    }

}
