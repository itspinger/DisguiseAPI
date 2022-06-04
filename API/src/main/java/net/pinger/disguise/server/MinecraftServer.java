package net.pinger.disguise.server;

import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class MinecraftServer implements Comparable<MinecraftServer> {

    private static final MinecraftServer CURRENT = MinecraftServer.fromRaw();

    private final String version;
    private final Integer[] splitter;

    public MinecraftServer(String version) {
        this.version = version;

        // Convert to an array
        this.splitter = Arrays.stream(version.split("\\."))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }

    /**
     * This method creates a new instance of this class
     * by using the rawVersion from the BukkitAPI.
     *
     * @param rawVersion the rawVersion
     * @return the new instance
     */

    public static MinecraftServer fromRaw(String rawVersion) {
        return new MinecraftServer(rawVersion.substring(rawVersion.indexOf("(MC: " + 5), rawVersion.length() - 1));
    }

    /**
     * This method creates a new instance of this class
     * by using the {@link Bukkit#getVersion()} version.
     *
     * @return the new instance
     */

    public static MinecraftServer fromRaw() {
        return fromRaw(Bukkit.getVersion());
    }

    /**
     * This method checks if the current server version
     * is greater or equal to the specified version by
     * comparing the two {@link MinecraftServer} references.
     *
     * @param otherServer the specified server version
     * @return the compared value
     */

    public static boolean atLeast(MinecraftServer otherServer) {
        return CURRENT.compareTo(otherServer) >= 0;
    }

    /**
     * This method checks if the current server version
     * is greater or equal to the specified version.
     *
     * @param otherVersion the specified version
     * @return the compared value
     */

    public static boolean atLeast(String otherVersion) {
        return CURRENT.compareTo(new MinecraftServer(otherVersion)) >= 0;
    }

    /**
     * This method returns the version of this
     * minecraft server.
     *
     * @return the version
     */

    public String getVersion() {
        return version;
    }

    @Override
    public int compareTo(@Nonnull MinecraftServer o) {
        if (this.version.equalsIgnoreCase(o.version)) {
            return 0;
        }

        return Arrays.compare(this.splitter, o.splitter);
    }
}
