package net.pinger.disguise.context;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.pinger.disguise.Skin;

import java.util.UUID;

public class GameProfileContext {

    /**
     * This method creates a new game profile
     * from the provided skin object.
     *
     * @param skin the skin object
     * @return the new game profile
     */

    public static Object createProfile(Skin skin) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), "Player");
        profile.getProperties().put("textures", (Property) PropertyContext.createProperty(skin));

        // Return the profile
        return profile;
    }

}
