package net.pinger.disguise.packet.v1_17_1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.BiomeManager;
import net.pinger.disguise.skin.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.player.update.PlayerUpdate;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@PacketHandler(version = "1.17.1")
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
        ServerLevel level = sp.getLevel();

        // Create the PacketPlayOutRespawn packet
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(
                level.dimensionType(),
                level.dimension(),
                BiomeManager.obfuscateSeed(level.getSeed()),
                sp.gameMode.getGameModeForPlayer(),
                sp.gameMode.getPreviousGameModeForPlayer(),
                level.isDebug(),
                level.isFlat(),
                true
        );

        // Get the name and stuff
        Location loc = player.getLocation();

        // Send position
        ClientboundPlayerPositionPacket pos = new ClientboundPlayerPositionPacket(
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch(),
                new HashSet<>(),
                0,
                false
        );

        PlayerUpdate update = this.createUpdate(player);

        // Send all the necessary packets
        this.sendPacket(player, new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, sp));
        this.sendPacket(player, new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, sp));

        // Send the respawn and pos packet
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
