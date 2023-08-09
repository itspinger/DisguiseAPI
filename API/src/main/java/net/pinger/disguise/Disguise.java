package net.pinger.disguise;

import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.PlayerManager;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.registration.RegistrySystem;
import net.pinger.disguise.skin.SkinManager;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;

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
     * This method returns the registry system responsible for handling
     * different Disguise plugins at once.
     * <p>
     * This is useful for keeping event handlers upon disguises, but
     * also limiting when players can, and cannot disguise.
     * <p>
     * For additional information, you can read the documentation
     * of {@link DisguiseRegistration}.
     *
     * @return the registry system
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
     * This method returns the player manager.
     *
     * @return the player manager
     */

    PlayerManager getPlayerManager();

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

    /**
     * This method returns a plugin instance, which handles everything
     * within this api.
     * <p>
     * If we failed to find a provider for this plugin, the
     * {@link #isEnabled()} method will return false, since the plugin
     * will be disabled.
     *
     * @return the plugin
     */

    Plugin getPlugin();

    /**
     * This method checks whether this plugin is enabled.
     * <p>
     * A good reason to check the return value of this method is
     * to see if the provider is found.
     *
     * @return whether the api is enabled
     */

    @CheckReturnValue
    default boolean isEnabled() {
        return this.getPlugin().isEnabled();
    }

}
