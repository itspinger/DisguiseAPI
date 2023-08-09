package net.pinger.disguise.context;

import com.mojang.authlib.GameProfile;
import net.pinger.disguise.skin.Skin;

import java.util.UUID;

public class GameProfileContext {

    /**
     * This method creates a new game profile
     * from the provided skin object.
     *
     * @param skin the skin object
     * @return the new game profile
     */

    public static GameProfile createProfile(Skin skin) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), "Player");
        profile.getProperties().put("textures", PropertyContext.createProperty(skin));

        // Return the profile
        return profile;
    }

}
