package com.adp.changeservice;

import com.adp.changeservice.domain.repository.CoinRepository;
import com.adp.changeservice.infrastructure.database.CoinRepositoryImpl;
import com.adp.changeservice.infrastructure.database.jpa.repository.JpaCoinRepository;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigurationPackage
public class DatabaseConfig {

    @Bean
    CoinRepository coinRepository(final JpaCoinRepository jpaRepository) {
        return new CoinRepositoryImpl(jpaRepository);
    }
}
