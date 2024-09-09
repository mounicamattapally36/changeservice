package com.adp.changeservice.presentation.rest.controller.dto;

import lombok.NonNull;

import java.util.Map;

public record ChangeResponse(@NonNull Map<Double, Integer> change) {

    public static ChangeResponse of(@NonNull final Map<Double, Integer> change) {
        return new ChangeResponse(change);
    }
}
