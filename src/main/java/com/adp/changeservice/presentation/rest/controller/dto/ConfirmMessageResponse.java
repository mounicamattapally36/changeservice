package com.adp.changeservice.presentation.rest.controller.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

public record ConfirmMessageResponse(
        String uri,
        String method,
        String requestStatus,
        List<String> exceptionMessages) {

    public static ConfirmMessageResponse of(final String uri, final String method, final @NonNull RequestStatusEnum requestStatus,
                                            final @NonNull List<String> exceptionMessages) {
        return new ConfirmMessageResponse(uri, method, requestStatus.value, exceptionMessages);
    }

    @Getter
    @RequiredArgsConstructor
    public enum RequestStatusEnum {
        SUCCESS("success"),
        FAILURE("failure");

        private final String value;
    }
}