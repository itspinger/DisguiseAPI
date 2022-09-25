package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.player.PlayerManager;
import org.slf4j.Logger;

public interface Disguise {

    PlayerManager getPlayerManager();

    /**
     * This method returns the packet context
     * which holds all registered packet providers.
     *
     * @return the packet context
     */

    PacketContext getPacketContext();

    /**
     * This method returns the skin manager
     * responsible for fetching skins.
     *
     * @return the skin manager
     */

    SkinManager getSkinManager();

    /**
     * This method returns the SLF4J logger.
     *
     * @return the logger
     */

    Logger getSimpleLogger();

}
