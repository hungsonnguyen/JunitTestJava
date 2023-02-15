package com.zeusbe.dto.request;

import com.zeusbe.model.enumeration.ExchangeAction;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class ConfirmSwapReq {
    @NotNull
    private Long exchangeId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExchangeAction action;
}
