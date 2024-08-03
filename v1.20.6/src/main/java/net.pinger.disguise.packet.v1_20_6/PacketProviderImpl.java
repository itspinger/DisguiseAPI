package net.pinger.disguise.packet.v1_20_6;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@PacketHandler(version = "1.20.6")
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

        Optional<Property> any = textures.stream().findFirst();
        return any.map(property -> new Skin(property.value(), property.signature())).orElse(null);
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

        CommonPlayerSpawnInfo spawnInfo = sp.createCommonSpawnInfo(level);
        ClientboundRespawnPacket respawn = new ClientboundRespawnPacket(spawnInfo, ClientboundRespawnPacket.KEEP_ALL_DATA);

        // Remove the player from the list
        this.sendPacket(player, new ClientboundPlayerInfoRemovePacket(Collections.singletonList(player.getUniqueId())));
        this.sendPacket(player, ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(Collections.singletonList(sp)));

        // Send the respawn packet to the player
        // With the respawn packet, the player gets to see their own skin
        this.sendPacket(player, respawn);

        // Update scale
        sp.onUpdateAbilities();

        sp.connection.teleport(player.getLocation());

        // Send health, food, experience (food is sent together with health)
        sp.resetSentInfo();

        if (this.plugin.isEnabled()) {
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                PlayerList playerList = sp.server.getPlayerList();
                playerList.sendPlayerPermissionLevel(sp);
                playerList.sendLevelInfo(sp, level);
                playerList.sendAllPlayerInfo(sp);
            });
        }

        // Send the refresh packet to other players
        // Where they will be able to see the updated skin
        PacketProvider.refreshPlayer(player, this.plugin);
    }
}
