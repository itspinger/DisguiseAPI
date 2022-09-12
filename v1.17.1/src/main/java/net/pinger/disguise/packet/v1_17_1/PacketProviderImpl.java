package net.pinger.disguise.packet.v1_17_1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.pinger.disguise.Skin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.data.PlayerDataWrapper;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

@PacketHandler(version = "1.17.1")
public class PacketProviderImpl implements PacketProvider {

    private final Plugin plugin;

    public PacketProviderImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Skin getProperty(Player player) {
        GameProfile profile = ((CraftPlayer) player).getProfile();
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
                entityPlayer.getCommandSenderWorld().dimensionType(),
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
        LevelChunk entity = ((CraftChunk) player.getLocation().getChunk()).getHandle();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.sendPacket(player, respawn);

            dataWrapper.applyProperties();
            this.sendPacket(player, new ClientboundLevelChunkPacket(entity));

            // Send the add packet
            this.sendPacket(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer));

            // Refresh the player
            PacketProvider.refreshPlayer(player, plugin);
        }, 1L);
    }
}
