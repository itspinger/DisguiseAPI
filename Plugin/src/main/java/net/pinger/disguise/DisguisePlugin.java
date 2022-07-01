package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.packet.PacketContextImpl;
import net.pinger.disguise.packet.PacketProvider;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DisguisePlugin extends JavaPlugin implements Disguise {

    // The disguise plugin logger
    private final Logger logger = LoggerFactory.getLogger("DisguiseAPI");
    private final PacketContextImpl packetContext = new PacketContextImpl();
    private SkinManager skinManager;

    @Override
    public void onEnable() {
        // Enable the plugin
        DisguiseAPI.setDisguise(this);

        // Set the skin manager
        this.skinManager = new SkinManagerImpl(this);

        // Get the number of providers
        Set<Class<? extends PacketProvider>> providers = packetContext.getRegisteredProviders();
        logger.info(String.format("Loaded providers: %s", providers.size()));
        logger.info(String.format("Current Server Version: %s", MinecraftServer.CURRENT.getVersion()));
        logger.info("Searching for a PacketHandler corresponding with this version...");

        // Try to apply the provider
        PacketProvider provider = this.packetContext.applyProvider();
        if (provider == null) {
            // Send info message
            logger.error("Failed to find a PacketHandler matching with this version.");
        } else {
            logger.info("Successfully found a PacketHandler matching this version.");
        }
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
        return this.logger;
    }
}
