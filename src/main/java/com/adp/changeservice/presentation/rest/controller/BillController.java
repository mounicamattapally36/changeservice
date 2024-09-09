package com.adp.changeservice.presentation.rest.controller;


import com.adp.changeservice.domain.CoinService;
import com.adp.changeservice.presentation.rest.controller.dto.ChangeResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/change-service/bills")
public class BillController {

    @NonNull
    private final CoinService coinService;

    @PostMapping(value = "/{bill}", produces = "application/json")
    public ChangeResponse getChange(@PathVariable final double bill) {
        final Map<Double, Integer> change = coinService.getChangeForBill(bill);
        return ChangeResponse.of(change);
    }

}
