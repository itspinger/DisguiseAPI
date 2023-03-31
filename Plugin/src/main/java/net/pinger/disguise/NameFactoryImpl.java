package net.pinger.disguise;

import net.pinger.disguise.exception.NameFormatException;
import org.bukkit.entity.Player;

@Deprecated
public class NameFactoryImpl implements NameFactory {

    private final DisguisePlugin plugin;

    public NameFactoryImpl(DisguisePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @Deprecated
    public void changeName(Player player, String name) throws NameFormatException {
        DisguiseAPI.getDefaultProvider().updatePlayer(player, name);
    }

    @Override
    @Deprecated
    public void resetNick(Player player) {
        DisguiseAPI.getDefaultProvider().resetPlayer(player, false, true);
    }
}
