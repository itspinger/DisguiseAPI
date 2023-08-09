package net.pinger.disguise;

import net.pinger.disguise.listener.PlayerListener;
import net.pinger.disguise.packet.PacketManagerImpl;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.PlayerManager;
import net.pinger.disguise.player.PlayerManagerImpl;
import net.pinger.disguise.registration.DisguiseRegistration;
import net.pinger.disguise.registration.RegistrySystem;
import net.pinger.disguise.server.MinecraftServer;
import net.pinger.disguise.skin.SkinManager;
import net.pinger.disguise.skin.SkinManagerImpl;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisguisePlugin extends JavaPlugin implements Disguise {

    private static final Logger LOGGER = LoggerFactory.getLogger("DisguiseAPI");

    private SkinManager skinManager;
    private PlayerManager playerManager;
    private PacketProvider provider;
    private DisguiseProvider defaultProvider;
    private RegistrySystem registrySystem;

    @Override
    public void onEnable() {
        // Enable the plugin
        DisguiseAPI.setDisguise(this);

        // Set the skin manager
        this.skinManager = new SkinManagerImpl(this);
        PacketManagerImpl manager = new PacketManagerImpl(this);

        LOGGER.info(String.format("Loaded providers: %s", manager.getProviderCount()));
        LOGGER.info(String.format("Current Server Version: %s", MinecraftServer.CURRENT));
        LOGGER.info("Searching for a provider supporting this version...");

        // Try to apply the provider
        this.provider = manager.find();

        // If we didn't find the provider for this version
        // Then we need to disable this plugin
        // Other plugins should use DisguiseAPI#isEnabled to check
        // Whether the plugins were loaded
        if (this.provider == null) {
            // Send info message
            LOGGER.error("Failed to find a packet provider for this version!!!");
            LOGGER.error("Failed to find a packet provider for this version!!!");
            LOGGER.error("Failed to find a packet provider for this version!!!");

            // Disable this plugin
            this.getPluginLoader().disablePlugin(this);
            return;
        } else {
            LOGGER.info("Successfully found a provider matching this version.");
        }

        this.registrySystem = new RegistrySystem();
        this.defaultProvider = new DisguiseProviderImpl(this);
        this.playerManager = new PlayerManagerImpl(this);

        // Add metrics
        new Metrics(this, 16508);

        // Register listeners
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // If the provider for this version wasn't found
        // Then this will return null
        if (this.playerManager == null) {
            return;
        }

        // Shutdown the manager
        // In order to reset skins and names of all online player
        this.playerManager.shutdown();
    }

    @Override
    public DisguiseProvider getDefaultProvider() {
        return this.defaultProvider;
    }

    @Override
    public DisguiseProvider createProvider(DisguiseRegistration registration) {
        return new DisguiseProviderImpl(this, registration);
    }

    @Override
    public RegistrySystem getRegistrySystem() {
        return this.registrySystem;
    }

    @Override
    public PacketProvider getPacketProvider() {
        return this.provider;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    @Override
    public Logger getSimpleLogger() {
        return DisguisePlugin.LOGGER;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }
}
