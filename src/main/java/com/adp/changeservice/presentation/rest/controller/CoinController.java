package com.adp.changeservice.presentation.rest.controller;


import com.adp.changeservice.domain.CoinService;
import com.adp.changeservice.presentation.rest.controller.dto.InventoryResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/change-service/coins")
public class CoinController {

    @NonNull
    private final CoinService coinService;

    @GetMapping(produces = "application/json")
    public InventoryResponse getCoinInventory() {
        return InventoryResponse.of(coinService.getAvailableCoins());
    }
}
