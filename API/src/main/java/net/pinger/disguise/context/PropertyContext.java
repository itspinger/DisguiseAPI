package net.pinger.disguise.context;

import com.mojang.authlib.properties.Property;
import net.pinger.disguise.skin.Skin;

public class PropertyContext {

    /**
     * Creates a new property from the given skin details
     * which then can be applied to a player that is online on the server.
     *
     * @param skin the skin
     * @return a new property object
     */

    public static Property createProperty(Skin skin) {
        return new Property("textures", skin.getValue(), skin.getSignature());
    }

}
