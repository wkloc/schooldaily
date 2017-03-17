package com.pgs.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by mmalek on 3/15/2017.
 */
@Configuration
public class FlywayConfig {


    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = flyway -> {
            flyway.clean();
            flyway.migrate();
        };
        return strategy;
    }

    @Bean
    @Profile("prod")
    public FlywayMigrationStrategy onlyMigrateStrategy() {
        FlywayMigrationStrategy strategy = flyway -> {
            flyway.migrate();
        };
        return strategy;
    }
}
