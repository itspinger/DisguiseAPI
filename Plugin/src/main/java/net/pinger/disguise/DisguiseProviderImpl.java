package net.pinger.disguise;

import com.mojang.authlib.GameProfile;
import net.pinger.disguise.exception.ValidationException;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.info.PlayerUpdateInfo;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.registration.RegistrySystem;
import net.pinger.disguise.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class DisguiseProviderImpl implements DisguiseProvider {

    private final DisguisePlugin disguise;
    private final PacketProvider provider;
    private final DisguiseRegistration registration;
    private final RegistrySystem registry;

    public DisguiseProviderImpl(DisguisePlugin disguise, DisguiseRegistration registration) {
        this.disguise = disguise;
        this.registration = registration;
        this.provider = DisguiseAPI.getProvider();
        this.registry = DisguiseAPI.getRegistrySystem();
    }

    public DisguiseProviderImpl(DisguisePlugin disguise) {
        this(disguise, RegistrySystem.DEFAULT_REGISTRATION);
    }

    @Override
    public void updatePlayer(DisguisePlayer player, Skin skin, String name, boolean silent) throws ValidationException {
        PlayerUpdateInfo info = new PlayerUpdateInfo(player, skin, name);

        // If no property was updated
        // Send a success message
        if (skin == null && name == null) {
            return;
        }

        // Check if this info is valid
        // According to this registration
        this.registration.validatePlayerInfo(info);
        Player pl = player.toBukkit();

        if (skin != null) {
            this.provider.updateProperties(pl, skin);
        } else {
            // Reset just in case
            // We are not able to find it
            this.provider.clearProperties(pl);
        }

        if (name != null) {
            this.updateNickname(pl, name);
        }

        this.provider.sendServerPackets(pl);

        // After sending updates
        // We have to call the onUpdateInfo event
        // And register this update in the register system
        if (this.registration != RegistrySystem.DEFAULT_REGISTRATION) {
            this.registry.updateRegistration(player, this.registration);
        }

        // Only if it's non-silent do we call this method
        if (!silent) {
            Bukkit.getScheduler().runTaskAsynchronously(this.disguise, () -> {
                this.registration.onPlayerUpdateInfo(info);
            });
        }
    }

    @Override
    public void updatePlayer(DisguisePlayer player, String name, boolean silent) throws ValidationException {
        PlayerUpdateInfo info = new PlayerUpdateInfo(player, name);

        if (name == null) {
            return;
        }

        // Check if this info is valid
        // According to this registration
        this.registration.validatePlayerInfo(info);
        Player pl = player.toBukkit();

        this.updateNickname(pl, name);
        this.provider.sendServerPackets(pl);

        // After sending updates
        // We have to call the onUpdateInfo event
        // And register this update in the register system
        if (this.registration != RegistrySystem.DEFAULT_REGISTRATION) {
            this.registry.updateRegistration(player, this.registration);
        }

        // Only if it's non-silent do we call this method
        if (!silent) {
            Bukkit.getScheduler().runTaskAsynchronously(this.disguise, () -> {
                this.registration.onPlayerUpdateInfo(info);
            });
        }
    }

    @Override
    public void resetPlayer(DisguisePlayer player, boolean resetSkin, boolean resetName, boolean silent) {
        Skin skin = resetSkin ? player.getDefaultSkin() : null;
        String name = resetName ? player.getDefaultName() : null;

        // Create a new player data with the given info
        PlayerUpdateInfo info = new PlayerUpdateInfo(player, skin, name);
        Player pl = player.toBukkit();

        if (skin != null) {
             this.provider.updateProperties(pl, skin);
        } else {
            // Reset just in case
            // We are not able to find it
            this.provider.clearProperties(pl);
        }

        if (name != null) {
            this.updateNickname(pl, name);
        }

        // Send packet update
        // And call reset on this player
        this.provider.sendServerPackets(pl);

        // Don't do this for non default registrations
        if (this.registration != RegistrySystem.DEFAULT_REGISTRATION) {
            this.registry.removeRegistration(player, this.registration);
        }

        // Only if it's non-silent do we call this method
        if (!silent) {
            Bukkit.getScheduler().runTaskAsynchronously(this.disguise, () -> {
                this.registration.onPlayerResetInfo(info);
            });
        }
    }

    private void updateNickname(Player player, String name) {
        try {
            Field field = GameProfile.class.getDeclaredField("name");

            // Set the field accessible and update it
            field.setAccessible(true);
            field.set(this.provider.getGameProfile(player), name);
        } catch (Exception e) {
            throw new ValidationException("Failed to update player name!", e);
        }

        player.setDisplayName(name);
        player.setPlayerListName(name);
    }
}
