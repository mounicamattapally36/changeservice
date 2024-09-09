package com.adp.changeservice.domain;

import com.adp.changeservice.domain.repository.CoinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CoinInventory {

    private final CoinRepository coinRepository;

    public Map<Double, Integer> getAvailableCoins() {
        return coinRepository.getAvailableCoins();
    }

    // Update the inventory after a transaction
    public void updateInventory(final double coin, final int coinsUsed) {
        final Map<Double, Integer> coinInventory = coinRepository.getAvailableCoins();
        coinRepository.updateCoins(Map.of(coin, coinInventory.get(coin) - coinsUsed));
    }

    public void saveInitialData(final Map<Double, Integer> coinInventory) {
        // Logic to save coinInventory data to the database
        coinRepository.saveCoins(coinInventory);
    }
}
