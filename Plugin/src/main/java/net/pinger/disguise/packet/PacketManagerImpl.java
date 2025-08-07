package net.pinger.disguise.packet;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.DisguisePlugin;
import net.pinger.disguise.annotation.PacketHandler;
import net.pinger.disguise.server.MinecraftServer;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PacketManagerImpl implements PacketManager {

    private final DisguisePlugin plugin;
    private PacketProvider provider;
    private final Set<Class<? extends PacketProvider>> registeredProviders;

    public PacketManagerImpl(DisguisePlugin plugin) {
        this.plugin = plugin;
        this.registeredProviders = new HashSet<>();

        // Add default providers here
        this.registeredProviders.addAll(Arrays.asList(
            net.pinger.disguise.packet.v1_8_8.PacketProviderImpl.class, // 1.8.8
            net.pinger.disguise.packet.v1_9_4.PacketProviderImpl.class, // 1.9.4
            net.pinger.disguise.packet.v1_10.PacketProviderImpl.class, // 1.10
            net.pinger.disguise.packet.v1_11.PacketProviderImpl.class, // 1.11
            net.pinger.disguise.packet.v1_12.PacketProviderImpl.class, // 1.12
            net.pinger.disguise.packet.v1_13.PacketProviderImpl.class, // 1.13
            net.pinger.disguise.packet.v1_13_1.PacketProviderImpl.class, // 1.13.1
            net.pinger.disguise.packet.v1_14.PacketProviderImpl.class, // 1.14
            net.pinger.disguise.packet.v1_15.PacketProviderImpl.class, // 1.15
            net.pinger.disguise.packet.v1_16_1.PacketProviderImpl.class, // 1.16.1
            net.pinger.disguise.packet.v1_16_2.PacketProviderImpl.class, // 1.16.2
            net.pinger.disguise.packet.v1_16_4.PacketProviderImpl.class, // 1.16.4
            net.pinger.disguise.packet.v1_17.PacketProviderImpl.class, // 1.17
            net.pinger.disguise.packet.v1_17_1.PacketProviderImpl.class, // 1.17.1
            net.pinger.disguise.packet.v1_18.PacketProviderImpl.class, // 1.18
            net.pinger.disguise.packet.v1_18_2.PacketProviderImpl.class, // 1.18.2
            net.pinger.disguise.packet.v1_19.PacketProviderImpl.class, // 1.19
            net.pinger.disguise.packet.v1_19_1.PacketProviderImpl.class, // 1.19.1 and 1.19.2
            net.pinger.disguise.packet.v1_19_3.PacketProviderImpl.class, // 1.19.3
            net.pinger.disguise.packet.v1_19_4.PacketProviderImpl.class, // 1.19.4
            net.pinger.disguise.packet.v1_20.PacketProviderImpl.class, // 1.20+
            net.pinger.disguise.packet.v1_20_2.PacketProviderImpl.class, // 1.20.2
            net.pinger.disguise.packet.v1_20_3.PacketProviderImpl.class, // 1.20.3
            net.pinger.disguise.packet.v1_20_6.PacketProviderImpl.class, // 1.20.6
            net.pinger.disguise.packet.v1_21.PacketProviderImpl.class, // 1.21
            net.pinger.disguise.packet.v1_21_3.PacketProviderImpl.class, // 1.21.3
            net.pinger.disguise.packet.v1_21_4.PacketProviderImpl.class, // 1.21.4
            net.pinger.disguise.packet.v1_21_5.PacketProviderImpl.class, // 1.21.5
            net.pinger.disguise.packet.v1_21_6.PacketProviderImpl.class, // 1.21.6
            net.pinger.disguise.packet.v1_21_7.PacketProviderImpl.class // 1.21.7
        ));
    }

    @Override
    public PacketProvider find() {
        // Check if the provider was already fetched
        // If so, retrieve from #getProvider
        if (this.provider != null) {
            return this.provider;
        }

        for (Class<?> clazz : this.registeredProviders) {
            try {
                // Get the PacketHandler annotation
                // For each class
                // To determine whether this is the version that we want
                PacketHandler packetHandler = clazz.getAnnotation(PacketHandler.class);

                // Check if the annotation is null
                // And skip if so
                if (packetHandler == null) {
                    continue;
                }

                // Check if the main version which this handler was built upon
                // Is equal to the server version
                // If not, check its compatibility and do the same
                if (MinecraftServer.isVersion(packetHandler.version())) {
                    return this.provider = (PacketProvider) clazz.getConstructor(Plugin.class).newInstance(this.plugin);
                }

                // Check compatibility versions
                for (String compatibilityVersion : packetHandler.compatibility()) {
                    if (MinecraftServer.isVersion(compatibilityVersion)) {
                        return this.provider = (PacketProvider) clazz.getConstructor(Plugin.class).newInstance(this.plugin);
                    }
                }
            } catch (Exception e) {
                DisguiseAPI.getLogger().info("", e);
            }
        }

        // This means that no corresponding providers were found
        // And so the plugin should fail to load
        return null;
    }

    @Override
    public PacketProvider getProvider() {
        return this.provider;
    }

    @Override
    public Set<Class<? extends PacketProvider>> getRegisteredProviders() {
        return Collections.unmodifiableSet(this.registeredProviders);
    }
}

