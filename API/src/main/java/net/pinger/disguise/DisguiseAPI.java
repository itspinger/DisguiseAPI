package net.pinger.disguise;

import net.pinger.disguise.packet.PacketProvider;
import org.slf4j.Logger;

public class DisguiseAPI {

    private static Disguise disguise;

    // Don't let anyone initialize this
    private DisguiseAPI() {}

    /**
     * This method sets the instance of the disguise field.
     *
     * @param disguise the disguise instance
     */

    public static void setDisguise(Disguise disguise) {
        DisguiseAPI.disguise = disguise;
    }

    /**
     * This method is used to assign a value to the {@link PacketProvider} field
     * which manages sent packets for disguised
     */

    public void applyProvider() {
        disguise.getPacketContext().applyProvider();
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

    public static void registerProvider(Class<? extends PacketProvider<?>> providerClass, boolean replace) {
        disguise.getPacketContext().registerProvider(providerClass, replace);
    }

    /**
     * This method registers the given provider.
     *
     * @param providerClass the given provider
     */


    public static void registerProvider(Class<? extends PacketProvider<?>> providerClass) {
        disguise.getPacketContext().registerProvider(providerClass);
    }

    /**
     * This method returns the current provider.
     *
     * @return the provider
     */

    public static PacketProvider<?> getProvider() {
        return disguise.getPacketContext().getProvider();
    }

    /**
     * This method returns the standard logger
     * of the api.
     *
     * @return the logger
     */

    public static Logger getLogger() {
        return disguise.getSimpleLogger();
    }
}
