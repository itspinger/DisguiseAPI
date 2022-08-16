package net.pinger.disguise.packet.v1_18_2;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.data.PlayerDataWrapper;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

@PacketHandler(version = "1.18.2")
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

        // Create the PacketPlayOutRespawn packet
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(
                entityPlayer.getCommandSenderWorld().dimensionTypeRegistration(),
                entityPlayer.getCommandSenderWorld().dimension(),
                entityPlayer.getCommandSenderWorld().getLevelData().getZSpawn(),
                entityPlayer.gameMode.getGameModeForPlayer(),
                entityPlayer.gameMode.getPreviousGameModeForPlayer(),
                false,
                entityPlayer.getLevel().isFlat(),
                true);

        this.sendPacket(new ClientboundRemoveEntitiesPacket(entityPlayer.getId()));
        this.sendPacket(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, entityPlayer));

        // Create a data wrapper
        PlayerDataWrapper dataWrapper = new PlayerDataWrapper(player);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.sendPacket(player, respawn);

            dataWrapper.applyProperties();

            // Send the add packet
            this.sendPacket(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer));

            // Refresh the player
            PacketProvider.refreshPlayer(player, plugin);
        }, 1L);
    }
}
