package net.pinger.disguise.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class ConverterUtil {

    /**
     * This is the pattern which the {@link UUID} type follows
     */

    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    /**
     * Converts a given string to a new {@link UUID}.
     *
     * @param id the given string
     * @return the uuid
     */

    public static UUID fromString(String id) {
        if (UUID_PATTERN.matcher(id).matches())
            return UUID.fromString(id);


        id = id.substring(0, 8) + "-" +
                id.substring(8, 12) + "-" +
                id.substring(12, 16) + "-" +
                id.substring(16, 20) + "-" +
                id.substring(20, 32);

        return UUID.fromString(id);
    }

}
