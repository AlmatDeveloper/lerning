package kz.learning.kiryalogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KiryaLoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiryaLoggingApplication.class, args);
    }

}
