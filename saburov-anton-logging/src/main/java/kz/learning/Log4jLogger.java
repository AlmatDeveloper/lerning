package kz.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLogger.class);

    public static void main(String[] args) {
        LOGGER.trace("asd {}", "a");
        LOGGER.debug("asd {}", "q");
        LOGGER.info("asd {}", "w");
        LOGGER.warn("asd {}", "e");
        LOGGER.error("asd {}", "r");
    }
}
