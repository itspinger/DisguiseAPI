package net.pinger.disguise.player.update;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerUpdate {

    private final Player player;
    private final GameMode gameMode;
    private final boolean allowFlight;
    private final boolean flying;
    private final Location location;
    private final int level;
    private final float xp;

    public PlayerUpdate(Player player) {
        this.player = player;

        // Update other fields
        this.gameMode = player.getGameMode();
        this.allowFlight = player.getAllowFlight();
        this.flying = player.isFlying();
        this.location = player.getLocation();
        this.level = player.getLevel();
        this.xp = player.getExp();
    }

    /**
     * This method applies the saved properties to the
     * given player.
     */

    public void send() {
        if (this.player == null) {
            return;
        }

        // Update the properties here
        this.player.setGameMode(this.gameMode);
        this.player.setAllowFlight(this.allowFlight);
        this.player.setFlying(this.flying);
        this.player.teleport(this.location);
        this.player.updateInventory();
        this.player.setLevel(this.level);
        this.player.setExp(this.xp);
    }
}
