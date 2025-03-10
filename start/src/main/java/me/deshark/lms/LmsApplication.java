package me.deshark.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author deshark
 */
@SpringBootApplication(scanBasePackages = "me.deshark.lms")
@PropertySource(value = "classpath:secrets.properties", ignoreResourceNotFound = true)
public class LmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }
} 