package net.pinger.disguise.player.info;

public class PlayerNameUpdateAction extends PlayerUpdateAction<String> {
    protected PlayerNameUpdateAction(String name) {
        super(PlayerUpdateInfoAction.NICK, name);
    }
}
