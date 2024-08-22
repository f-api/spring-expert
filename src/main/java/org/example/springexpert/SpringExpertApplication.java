package org.example.springexpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringExpertApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExpertApplication.class, args);
    }

}
