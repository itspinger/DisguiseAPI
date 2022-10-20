package net.pinger.disguise;

import net.pinger.disguise.exception.UserNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class DisguisePlayerImpl implements DisguisePlayer {

    private String defaultName;
    private Skin defaultSkin;
    private final UUID id;

    public DisguisePlayerImpl(UUID id) {
        this.id = id;
    }

    @Override
    public String getDefaultName() {
        return this.defaultName;
    }

    @Override
    public void setDefaultName(String name) {
        // Don't allow change of name
        // If already set
        if (this.defaultName != null) {
            return;
        }

        this.defaultName = name;
    }

    @Override
    public Skin getDefaultSkin() {
        if (this.defaultSkin != null) {
            return this.defaultSkin;
        }

        // Fetch the default skin here
        boolean property = false;

        try {
            Skin skin = DisguiseAPI.getSkinManager().getFromMojang(this.id);

            // If an error occurred, make sure to flag the property as true
            if (skin == null) {
                property = true;
            }

            this.defaultSkin = skin;
        } catch (UserNotFoundException e) {
            property = true;
        }

        if (!property) {
            return this.defaultSkin;
        }

        // Catch from the property
        return (this.defaultSkin = getCurrentSkin());
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
