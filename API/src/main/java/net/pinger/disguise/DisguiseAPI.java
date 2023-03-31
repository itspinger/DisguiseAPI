package net.pinger.disguise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.PlayerManager;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.registration.RegistrySystem;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.UUID;

public class DisguiseAPI {

    public static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    private static Disguise disguise;

    // Don't let anyone initialize this
    private DisguiseAPI() {}

    /**
     * This method sets the instance of the disguise field.
     *
     * @param disguise the disguise instance
     */

    public static void setDisguise(Disguise disguise) {
        DisguiseAPI.disguise = disguise;
    }

    /**
     * This method returns the default {@link DisguiseProvider} created
     * for changing player skins, names.
     * <p>
     * If you need a customized way of handling player data update,
     * you can use {@link #createProvider(DisguiseRegistration)} method.
     *
     * @return the default provider
     */

    public static DisguiseProvider getDefaultProvider() {
        return DisguiseAPI.disguise.getDefaultProvider();
    }

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

    public static DisguiseProvider createProvider(DisguiseRegistration registration) {
        return DisguiseAPI.disguise.createProvider(registration);
    }

    /**
     * This method returns the skin manager
     * responsible for fetching skins.
     *
     * @return the skin manager
     */

    public static SkinManager getSkinManager() {
        return disguise.getSkinManager();
    }

    public static RegistrySystem getRegistrySystem() {
        return disguise.getRegistrySystem();
    }

    /**
     * This method returns the current provider, if possible.
     * <p>
     * If your plugin is depending on DisguiseAPI, it is important to check
     * whether this method returns null in the <b>onEnable</b> method.
     *
     * You may not need to store this inside a static field, since
     * it will not change once it has been set until the restart of the server.
     * <br>
     * <b>Correct</b> way of checking the provider would be:
     * <pre>
     *         if (provider == null) {
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *
     *             // Disable the plugin
     *             this.getPluginLoader().disablePlugin(this);
     *             return;
     *         }
     * </pre>
     *
     * @return the current provider
     */

    public static PacketProvider getProvider() {
        return disguise.getPacketContext().getProvider();
    }

    public static PlayerManager getPlayerManager() {
        return disguise.getPlayerManager();
    }

    public static DisguisePlayer getDisguisePlayer(Player player) {
        return getPlayerManager().getDisguisePlayer(player);
    }

    public static DisguisePlayer getDisguisePlayer(UUID id) {
        return getPlayerManager().getDisguisePlayer(id);
    }

    public static NameFactory getNameFactory() {
        return disguise.getNameFactory();
    }

    /**
     * This method returns the standard logger
     * of the api.
     *
     * @return the logger
     */

    public static Logger getLogger() {
        return disguise.getSimpleLogger();
    }
}
