package com.gymmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Build Spring Boot Application
 * @author Ajay.Srivas
 * @author Keerthana.Pai
 * @author Ashwini.D
 * @since 19/09/2017
 */
@SpringBootApplication
public class GymApplication {
    /**
     * Logger to log trace.
     */
    public static final Logger llog = LoggerFactory.getLogger(GymApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }
}
