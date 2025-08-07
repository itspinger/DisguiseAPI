package net.pinger.disguise.packet.v1_21_7;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.*;
import javax.annotation.Nonnull;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R5.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@PacketHandler(version = "1.21.7")
public class PacketProviderImpl implements PacketProvider {
    private final Plugin plugin;

    public PacketProviderImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    // Gets the GameProfile associated with the given Bukkit Player.
    @Override
    public GameProfile getGameProfile(Player player) {
        return ((CraftPlayer) player).getProfile();
    }

    /**
     * Retrieves the skin property (textures) from the player's GameProfile.
     * Uses reflection to access the private fields 'value' and 'signature' inside Property.
     */
    @Override
    public Skin getProperty(Player player) {
        GameProfile profile = getGameProfile(player);
        Collection<Property> textures = profile.getProperties().get("textures");

        return textures.stream()
            .findFirst()
            .map(p -> {
                try {
                    // Access private 'value' field via reflection
                    java.lang.reflect.Field valueField = p.getClass().getDeclaredField("value");
                    valueField.setAccessible(true);
                    String value = (String) valueField.get(p);

                    // Access private 'signature' field via reflection
                    java.lang.reflect.Field signatureField = p.getClass().getDeclaredField("signature");
                    signatureField.setAccessible(true);
                    String signature = (String) signatureField.get(p);

                    return new Skin(value, signature);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .orElse(null);
    }

    /**
     * Updates the player's skin properties by clearing existing 'textures'
     * and adding the new skin's Property.
     */
    @Override
    public void updateProperties(Player player, @Nonnull Skin skin) {
        // Ensure the skin handle is a Property before casting
        if (!(skin.getHandle() instanceof Property)) return;
        Property prop = (Property) skin.getHandle();

        GameProfile profile = getGameProfile(player);

        // Remove all existing texture properties
        profile.getProperties().removeAll("textures");

        // Add the new skin property
        profile.getProperties().put("textures", prop);
    }

    // Clears the 'textures' properties from the player's GameProfile.
    @Override
    public void clearProperties(Player player) {
        getGameProfile(player).getProperties().removeAll("textures");
    }

    // Sends one or more packets directly to the player.
    @Override
    public void sendPacket(Player player, Object... packet) {
        ServerPlayer sp = ((CraftPlayer) player).getHandle();
        for (Object p : packet) {
            sp.connection.send((Packet<?>) p);
        }
    }

    // Sends a sequence of server packets to update the player's skin and refresh their data.
    @Override
    public void sendServerPackets(Player player) {
        ServerPlayer sp = ((CraftPlayer) player).getHandle();
        ServerLevel level = (ServerLevel) sp.level();

        // Create spawn info and respawn packet for player
        CommonPlayerSpawnInfo spawnInfo = sp.createCommonSpawnInfo(level);
        ClientboundRespawnPacket resp = new ClientboundRespawnPacket(spawnInfo, ClientboundRespawnPacket.KEEP_ALL_DATA);

        // Remove player info, update info, and respawn
        sendPacket(player,
            new ClientboundPlayerInfoRemovePacket(Collections.singletonList(player.getUniqueId())),
            ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(Collections.singletonList(sp)),
            resp
        );

        // Update abilities, teleport, and reset sent info
        sp.onUpdateAbilities();
        sp.connection.teleport(player.getLocation());
        sp.resetSentInfo();

        // Run plugin tasks to refresh player permissions and info
        if (plugin.isEnabled()) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                PlayerList pl = sp.server.getPlayerList();
                pl.sendPlayerPermissionLevel(sp);
                pl.sendLevelInfo(sp, level);
                pl.sendAllPlayerInfo(sp);
            });
        }

        PacketProvider.refreshPlayer(player, plugin);
    }
}
