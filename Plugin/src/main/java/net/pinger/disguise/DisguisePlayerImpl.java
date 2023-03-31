package net.pinger.disguise;

import net.pinger.disguise.exception.UserNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class DisguisePlayerImpl implements DisguisePlayer {

    private final UUID id;
    private final Skin defaultSkin;
    private final String defaultName;

    public DisguisePlayerImpl(UUID id, Skin defaultSkin, String defaultName) {
        this.id = id;
        this.defaultSkin = defaultSkin;
        this.defaultName = defaultName;
    }

    @Override
    public String getDefaultName() {
        return this.defaultName;
    }

    @Override
    public Skin getDefaultSkin() {
        return this.defaultSkin;
    }

    @Override
    public Skin getCurrentSkin() {
        Objects.requireNonNull(toBukkit(), "Player must be online");
        return DisguiseAPI.getProvider().getProperty(toBukkit());
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Player toBukkit() {
        return Bukkit.getPlayer(this.id);
    }
}
