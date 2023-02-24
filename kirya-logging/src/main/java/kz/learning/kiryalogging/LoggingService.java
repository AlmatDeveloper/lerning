package kz.learning.kiryalogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private static final Logger log = LoggerFactory.getLogger(LoggingService.class);

    @Scheduled(fixedRate = 100)
    public void trace() {
        log.trace("trace");
    }

    @Scheduled(fixedRate = 200)
    public void debug() {
        log.debug("debug");
    }

    @Scheduled(fixedRate = 300)
    public void info() {
        log.info("info");
    }

    @Scheduled(fixedRate = 400)
    public void warn() {
        log.warn("warn");
    }

    @Scheduled(fixedRate = 500)
    public void error() {
        log.error("error");
    }

    @Scheduled(fixedRate = 2000)
    public void fatal() {
        throw new RuntimeException("Runtime message");
    }
}
