package com.adp.changeservice.infrastructure.database;

import com.adp.changeservice.DatabaseConfig;
import com.adp.changeservice.domain.repository.CoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoinRepositoryImplTest {

    private static final Map<Double, Integer> coinMap = Map.of(0.25, 100, 0.10, 100, 0.05, 100, 0.01, 100);

    @Autowired
    private CoinRepository coinRepository;

    @BeforeEach
    public void reset() {
        coinRepository.clearCoins();
    }

    @Test
    void testGetAvailableCoins() {
        coinRepository.saveCoins(coinMap);
        final Map<Double, Integer> actual = coinRepository.getAvailableCoins();
        assertThat(actual).isEqualTo(coinMap);
    }

    @Test
    void testUpdateCoins() {
        coinRepository.saveCoins(coinMap);
        coinRepository.updateCoins(Map.of(0.25, 80));
        final Map<Double, Integer> actual = coinRepository.getAvailableCoins();
        assertThat(actual).isEqualTo(Map.of(0.25, 80, 0.10, 100, 0.05, 100, 0.01, 100));
    }

    @Test
    void testSaveCoins() {
        coinRepository.saveCoins(coinMap);
        final Map<Double, Integer> actual = coinRepository.getAvailableCoins();
        assertThat(actual).isEqualTo(coinMap);
    }
}
