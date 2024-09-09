package com.adp.changeservice.domain.repository;

import java.util.Map;

public interface CoinRepository {

    Map<Double, Integer> getAvailableCoins();

    void saveCoins(final Map<Double, Integer> coins);

    void updateCoins(final Map<Double, Integer> coins);

    void clearCoins();
}
