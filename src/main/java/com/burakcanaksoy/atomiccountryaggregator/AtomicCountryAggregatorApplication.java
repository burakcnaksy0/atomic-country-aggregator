package com.burakcanaksoy.atomiccountryaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class AtomicCountryAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtomicCountryAggregatorApplication.class, args);
    }

}
