package net.pinger.disguise;

import net.pinger.disguise.packet.PacketContext;
import net.pinger.disguise.packet.PacketContextImpl;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisguisePlugin extends JavaPlugin implements Disguise {

    // The disguise plugin logger
    private final Logger logger = LoggerFactory.getLogger(DisguisePlugin.class);
    private final PacketContextImpl packetContext = new PacketContextImpl();

    @Override
    public void onEnable() {
        // Do stuff here


        // Enable the plugin
        DisguiseAPI.setDisguise(this);
    }

    @Override
    public void onDisable() {
        
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
