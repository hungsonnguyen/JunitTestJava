package com.zeusbe.dto;

import com.zeusbe.model.enumeration.ConfirmStatus;
import com.zeusbe.model.enumeration.ExchangeStatus;
import com.zeusbe.model.enumeration.ExchangeType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSwapDto {
    private Long exchangeId;

    private ConfirmStatus ownerConfirm;

    private ConfirmStatus exchangerConfirm;

    private ExchangeStatus exchangeStatus;

    private boolean chatting;

    private ExchangeType swapType;

    private ProductSwapDTO myProduct;

    private ProductSwapDTO exchangeProduct;
}
