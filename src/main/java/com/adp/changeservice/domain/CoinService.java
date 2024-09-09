package com.adp.changeservice.domain;

import com.adp.changeservice.domain.exception.NoSufficientCoinsException;
import com.adp.changeservice.domain.exception.NotValidBillException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class CoinService {

    @Value("#{'${change.service.supported.bills}'.split(',')}")
    private List<Double> supportedBills;

    private final CoinInventory coinInventory;


    @Transactional
    public Map<Double, Integer> getChangeForBill(final double bill) {

        validateBill(bill);

        final Map<Double, Integer> availableInventory = coinInventory.getAvailableCoins();

        if (CollectionUtils.isEmpty(availableInventory)) {
            throw new NoSufficientCoinsException("No coins available to make change.");
        }

        final Map<Double, Integer> changeMap = new HashMap<>(); // Result map for coins used
        final AtomicReference<Double> amount = new AtomicReference<>(bill);

        // Iterate over the coin types using streams (sorted descending for the greedy approach)
        availableInventory.keySet().stream()
                .sorted(Comparator.reverseOrder()) // Sort coins in descending order
                .forEach(coin -> {
                    final double amountRemaining = amount.get();
                    final int neededCoins = (int) (amountRemaining / coin); // How many of this coin are needed
                    final int availableCoins = availableInventory.get(coin); // How many are available

                    if (neededCoins > 0 && availableCoins > 0) {
                        // Use as many coins as possible, up to the available amount
                        int coinsToUse = Math.min(neededCoins, availableCoins);

                        // Add the coins to the change map
                        changeMap.put(coin, coinsToUse);

                        // Deduct the coins from the inventory
                        coinInventory.updateInventory(coin, coinsToUse);

                        // Reduce the remaining amount
                        amount.getAndUpdate(value -> value - coinsToUse * coin);
                    }
                });

        if (amount.get() > 0) {
            throw new NoSufficientCoinsException("Not enough coins to make exact change.");
        }

        return changeMap;
    }

    public Map<Double, Integer> getAvailableCoins() {
        return coinInventory.getAvailableCoins();
    }

    private void validateBill(final double bill) {
        if (!supportedBills.contains(bill)) {
            throw new NotValidBillException("Bill amount not supported.");
        }
    }
}
