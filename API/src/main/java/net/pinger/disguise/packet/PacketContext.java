package net.pinger.disguise.packet;

import com.google.common.collect.Sets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.server.MinecraftServer;

import java.util.Set;

public class PacketContext {

    private PacketProvider<?> provider;
    private final Set<Class<? extends PacketProvider<?>>> registeredProviders = Sets.newHashSet();

    public PacketContext() {
        // Add default providers here
    }

    /**
     * This method is used to assign a value to the {@link PacketProvider} field
     * which manages sent packets for disguised
     */

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

    /**
     * This method registers the given provider, which will be instantiated
     * once the {@link #applyProvider()} is called if the versions match.
     * <p>
     * If the replace boolean is true, this version will replace any other class
     * that matches the provided version of this one. By default, the value is true.
     *
     * @param providerClass the class of the provider
     * @param replace whether we should replace any other classes with this noe
     */

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

    /**
     * This method registers the given provider.
     *
     * @param providerClass the given provider
     */

    public void registerProvider(Class<? extends PacketProvider<?>> providerClass) {
        this.registerProvider(providerClass, true);
    }

    public PacketProvider<?> getProvider() {
        return provider;
    }
}
