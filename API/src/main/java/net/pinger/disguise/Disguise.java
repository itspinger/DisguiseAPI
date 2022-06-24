package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import org.slf4j.Logger;

public interface Disguise {

    /**
     * This method returns the packet context
     * which holds all registered packet providers.
     *
     * @return the packet context
     */

    PacketContext getPacketContext();

    /**
     * This method returns the SLF4J logger.
     *
     * @return the logger
     */

    Logger getLogger();

}
