package net.pinger.disguise.server;

import net.pinger.disguise.util.Arrays;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class MinecraftServer implements Comparable<MinecraftServer> {

    /**
     * A pattern for extraction of {@link MinecraftServer} versions
     */

    public static final Pattern VERSION_PATTERN = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.?\\d{0,3}", Pattern.MULTILINE);

    /**
     * Returns the wrapper of the current {@link MinecraftServer} version
     */

    public static final MinecraftServer CURRENT = MinecraftServer.fromRaw();

    private final String version;
    private final Integer[] splitter;

    public MinecraftServer(String version) {
        this.version = version;

        // Convert to an array
        this.splitter = Stream.of(version.split("\\."))
                .filter(o -> !o.isEmpty())
                .map(Integer::parseInt)
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
        // Check if it's WindSpigot
        Matcher matcher = VERSION_PATTERN.matcher(rawVersion);

        // Check if the version pattern
        // Found a match for this input string
        if (matcher.find()) {
            return new MinecraftServer(matcher.group(0));
        }

        // Throw an error
        // If the version is invalid
        throw new IllegalArgumentException("Failed to parse Spigot version: " + rawVersion);
    }

    /**
     * This method creates a new instance of this class
     * by using the {@link Bukkit#getVersion()} version.
     *
     * @return the new instance
     */

    public static MinecraftServer fromRaw() {
        return fromRaw(Bukkit.getBukkitVersion());
    }

    /**
     * This method checks if the current server version
     * is equal to the specified version.
     * <p>
     * Note that the specified string should be split,
     * not given by the raw value. For example: 1.7.10, 1.8.8
     *
     * @param otherVersion the version to compare to
     * @return if the server version is equal to the specified one
     */

    public static boolean isVersion(String otherVersion) {
        return CURRENT.compareTo(new MinecraftServer(otherVersion)) == 0;
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
        return Arrays.compare(this.splitter, o.splitter);
    }

    @Override
    public String toString() {
        return this.version;
    }
}
