package net.pinger.disguise.registration;

import com.google.common.collect.Sets;
import net.pinger.disguise.DisguisePlayer;
import net.pinger.disguise.DisguiseProvider;
import net.pinger.disguise.exception.ValidationException;
import net.pinger.disguise.player.info.PlayerUpdateInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RegistrySystem {

    public static final DisguiseRegistration DEFAULT_REGISTRATION = new DefaultRegistration();

    private final Map<DisguisePlayer, Set<DisguiseRegistration>> registrations;

    public RegistrySystem() {
        this.registrations = new HashMap<>();
    }

    public boolean isCached(DisguisePlayer player, DisguiseRegistration registration) {
        Set<DisguiseRegistration> registry = this.registrations.get(player);

        // If not used currently return
        if (registry == null || registry.isEmpty()) {
            return false;
        }

        int size = registry.contains(registration) ? registry.size() - 1 : registry.size();

        // Check if the size is empty after removing the registration
        // If it is contained anyway
        return size != 0;
    }

    public boolean isCached(DisguisePlayer player) {
        Set<DisguiseRegistration> registry = this.registrations.get(player);

        // If not used currently return
        return registry != null && !registry.isEmpty();
    }

    public void validateEmpty(DisguisePlayer player, DisguiseRegistration registration) throws ValidationException {
        if (!this.isCached(player, registration)) {
            return;
        }

        throw new ValidationException("This player is already cached in another disguise plugin!");
    }

    public void validateEmpty(DisguisePlayer player) throws ValidationException {
        if (!this.isCached(player)) {
            return;
        }

        throw new ValidationException("This player is already cached in another disguise plugin!");
    }

    public void validateEmpty(PlayerUpdateInfo info, DisguiseRegistration registration) throws ValidationException {
        this.validateEmpty(info.getPlayer(), registration);
    }

    public void validateEmpty(PlayerUpdateInfo info) throws ValidationException {
        if (!this.isCached(info.getPlayer())) {
            return;
        }

        throw new ValidationException("This player is already cached!");
    }

    public void updateRegistration(DisguisePlayer player, DisguiseRegistration registration) {
        Set<DisguiseRegistration> registry = this.registrations.get(player);

        if (registry == null) {
            this.registrations.put(player, Sets.newHashSet(registration));
            return;
        }

        registry.removeIf(reg -> reg.equals(registration));

        // Add the new registration
        registry.add(registration);
    }

    public void removeRegistration(DisguisePlayer player, DisguiseRegistration registration) {
        Set<DisguiseRegistration> registry = this.registrations.get(player);

        if (registry == null || registry.isEmpty()) {
            return;
        }

        registry.removeIf(reg -> reg.equals(registration));
    }

}
