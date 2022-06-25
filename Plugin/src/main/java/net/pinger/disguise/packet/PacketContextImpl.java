package net.pinger.disguise.packet;

import com.google.common.collect.Sets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.packet.v1_12.PacketProviderImpl;
import net.pinger.disguise.server.MinecraftServer;

import java.util.Arrays;
import java.util.Set;

public class PacketContextImpl implements PacketContext {

    private PacketProvider<?> provider;
    private final Set<Class<? extends PacketProvider<?>>> registeredProviders = Sets.newHashSet();

    public PacketContextImpl() {
        // Add default providers here
        this.registeredProviders.addAll(Arrays.asList(
                net.pinger.disguise.packet.v1_8_8.PacketProviderImpl.class, // 1.8.8
                net.pinger.disguise.packet.v1_9_4.PacketProviderImpl.class, // 1.9.4
                net.pinger.disguise.packet.v1_10.PacketProviderImpl.class, // 1.10
                net.pinger.disguise.packet.v1_11.PacketProviderImpl.class, // 1.11
                net.pinger.disguise.packet.v1_12.PacketProviderImpl.class, // 1.12
                net.pinger.disguise.packet.v1_13.PacketProviderImpl.class, // 1.13
                net.pinger.diguise.packet.v1_13_1.PacketProviderImpl.class, // 1.13.1
                net.pinger.disguise.packet.v1_14.PacketProviderImpl.class // 1.14
        ));
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
