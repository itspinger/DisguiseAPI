package net.pinger.disguise.skin;

public enum SkinModel {

    STEVE("normal"),
    ALEX("slim");

    private final String type;

    SkinModel(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static SkinModel getModel(String type) {
        return type.equalsIgnoreCase("normal") ? STEVE : ALEX;
    }
}
