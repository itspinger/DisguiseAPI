package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.packet.PacketContextImpl;
import net.pinger.disguise.packet.PacketProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DisguisePlugin extends JavaPlugin implements Disguise {

    // The disguise plugin logger
    private final Logger logger = LoggerFactory.getLogger("DisguiseAPI");
    private final PacketContextImpl packetContext = new PacketContextImpl();

    @Override
    public void onEnable() {
        // Enable the plugin
        DisguiseAPI.setDisguise(this);

        // Get the number of providers
        Set<Class<? extends PacketProvider>> providers = packetContext.getRegisteredProviders();
        logger.info(String.format("Loaded providers: %s", providers.size()));
        logger.info("Searching for the PacketProvider corresponding with this version...");

        // Try to apply the provider
        PacketProvider provider = this.packetContext.applyProvider();
    }


    @Override
    public PacketContext getPacketContext() {
        return this.packetContext;
    }

    @Override
    public Logger getSimpleLogger() {
        return this.logger;
    }
}
