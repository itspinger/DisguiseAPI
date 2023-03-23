package net.pinger.disguise.registration;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.player.info.PlayerUpdateInfo;

public abstract class DisguiseRegistration {

    protected final RegistrySystem registry;

    public DisguiseRegistration() {
        this.registry = DisguiseAPI.getRegistrySystem();
    }

    /**
     * This method validates the player information for this specific
     * registration. Return true for success validation, false for failure.
     * <p>
     * Before the update is actually received in the {@link #onPlayerUpdateInfo(PlayerUpdateInfo)},
     * this method will be called to check whether the info can be applied
     * to the player.
     * <p>
     * An example usage of a good validation would be to check whether
     * the player is already cached in another registration, for the reason
     * that if two plugins are performing updates on the same player,
     * those two plugins may collide.
     *
     * @param info the player info that is created
     * @return true if the validation is good, otherwise false
     */

    public boolean validatePlayerInfo(PlayerUpdateInfo info) {
        return true;
    }

    /**
     * This method is used to handle any change in players
     * data (skin, name...).
     * It is called right after the data is changed, and it is
     * up to the user of the registration to handle the info
     * however they want to.
     * <p>
     * If you don't need this and {@link #validatePlayerInfo(PlayerUpdateInfo)},
     * you can use {@link RegistrySystem#DEFAULT_REGISTRATION}.
     *
     * @param info the info of the update
     */

    public abstract void onPlayerUpdateInfo(PlayerUpdateInfo info);
}
