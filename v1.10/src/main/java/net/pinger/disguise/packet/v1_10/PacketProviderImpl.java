package net.pinger.disguise.packet.v1_10;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_10_R1.*;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

@PacketHandler(version = "1.10", compatibility = "1.10.2")
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

        // Create the PacketPlayOutRespawn packet
        PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(
                entityPlayer.dimension,
                entityPlayer.getWorld().getDifficulty(),
                entityPlayer.getWorld().worldData.getType(),
                entityPlayer.playerInteractManager.getGameMode());

        // Send all the necessary packets
        this.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));

        final GameMode gameMode = player.getGameMode();
        final boolean allowFlight = player.getAllowFlight();
        final boolean flying = player.isFlying();
        final Location location = player.getLocation();
        final int level = player.getLevel();
        final float xp = player.getExp();
        final double maxHealth = player.getMaxHealth();
        final double health = player.getHealth();

        Chunk chunk = player.getLocation().getChunk();
        net.minecraft.server.v1_10_R1.Chunk entity = ((CraftChunk) chunk).getHandle();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.sendPacket(player, respawn);

            player.setGameMode(gameMode);
            player.setAllowFlight(allowFlight);
            player.setFlying(flying);
            player.teleport(location);
            player.updateInventory();
            player.setLevel(level);
            player.setExp(xp);
            player.setMaxHealth(maxHealth);
            player.setHealth(health);

            this.sendPacket(player, new PacketPlayOutMapChunk(entity, 20));

            // Send the add packet
            this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));

            // Refresh the player
            PacketProvider.refreshPlayer(player, plugin);
        }, 1L);
    }
}
