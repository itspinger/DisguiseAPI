package net.pinger.disguise.registration;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class RegistrySystem {

    public static final DisguiseRegistration DEFAULT_REGISTRATION = new DefaultRegistration();

    private final Map<JavaPlugin, DisguiseRegistration> registrations;

    public RegistrySystem() {
        this.registrations = new HashMap<>();
    }

    public DisguiseRegistration getRegistration(Class<? extends JavaPlugin> classifier) {
        return this.registrations.get(JavaPlugin.getPlugin(classifier));
    }

    public DisguiseRegistration getRegistration(JavaPlugin plugin) {
        return this.registrations.get(plugin);
    }

}
