package net.pinger.disguise;

import net.pinger.disguise.listener.PlayerListener;
import net.pinger.disguise.metrics.Metrics;
import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.packet.PacketContextImpl;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.player.PlayerManager;
import net.pinger.disguise.player.PlayerManagerImpl;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DisguisePlugin extends JavaPlugin implements Disguise {

    private static final Logger LOGGER = LoggerFactory.getLogger("DisguiseAPI");

    private PacketContextImpl packetContext;
    private SkinManager skinManager;
    private PlayerManager playerManager;
    private PacketProvider provider;
    private NameFactory nameFactory;

    @Override
    public void onEnable() {
        // Enable the plugin
        DisguiseAPI.setDisguise(this);

        // Set the skin manager
        this.skinManager = new SkinManagerImpl(this);
        this.packetContext = new PacketContextImpl(this);
        this.nameFactory = new BukkitNameFactory(this);

        // Get the number of providers
        Set<Class<? extends PacketProvider>> providers = packetContext.getRegisteredProviders();
        LOGGER.info(String.format("Loaded providers: %s", providers.size()));
        LOGGER.info(String.format("Current Server Version: %s", MinecraftServer.CURRENT));
        LOGGER.info("Searching for a PacketHandler corresponding with this version...");

        // Try to apply the provider
        this.provider = this.packetContext.find();
        if (this.provider == null) {
            // Send info message
            LOGGER.error("Failed to find a PacketHandler matching with this version.");
        } else {
            LOGGER.info("Successfully found a PacketHandler matching this version.");
        }

        // Add metrics
        this.playerManager = new PlayerManagerImpl();
        new Metrics(this, 16508);

        // Register listeners
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Disable
        this.playerManager.shutdown();
    }

    @Override
    public PacketProvider getProvider() {
        return this.provider;
    }

    @Override
    public NameFactory getNameFactory() {
        return this.nameFactory;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    public PacketContext getPacketContext() {
        return this.packetContext;
    }

    @Override
    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    @Override
    public Logger getSimpleLogger() {
        return DisguisePlugin.LOGGER;
    }
}
