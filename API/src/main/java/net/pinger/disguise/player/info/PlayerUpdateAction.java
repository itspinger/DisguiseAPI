package net.pinger.disguise.player.info;

public abstract class PlayerUpdateAction<T> {

    private final PlayerUpdateInfoAction action;
    private final T update;

    protected PlayerUpdateAction(PlayerUpdateInfoAction action, T update) {
        this.action = action;
        this.update = update;
    }

    public PlayerUpdateInfoAction getAction() {
        return this.action;
    }

    /**
     * This method returns the object to update the player
     * with.
     * <p>
     * For example, if a skin is being changed for the player,
     * this method will return the skin that is being set.
     *
     * @return the object to update the player
     */

    public T get() {
        return this.update;
    }
}
