package net.pinger.disguise.packet.exception;

import net.pinger.disguise.server.MinecraftServer;

public class ProviderNotFoundException extends Exception {

    public ProviderNotFoundException() {
        super("Failed to find a packet provider for the version: " + MinecraftServer.CURRENT.getVersion());
    }

}
