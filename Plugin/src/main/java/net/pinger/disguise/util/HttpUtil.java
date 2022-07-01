package net.pinger.disguise.util;

import java.util.UUID;

public class HttpUtil {

    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    private static final String MOJANG_NAME_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String MOJANG_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    /**
     * This method returns a new mineskin url used for requesting new skins.
     *
     * @param url the url of the image
     * @return the url of the request
     */

    public static String toMineskin(String url) {
        return String.format(MINESKIN_URL, url);
    }

    /**
     * Returns a {@link String} which represents a formatted url to the mojang servers.
     * It specifically formats the {@link HttpUtil#MOJANG_URL} with the specified uuid.
     *
     * @param uuid the uuid of the player
     * @return the formatted string
     */

    public static String toMojangUrl(UUID uuid) {
        return String.format(MOJANG_URL, uuid);
    }

    /**
     * Returns a new string which represents a formatted url pointing to the uuid of this player
     * within the mojang servers.
     *
     * @param s the name of the player
     * @return the url
     */

    public static String toMojangUrl(String s) {
        return String.format(MOJANG_NAME_URL, s);
    }


}
