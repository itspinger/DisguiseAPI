package net.pinger.disguise.player.info;

import net.pinger.disguise.DisguisePlayer;
import net.pinger.disguise.skin.Skin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerUpdateInfo {

    private final DisguisePlayer player;
    private final List<PlayerUpdateAction<?>> actions;

    public PlayerUpdateInfo(DisguisePlayer player, PlayerUpdateAction<?>... actions) {
        this.player = player;
        this.actions = new ArrayList<>(Arrays.asList(actions));
    }

    public PlayerUpdateInfo(DisguisePlayer player, Skin skin, String name) {
        this.player = player;
        this.actions = new ArrayList<>();

        if (skin != null) {
            this.actions.add(new PlayerSkinUpdateAction(skin));
        }

        if (name != null) {
            this.actions.add(new PlayerNameUpdateAction(name));
        }
    }

    public PlayerUpdateInfo(DisguisePlayer player, Skin skin) {
        this(player, skin, null);
    }

    public PlayerUpdateInfo(DisguisePlayer player, String name) {
        this(player, null, name);
    }

    public DisguisePlayer getPlayer() {
        return this.player;
    }

    @SuppressWarnings("unchecked")
    private <T> PlayerUpdateAction<T> getUpdate(PlayerUpdateInfoAction action) {
        for (PlayerUpdateAction<?> update : this.actions) {
            if (action == update.getAction()) {
                return (PlayerUpdateAction<T>) update;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> PlayerUpdateAction<T> getUpdate(Class<? extends T> classifier) {
        for (PlayerUpdateAction<?> update : this.actions) {
            if (update.get().getClass() != classifier) {
                continue;
            }

            return (PlayerUpdateAction<T>) update;
        }

        return null;
    }

    @Nullable
    public PlayerSkinUpdateAction getSkinUpdate() {
        PlayerUpdateAction<Skin> update = this.getUpdate(PlayerUpdateInfoAction.SKIN);

        // Return null if so
        if (update == null) {
            return null;
        }

        return (PlayerSkinUpdateAction) update;
    }

    @Nullable
    public Skin getSkin() {
        PlayerSkinUpdateAction skin = this.getSkinUpdate();

        if (skin == null) {
            return null;
        }

        return skin.get();
    }

    @Nullable
    public PlayerNameUpdateAction getNameUpdate() {
        PlayerUpdateAction<String> update = this.getUpdate(PlayerUpdateInfoAction.NICK);

        // Return null if so
        if (update == null) {
            return null;
        }

        return (PlayerNameUpdateAction) update;
    }

    @Nullable
    public String getName() {
        PlayerNameUpdateAction name = this.getNameUpdate();

        if (name == null) {
            return null;
        }

        return name.get();
    }

    public boolean hasSkinUpdate() {
        return this.getSkinUpdate() != null;
    }

    public boolean hasNameUpdate() {
        return this.getNameUpdate() != null;
    }

}
