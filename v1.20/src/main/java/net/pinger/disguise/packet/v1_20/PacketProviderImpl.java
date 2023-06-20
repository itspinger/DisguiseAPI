package net.pinger.disguise.packet.v1_20;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.BiomeManager;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.update.PlayerUpdate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@PacketHandler(version = "1.20", compatibility = "1.20.1")
public class PacketProviderImpl implements PacketProvider {

    private final Plugin plugin;

    public PacketProviderImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public GameProfile getGameProfile(Player player) {
        return ((CraftPlayer) player).getProfile();
    }

    @Override
    public Skin getProperty(Player player) {
        GameProfile profile = this.getGameProfile(player);
        Collection<Property> textures = profile.getProperties().get("textures");

        // Check if the textures may be empty
        if (textures.isEmpty()) {
            return null;
        }

        Optional<Property> any = textures.stream().filter(property -> property.getValue() != null).findAny();
        return any.map(property -> new Skin(property.getValue(), property.getSignature())).orElse(null);
    }

    @Override
    public void updateProperties(Player player, @Nonnull Skin skin) {
        GameProfile profile = this.getGameProfile(player);

        // Check if the skin isn't equal to the property
        // Note that this shouldn't happen
        if (!(skin.getHandle() instanceof Property)) {
            return;
        }

        // Cast to the property
        Property prop = (Property) skin.getHandle();

        // Clear the properties of this player
        // And add the new ones
        this.clearProperties(player);
        profile.getProperties().put("textures", prop);
    }

    @Override
    public void clearProperties(Player player) {
        this.getGameProfile(player).getProperties().removeAll("textures");
    }

    @Override
    public void sendPacket(Player player, Object... packet) {
        // Get the entity player from the base player
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        // Loop through each packet
        // And send it to this player
        for (Object p : packet) {
            entityPlayer.connection.send((Packet<?>) p);
        }
    }

    @Override
    public void sendServerPackets(Player player) {
        // Get the entity player from the base player
        ServerPlayer sp = ((CraftPlayer) player).getHandle();
        ServerLevel level = sp.serverLevel();

        // Create the PacketPlayOutRespawn packet
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(
                level.dimensionTypeId(),
                level.dimension(),
                BiomeManager.obfuscateSeed(level.getSeed()),
                sp.gameMode.getGameModeForPlayer(),
                sp.gameMode.getPreviousGameModeForPlayer(),
                level.isDebug(),
                level.isFlat(),
                (byte) 3,
                sp.getLastDeathLocation(),
                sp.getPortalCooldown()
        );

        // Get the name and stuff
        Location l = player.getLocation();

        // Send position
        ClientboundPlayerPositionPacket pos = new ClientboundPlayerPositionPacket(
                l.getX(),
                l.getY(),
                l.getZ(),
                l.getYaw(),
                l.getPitch(),
                new HashSet<>(),
                0
        );

        PlayerUpdate update = this.createUpdate(player);

        // Remove the player from the list
        this.sendPacket(player, new ClientboundPlayerInfoRemovePacket(Collections.singletonList(sp.getUUID())));
        this.sendPacket(player, ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(Collections.singletonList(sp)));

        // Send the respawn packet to the player
        // With the respawn packet, the player gets to see their own skin
        this.sendPacket(player, respawn);
        this.sendPacket(player, pos);
        this.sendUpdate(update);

        // Update scale
        ((CraftPlayer) player).updateScaledHealth();
        sp.onUpdateAbilities();
        sp.resetSentInfo();

        // Send the refresh packet to other players
        // Where they will be able to see the updated skin
        PacketProvider.refreshPlayer(player, this.plugin);
    }
}
