package kz.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackLogger.class);

    public static void main(String[] args) {

        while (true) {
            LOGGER.trace("asd {}", "a");
            LOGGER.debug("asd {}", "q");
            LOGGER.info("asd {}", "w");
            LOGGER.warn("asd {}", "e");
            LOGGER.error("asd {}", "r");
            new RuntimeException();
        }
    }
}