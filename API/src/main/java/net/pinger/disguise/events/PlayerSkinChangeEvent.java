package net.pinger.disguise.events;

import net.pinger.disguise.DisguisePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSkinChangeEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final DisguisePlayer player;

    public PlayerSkinChangeEvent(DisguisePlayer player) {
        this.player = player;
    }

    public DisguisePlayer getPlayer() {
        return this.player;
    }

    public Player getBukkitPlayer() {
        return this.player.toBukkit();
    }

    @Override
    public HandlerList getHandlers() {
        return PlayerSkinChangeEvent.handlerList;
    }

    public static HandlerList getHandlerList() {
        return PlayerSkinChangeEvent.handlerList;
    }
}
