package com.adp.changeservice;

import com.adp.changeservice.domain.CoinInventory;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "change.service")
@Data
public class DataInitializer {

    private final CoinInventory coinInventory;
    private Map<Double, Integer> initialCoins;

    @PostConstruct
    public void loadData() {
        coinInventory.saveInitialData(initialCoins);
    }
}
