package net.pinger.disguise.packet;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.packet.exception.ProviderNotFoundException;
import net.pinger.disguise.server.MinecraftServer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PacketContext {

    private static PacketProvider<?> provider = null;

    private static final Set<Class<? extends PacketProvider<?>>> providers = Sets.newHashSet();
    private static final Map<String, List<String>> packetCompatibility = Maps.newHashMap();

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
                // Split the version of the packet
                // From the class name
                String name = clazz.getName().substring(clazz.getName().lastIndexOf("v") + 1);
                String[] splitter = name.split("_");
                String version = String.join(".", splitter);

                // Check if the server version
                // Is equal to the packet version
                if (MinecraftServer.isVersion(version)) {
                    DisguiseAPI.getLogger().info(String.format("Found the appropriate provider for version %s: %s", MinecraftServer.CURRENT.getVersion(), clazz.getName()));
                    return provider = clazz.getConstructor().newInstance();
                }

                // We also need to check for the packet compatibility
                // For the given version
                if (!packetCompatibility.containsKey(version))
                    continue;

                for (String serverVersion : packetCompatibility.get(version)) {
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
