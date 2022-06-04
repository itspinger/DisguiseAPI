package net.pinger.disguise.context;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.pinger.disguise.Skin;
import net.pinger.disguise.server.MinecraftServer;

import java.util.UUID;

public class GameObjectContext {

    /**
     * This method creates a new game profile
     * from the provided skin object.
     *
     * @param skin the skin object
     * @return the new game profile
     */

    public static Object createProfile(Skin skin) {
        UUID id = UUID.randomUUID();

        if (MinecraftServer.atLeast("1.8")) {
            GameProfile profile = new GameProfile(id, "Player");
            profile.getProperties().put("textures", (Property) PropertyContext.createProperty(skin));

            // Return the profile
            return profile;
        }

        net.minecraft.util.com.mojang.authlib.GameProfile profile = new net.minecraft.util.com.mojang.authlib.GameProfile(id, "Player");
        profile.getProperties().put("textures", (net.minecraft.util.com.mojang.authlib.properties.Property) PropertyContext.createProperty(skin));

        // Return the profile
        return profile;
    }

}
