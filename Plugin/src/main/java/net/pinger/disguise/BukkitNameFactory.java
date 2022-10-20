package net.pinger.disguise;

import com.mojang.authlib.GameProfile;
import net.pinger.disguise.exception.NameFormatException;
import net.pinger.disguise.profile.GameProfileModifier;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BukkitNameFactory implements NameFactory {

    private final DisguisePlugin plugin;

    public BukkitNameFactory(DisguisePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void changeName(Player player, String name) throws NameFormatException {
        this.updatePlayerNickname(player, name);
    }

    @Override
    public void resetNick(Player player) {
        // Each disguise player has a default name field
        // Which will allow us to revert the name of the player
        DisguisePlayer disguisePlayer = this.plugin.getPlayerManager().getDisguisePlayer(player);

        // Get the default name
        this.updatePlayerNickname(player, disguisePlayer.getDefaultName());
    }

    protected void updatePlayerNickname(Player player, String nickname) throws NameFormatException {
        // Check for length of the nickname
        int length = nickname.length();

        // Bounds are from 3 to 16
        // Anything below is too low and anything higher
        // Is generally not supported by minecraft
        // So we have to account for that
        if (!(length > 3 && length <= 16)) {
            throw new NameFormatException("The specified name does not match bounds (3, 16): " + nickname);
        }

        // Next we need to strip colors, if any are contained
        // This makes it easier to cache the names of the players
        nickname = ChatColor.stripColor(nickname);

        // Get the profile for this player
        GameProfile profile = this.plugin.getProvider().getGameProfile(player);

        // Now we can perform the operation
        // And hopefully an error isn't thrown on GameProfileModifier#modifyProfile
        GameProfileModifier.modifyProfile(profile, nickname);
        player.setDisplayName(nickname);
        player.setPlayerListName(nickname);
    }
}
