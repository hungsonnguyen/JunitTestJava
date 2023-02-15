package com.zeusbe.dto;

import com.zeusbe.model.enumeration.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MySwapDTO {
    private Long exchangeId;

    private Long productId;

    private String productName;

    private String avatarProduct;

    private ZonedDateTime updatedDate;

    private ExchangeStatus exchangeStatus;
}
