package com.adp.changeservice.infrastructure.database;

import com.adp.changeservice.domain.repository.CoinRepository;
import com.adp.changeservice.infrastructure.database.jpa.entity.CoinEntity;
import com.adp.changeservice.infrastructure.database.jpa.repository.JpaCoinRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CoinRepositoryImpl implements CoinRepository {

    private final JpaCoinRepository jpaCoinRepository;

    @Override
    public Map<Double, Integer> getAvailableCoins() {
        return jpaCoinRepository.findAll().stream()
                .collect(Collectors.toMap(CoinEntity::getDenomination, CoinEntity::getQuantity));
    }

    @Override
    public void saveCoins(final Map<Double, Integer> coins) {
        coins.entrySet().stream()
                .map(entry -> {
                    CoinEntity coinEntity = new CoinEntity();
                    coinEntity.setDenomination(entry.getKey());
                    coinEntity.setQuantity(entry.getValue());
                    return coinEntity;
                })
                .forEach(jpaCoinRepository::save);
    }

    @Override
    public void updateCoins(final Map<Double, Integer> coins) {
        coins.entrySet().stream()
                .map(entry -> {
                    final CoinEntity coinEntity = Optional.ofNullable(jpaCoinRepository.findByDenomination(entry.getKey()))
                            .orElseThrow(() -> new IllegalArgumentException("Coin not found"));
                    coinEntity.setQuantity(entry.getValue());
                    return coinEntity;
                })
                .forEach(jpaCoinRepository::save);


    }

    @Override
    @Transactional
    public void clearCoins() {
        jpaCoinRepository.deleteAll();
    }
}
