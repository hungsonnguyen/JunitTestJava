package com.zeusbe.service;

import com.zeusbe.dto.ItemSwapDto;
import com.zeusbe.dto.MySwapDTO;
import com.zeusbe.dto.request.ConfirmSwapReq;
import com.zeusbe.dto.request.RequestSwapReq;
import com.zeusbe.model.enumeration.ExchangeStatus;
import com.zeusbe.model.enumeration.ExchangeType;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ExchangeService {
    Boolean requestSwap(RequestSwapReq requestSwapReq);

    boolean confirmSwap(ConfirmSwapReq confirmSwapReq);

    List<MySwapDTO> getMyExchanges(ExchangeStatus exchangeStatus, ExchangeType type, Pageable pageable);

    List<ItemSwapDto> getExchangesByProductId(Long productId, ExchangeType type, Pageable pageable);

    void startChatting(Long exchangerId);

}
