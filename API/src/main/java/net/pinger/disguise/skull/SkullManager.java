package net.pinger.disguise.skull;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.net.MalformedURLException;
import java.net.URL;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.skin.Skin;
import net.pinger.disguise.context.GameProfileContext;
import net.pinger.disguise.item.ItemBuilder;
import net.pinger.disguise.item.XMaterial;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class SkullManager {

    private final LoadingCache<UUID, ItemStack> skulls = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(CacheLoader.from(this::loadSkull));

    private static Method metaSetProfileMethod;
    private static Field metaProfileField;

    public ItemStack getSkullFrom(UUID id) {
        try {
            return this.skulls.get(id);
        } catch (Exception e) {
            DisguiseAPI.getLogger().error(String.format("Failed to retrieve skull from id -> %s", id), e);
        }

        return null;
    }

    private ItemStack loadSkull(UUID id) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(id);

        // Getting the skull
        ItemStack stack = new ItemBuilder(XMaterial.PLAYER_HEAD).build();
        SkullMeta meta = (SkullMeta) stack.getItemMeta();

        // Setting the owner
        if (MinecraftServer.atLeast("1.12")) {
            meta.setOwningPlayer(player);
        } else {
            meta.setOwner(player.getName());
        }

        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getDefaultPlayerSkull() {
        return this.getSkullFrom(UUID.fromString("e5cb34a9-9f69-44ad-adeb-b81d5ce3d99e"));
    }

    public static void mutateItemMeta(SkullMeta meta, Skin skin) {
        if (MinecraftServer.atLeast("1.20.2")) {
            createProfile(meta, skin);
            return;
        }

        try {
            if (metaSetProfileMethod == null) {
                metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile");
                metaSetProfileMethod.setAccessible(true);
            }

            metaSetProfileMethod.invoke(meta, GameProfileContext.createProfile(skin));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            // if in an older API where there is no setProfile method,
            // we set the profile field directly.
            try {
                if (metaProfileField == null) {
                    metaProfileField = meta.getClass().getDeclaredField("profile");
                    metaProfileField.setAccessible(true);
                }

                metaProfileField.set(meta, GameProfileContext.createProfile(skin));
            } catch (NoSuchFieldException | IllegalAccessException ex2) {
                DisguiseAPI.getLogger().error("Failed to mutate the given meta object.", ex);
            }
        }
    }

    private static void createProfile(SkullMeta meta, Skin skin) {
        final PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "DisguiseAPI");
        final PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(new URL(skin.getUrl()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        meta.setOwnerProfile(profile);
    }
}
