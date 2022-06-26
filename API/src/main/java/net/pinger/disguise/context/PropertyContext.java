package net.pinger.disguise.context;

import com.mojang.authlib.properties.Property;
import net.pinger.disguise.Skin;

public class PropertyContext {

    /**
     * Creates a new property from the given skin details
     * which then can be applied to a player that is online on the server.
     * <p>
     * Note that after 1.7 Minecraft version the Property package
     * has been changed from {@link net.minecraft.util.com.mojang.authlib.properties.Property} to {@link com.mojang.authlib.properties.Property}.
     *
     * @param skin the skin
     * @return a new property object
     */

    public static Object createProperty(Skin skin) {
        return new Property("textures", skin.getValue(), skin.getSignature());
    }

}
