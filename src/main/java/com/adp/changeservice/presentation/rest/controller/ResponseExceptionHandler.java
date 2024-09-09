package com.adp.changeservice.presentation.rest.controller;

import com.adp.changeservice.domain.exception.NoSufficientCoinsException;
import com.adp.changeservice.domain.exception.NotValidBillException;
import com.adp.changeservice.presentation.rest.controller.dto.ConfirmMessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler {

    private static final String EXCEPTION_MESSAGE_IS_REQUIRED = "exceptionMessage is required";

    @ExceptionHandler({NotValidBillException.class})
    public ResponseEntity<ConfirmMessageResponse> handleNotValidBillException(final HttpServletRequest request,
                                                              final NotValidBillException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildConfirmMessageBody(request.getRequestURI(), request.getMethod(), List.of(e.getMessage())));
    }

    @ExceptionHandler({NoSufficientCoinsException.class})
    public ResponseEntity<ConfirmMessageResponse> handleNoSufficientCoinsException(final HttpServletRequest request,
                                                                   final NoSufficientCoinsException e) {

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(buildConfirmMessageBody(request.getRequestURI(), request.getMethod(), List.of(e.getMessage())));
    }



    private ConfirmMessageResponse buildConfirmMessageBody(String uri, String method, List<String> messages) {
        Assert.notEmpty(messages, () -> EXCEPTION_MESSAGE_IS_REQUIRED);
        return ConfirmMessageResponse.of(uri, method, ConfirmMessageResponse.RequestStatusEnum.FAILURE, messages);
    }
}
