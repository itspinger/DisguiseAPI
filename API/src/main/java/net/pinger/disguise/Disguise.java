package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.PlayerManager;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.registration.RegistrySystem;
import org.slf4j.Logger;

public interface Disguise {

    /**
     * This method returns the default {@link DisguiseProvider} created
     * for changing player skins, names.
     * <p>
     * If you need a customized way of handling player data update,
     * you can use {@link #createProvider(DisguiseRegistration)} method.
     *
     * @return the default provider
     */

    DisguiseProvider getDefaultProvider();

    /**
     * This method creates a new {@link DisguiseProvider} for updating
     * player skins, names.
     * <p>
     * Before using this method, make sure to read the documentation
     * of {@link DisguiseRegistration}. If you don't need custom registration
     * and validation for player property updates, you can use {@link #getDefaultProvider()}
     * to use the default registration created for that purpose.
     *
     * @param registration the registration
     * @return the created provider
     */

    DisguiseProvider createProvider(DisguiseRegistration registration);

    /**
     * This method returns
     *
     * @return
     */

    RegistrySystem getRegistrySystem();

    /**
     * This method returns the {@link PacketProvider provider} applied for this
     * server version.
     *
     * @see PacketProvider
     * @return the packet provider for this version
     */

    PacketProvider getPacketProvider();

    /**
     * This method returns the factory for changing player names.
     *
     * @return the factory
     */

    NameFactory getNameFactory();

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
