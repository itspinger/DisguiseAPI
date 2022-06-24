package net.pinger.disguise.packet;

import com.google.common.collect.Sets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.server.MinecraftServer;

import java.util.Set;

public class PacketContextImpl implements PacketContext {

    private PacketProvider<?> provider;
    private final Set<Class<? extends PacketProvider<?>>> registeredProviders = Sets.newHashSet();

    public PacketContextImpl() {
        // Add default providers here
    }

    @Override
    public void applyProvider() {
        // Search for the packet provider
        // By looping through each registered provider
        for (Class<?> clazz : registeredProviders) {
            try {
                // Get the PacketHandler annotation
                // And check if versions match
                PacketHandler packetHandler = clazz.getAnnotation(PacketHandler.class);

                // Case if the handler is null
                // We don't want to continue
                if (packetHandler == null) {
                    continue;
                }

                // Check if the direct version matches
                if (MinecraftServer.isVersion(packetHandler.version())) {
                    this.provider = (PacketProvider<?>) clazz.getConstructor().newInstance();
                    return;
                }

                // Now check for compatibility matching
                // Otherwise throw an error
                for (String serverVersion : packetHandler.compatibility()) {
                    if (MinecraftServer.isVersion(serverVersion)) {
                        this.provider = (PacketProvider<?>) clazz.getConstructor().newInstance();
                        return;
                    }
                }
            } catch (Exception e) {
                DisguiseAPI.getLogger().error("", e);
            }
        }
    }

    @Override
    public void registerProvider(Class<? extends PacketProvider<?>> providerClass, boolean replace) {
        // Check if the provider has the PacketHandler annotation
        PacketHandler packetHandler = providerClass.getAnnotation(PacketHandler.class);

        // Break this
        if (packetHandler == null) {
            return;
        }

        if (!replace) {
            this.registeredProviders.add(providerClass);
            return;
        }

        // Remove condition
        this.registeredProviders.removeIf(clazz -> {
            PacketHandler other = clazz.getAnnotation(PacketHandler.class);

            // Return condition
            return other != null && other.version().equalsIgnoreCase(packetHandler.version());
        });
    }

    @Override
    public void registerProvider(Class<? extends PacketProvider<?>> providerClass) {
        this.registerProvider(providerClass, true);
    }

    @Override
    public PacketProvider<?> getProvider() {
        return provider;
    }
}