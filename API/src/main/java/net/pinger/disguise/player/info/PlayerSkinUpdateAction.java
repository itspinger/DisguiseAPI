package net.pinger.disguise.player.info;

import net.pinger.disguise.Skin;

public class PlayerSkinUpdateAction extends PlayerUpdateAction<Skin> {

    protected PlayerSkinUpdateAction(Skin skin) {
        super(PlayerUpdateInfoAction.SKIN, skin);
    }

}
