package net.pinger.disguise;

import net.pinger.disguise.skin.Skin;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public interface DisguisePlayer {

    /**
     * This method returns the default name of this player.
     *
     * @return the default name
     */

    String getDefaultName();

    /**
     * This method returns the {@link Skin} default skin of the player.
     *
     * @return the default skin of this player
     */

    Skin getDefaultSkin();

    /**
     * This method returns the {@link Skin} the player is currently wearing.
     *
     * @throws IllegalArgumentException if the player is not online
     * @return the skin currently worn by the player
     */

    @Nullable
    Skin getCurrentSkin();

    /**
     * This method returns the {@link UUID id} of this player.
     *
     * @return the id of this user
     */

    UUID getId();

    /**
     * This method returns the {@link Player bukkit player} from the given
     * {@link DisguisePlayer disguise player}.
     *
     * @return the bukkit player
     */

    Player toBukkit();

}
