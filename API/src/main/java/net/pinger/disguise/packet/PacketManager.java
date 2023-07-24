package net.pinger.disguise.packet;

import java.util.Set;

public interface PacketManager {

    /**
     * This method tries to find a {@link PacketProvider} amongst
     * all providers in {@link #getRegisteredProviders()}.
     * <p>
     * The search for the provider is done inside the DisguisePlugin class,
     * and if the current version of the server doesn't match any providers,
     * both this and {@link #getProvider()} will return null.
     * <br>
     * Note that your plugin should not execute this method,
     * but rather look for the return value of {@link #getProvider()}.
     * <br>
     * If this method fails and {@link #getProvider()} returns null,
     * you should disable your plugin.
     *
     * @return the packet provider
     */

    PacketProvider find();

    /**
     * This method returns the current provider, if possible.
     * <p>
     * If your plugin is depending on DisguiseAPI, it is important to check
     * whether this method returns null in the <b>onEnable</b> method.
     * <p>
     * You may not need to store this inside a static field, since
     * it will not change once it has been set until the restart of the server.
     * <br>
     * <b>Correct</b> way of checking the provider would be:
     * <pre>
     *         if (provider == null) {
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *             getOutput().info("FAILED TO FIND A PACKET PROVIDER!!!");
     *
     *             // Disable the plugin
     *             this.getPluginLoader().disablePlugin(this);
     *             return;
     *         }
     * </pre>
     *
     * @return the current provider
     */

    PacketProvider getProvider();

    /**
     * Returns a {@link Set} of registered providers.
     * <p>
     * Do note that this method will return a {@link com.google.common.collect.ImmutableSet},
     * so trying to modify this list will throw a {@link UnsupportedOperationException} exception.
     *
     * @return the set of registered providers
     */

    Set<Class<? extends PacketProvider>> getRegisteredProviders();

    /**
     * This method returns the number of currently registered
     * providers.
     *
     * @return the provider count
     */

    default int getProviderCount() {
        return this.getRegisteredProviders().size();
    }
}
