package net.pinger.disguise.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinecraftServerTest {

    /**
     * If you are testing with junit only, make sure to set the MinecraftServer.CURRENT to null
     * before running this method.
     */

    @Test
    void fromRaw() {
        // Some random version strings
        // Which will mock some Spigot versions
        String[] test = new String[] {"1.7.5", "1.7", "1.16.1", "1.17", "1.99.99", "1.0"};

        // For every value in this array
        // We have to assert that the pattern in the MinecraftServer.VERSION_PATTERN
        // Will match every version
        for (String s : test) {
            // Format the version
            // To something similar like spigot
            String formatted = String.format("git-Paper-409 (MC: %s)", s);

            // Assert the matcher of the formatted version equals
            // To the passed string
            assertEquals(s, MinecraftServer.fromRaw(formatted).getVersion());
        }
    }
}