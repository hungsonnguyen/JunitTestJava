package com.zeusbe.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data

public class RequestSwapReq {
    @NotNull
    private Long sendProductId;

    @NotNull
    private Long receiveProductId;
}
