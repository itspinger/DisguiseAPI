package net.pinger.disguise.profile;

import com.mojang.authlib.GameProfile;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.NameFactory;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class GameProfileModifier {

    /**
     * This field is holds the name of the player. Once the field has been changed,
     * some packets might need to be sent, in order for it to get applied correctly.
     *
     * @since 1.1.3
     */

    private static Field profileNameField;

    /**
     * This method changes the profile name to the specified name.
     * <p>
     * Note that this method might be unsafe to use directly, because it does not
     * prevent certain errors from occurring. Instead, use {@link NameFactory#changeName(Player, String)}
     * when accounting for safety.
     *
     * @param profile the profile of this player
     * @param name the name of the
     */

    public static void modifyProfile(GameProfile profile, String name) {
        try {
            if (profileNameField == null) {
                profileNameField = profile.getClass().getDeclaredField("name");
            }

            profileNameField.setAccessible(true);
            profileNameField.set(profile, name);
        } catch (Exception e) {
            DisguiseAPI.getLogger().info("Failed to update player nickname", e);
        }
    }

}
