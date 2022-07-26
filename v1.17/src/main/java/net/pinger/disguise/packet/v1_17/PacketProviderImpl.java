package net.pinger.disguise.packet.v1_17;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashSet;

@PacketHandler(version = "1.17")
public class PacketProviderImpl implements PacketProvider {

    private final Plugin plugin;

    public PacketProviderImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void updateProperties(Player player, @Nonnull Skin skin) {
        GameProfile profile = ((CraftPlayer) player).getProfile();

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
        ((CraftPlayer) player).getProfile().getProperties().removeAll("textures");
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
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        // Cache the location from the player
        // Which might be updated later
        Location loc = player.getLocation();

        // Create the block position from the given location
        BlockPos position = new BlockPos(
                loc.getBlockX(),
                loc.getBlockY(),
                loc.getBlockZ());

        // Create the PacketPlayOutRespawn packet
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(
                entityPlayer.getCommandSenderWorld().dimensionType(),
                entityPlayer.getCommandSenderWorld().dimension(),
                entityPlayer.getId(),
                entityPlayer.gameMode.getGameModeForPlayer(),
                entityPlayer.gameMode.getPreviousGameModeForPlayer(),
                false,
                false,
                false);

        // Create the PacketPlayOutPosition packet
        ClientboundPlayerPositionPacket playerPosition = new ClientboundPlayerPositionPacket(
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch(),
                new HashSet<>(),
                0,
                false);

        this.sendPacket(new ClientboundRemoveEntityPacket(entityPlayer.getId()));
        this.sendPacket(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, entityPlayer));
        this.sendPacket(player, new ClientboundSetDefaultSpawnPositionPacket(position, 45));
        this.sendPacket(player, respawn);
        this.sendPacket(player, playerPosition);
        this.sendPacket(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer));

        player.updateInventory();
    }
}
