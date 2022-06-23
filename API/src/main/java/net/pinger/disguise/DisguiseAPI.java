package net.pinger.disguise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisguiseAPI {

    private static final Logger logger = LoggerFactory.getLogger("DisguiseAPI");
    private static DisguiseAPI instance;

    public static Logger getLogger() {
        return logger;
    }
}
