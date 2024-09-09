package com.adp.changeservice.presentation.rest.controller.dto;

import lombok.NonNull;

import java.util.Map;

public record InventoryResponse(@NonNull Map<Double, Integer> changeInventory) {

    public static InventoryResponse of(@NonNull final Map<Double, Integer> changeInventory) {
        return new InventoryResponse(changeInventory);
    }

}

