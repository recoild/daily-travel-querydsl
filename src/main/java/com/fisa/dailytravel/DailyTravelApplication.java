package com.fisa.dailytravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class DailyTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyTravelApplication.class, args);
    }

}
