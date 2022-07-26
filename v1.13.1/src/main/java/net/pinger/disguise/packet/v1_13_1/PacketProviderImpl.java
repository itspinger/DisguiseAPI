package net.pinger.disguise.packet.v1_13_1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_13_R2.*;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashSet;

@PacketHandler(version = "1.13.1", compatibility = "1.13.2")
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
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        // Loop through each packet
        // And send it to this player
        for (Object p : packet) {
            entityPlayer.playerConnection.sendPacket((Packet<?>) p);
        }
    }

    @Override
    public void sendServerPackets(Player player) {
        // Get the entity player from the base player
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        // Cache the location from the player
        // Which might be updated later
        Location loc = player.getLocation();

        // Create the block position from the given location
        BlockPosition position = new BlockPosition(
                loc.getBlockX(),
                loc.getBlockY(),
                loc.getBlockZ());

        // Create the PacketPlayOutRespawn packet
        PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(
                entityPlayer.dimension,
                entityPlayer.getWorld().getDifficulty(),
                entityPlayer.getWorld().worldData.getType(),
                entityPlayer.playerInteractManager.getGameMode());

        // Create the PacketPlayOutPosition packet
        PacketPlayOutPosition playerPosition = new PacketPlayOutPosition(
                loc.getX(),
                loc.getY(), loc.getZ(),
                loc.getYaw(),
                loc.getPitch(),
                new HashSet<>(),
                0);

        // Send all the necessary packets
        this.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
        this.sendPacket(player, new PacketPlayOutSpawnPosition(position));
        this.sendPacket(player, respawn);
        this.sendPacket(player, playerPosition);
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));

        // Update the player inventory at last
        player.updateInventory();
    }
}
