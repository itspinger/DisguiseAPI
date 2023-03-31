package net.pinger.disguise;

import net.pinger.disguise.exception.NameFormatException;
import org.bukkit.entity.Player;

@Deprecated
public interface NameFactory {

    /**
     * This method changes the name of the {@link Player} to the specified name.
     * <p>
     * If the provided name contains more than 16 characters or contains colors,
     * a {@link NameFormatException} will be thrown and the execution will not be possible.
     *
     * @param player the player to change the nick for
     * @param name the name of the
     */

    void changeName(Player player, String name) throws NameFormatException;

    /**
     * This method resets the nickname of the specified player. If you instead
     * need to change the name of the player, use {@link #changeName(Player, String)}
     * for that.
     *
     * <p>
     * Currently, no rules are written for overriding already existing names.
     *
     * @param player the player
     */

    void resetNick(Player player);

}
