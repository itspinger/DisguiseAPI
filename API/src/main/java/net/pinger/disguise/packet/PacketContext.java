package net.pinger.disguise.packet;

public interface PacketContext {


    /**
     * This method is used to assign a value to the {@link PacketProvider} field
     * which manages sent packets for disguised
     */

    void applyProvider();

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

    void registerProvider(Class<? extends PacketProvider<?>> providerClass, boolean replace);

    /**
     * This method registers the given provider.
     *
     * @param providerClass the given provider
     */

    void registerProvider(Class<? extends PacketProvider<?>> providerClass);

    /**
     * This method returns the current provider.
     *
     * @return the provider
     */

    PacketProvider<?> getProvider();
}
