package com.adp.changeservice.infrastructure.database.jpa.repository;

import com.adp.changeservice.infrastructure.database.jpa.entity.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaCoinRepository extends JpaRepository<CoinEntity, Double> {

    @Modifying
    @Query(value = "delete from coins", nativeQuery = true)
    void deleteAll();

    CoinEntity findByDenomination(final Double denomination);

}
