package net.pinger.disguise.packet;

import com.google.common.collect.Sets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.exception.ProviderNotFoundException;
import net.pinger.disguise.server.MinecraftServer;

import java.util.Set;

public final class PacketContext {

    private static PacketProvider<?> provider = null;
    private static final Set<Class<? extends PacketProvider<?>>> providers = Sets.newHashSet();

    /**
     * This method returns the provider corresponding
     * with the current server version.
     *
     * @return the packet provider
     * @throws ProviderNotFoundException if the provider was not found
     */

    public static PacketProvider<?> getProvider() throws ProviderNotFoundException {
        if (provider != null)
            return provider;

        for (Class<? extends PacketProvider<?>> clazz : providers) {
            try {
                // Get the PacketHandler annotation
                PacketHandler handler = clazz.getAnnotation(PacketHandler.class);

                // Check if the handler is null
                if (handler == null) {
                    return null;
                }

                // Check if the server version
                // Is equal to the packet version
                if (MinecraftServer.isVersion(handler.version())) {
                    DisguiseAPI.getLogger().info(String.format("Found the appropriate provider for version %s: %s", MinecraftServer.CURRENT.getVersion(), clazz.getName()));
                    return provider = clazz.getConstructor().newInstance();
                }

                // Loop through compatible versions
                for (String serverVersion : handler.compatibility()) {
                    if (MinecraftServer.isVersion(serverVersion)) {
                        // Then the current class is also compatible with the current version
                        DisguiseAPI.getLogger().info(String.format("Found the appropriate provider for version %s: %s", MinecraftServer.CURRENT.getVersion(), clazz.getName()));
                        return provider = clazz.getConstructor().newInstance();
                    }
                }
            } catch (Exception e) {
                DisguiseAPI.getLogger().error("An error occurred while trying to find an applicable provider for version: " + MinecraftServer.CURRENT.getVersion());
                DisguiseAPI.getLogger().error(e.getMessage());
            }
        }

        throw new ProviderNotFoundException();
    }

}
