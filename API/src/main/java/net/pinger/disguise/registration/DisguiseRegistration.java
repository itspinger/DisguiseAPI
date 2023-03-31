package net.pinger.disguise.registration;

import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.exception.ValidationException;
import net.pinger.disguise.player.info.PlayerUpdateInfo;

public abstract class DisguiseRegistration {

    protected final RegistrySystem registry;

    public DisguiseRegistration() {
        this.registry = DisguiseAPI.getRegistrySystem();
    }

    /**
     * This method validates the player information for this specific
     * registration. Throws exception for failure, otherwise validation is successful.
     * <p>
     * Before the update is actually received in the {@link #onPlayerUpdateInfo(PlayerUpdateInfo)},
     * this method will be called to check whether the info can be applied
     * to the player.
     * <p>
     * An example usage of a good validation would be to check whether
     * the player is already cached in another registration, for the reason
     * that if two plugins are performing updates on the same player,
     * those two plugins may collide.
     * <p>
     * Note that this validation is not called before
     *
     * @param info the player info that is created
     */

    public void validatePlayerInfo(PlayerUpdateInfo info) throws ValidationException {

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

    /**
     * This method is used to handle data reset of players.
     * This can be only nick, only skin or both skin and nick.
     * <p>
     * To determine which type it is you can check can use
     * the methods inside {@link PlayerUpdateInfo}:
     * <ul>
     *     <li>{@link PlayerUpdateInfo#hasNameUpdate()}</li>
     *     <li>{@link PlayerUpdateInfo#hasSkinUpdate()}</li>
     * </ul>
     * <p>
     * You can use {@link RegistrySystem#DEFAULT_REGISTRATION} if you don't
     * need custom handling of this event.
     *
     * @param info the info of the reset update
     */

    public abstract void onPlayerResetInfo(PlayerUpdateInfo info);
}
