package com.zeusbe.controller;

import com.zeusbe.dto.ItemSwapDto;
import com.zeusbe.dto.MySwapDTO;
import com.zeusbe.dto.request.ConfirmSwapReq;
import com.zeusbe.dto.request.RequestSwapReq;
import com.zeusbe.exception.ErrorResponse;
import com.zeusbe.exception.RecordNotFoundException;
import com.zeusbe.model.Exchange;
import com.zeusbe.model.User;
import com.zeusbe.model.enumeration.ExchangeStatus;
import com.zeusbe.model.enumeration.ExchangeType;
import com.zeusbe.service.ExchangeService;
import com.zeusbe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/exchange")
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    UserService userService;

    private final Logger log = LoggerFactory.getLogger(ExchangeController.class);


    @PostMapping("/request-swap")
    public ResponseEntity<Boolean> requestSwap(@Valid @RequestBody RequestSwapReq requestSwap) {
        return new ResponseEntity<>(exchangeService.requestSwap(requestSwap), HttpStatus.OK);
    }


    @PostMapping("/confirm-swap")
    public ResponseEntity<Boolean> confirm(@Valid @RequestBody ConfirmSwapReq confirmSwapReq) {
        log.debug("Request to confirm swap {}", confirmSwapReq);
        return new ResponseEntity<>(exchangeService.confirmSwap(confirmSwapReq), HttpStatus.OK);
    }

    @GetMapping("/myExchanges")
    public ResponseEntity<List<MySwapDTO>> getMyExchanges(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) ExchangeStatus exchangeStatus,
            @RequestParam(required = false) ExchangeType type
    ) {
        System.out.println("123");
        log.debug("Request to get my exchanges");
        List<MySwapDTO> myExchangeDTOs = exchangeService.getMyExchanges(exchangeStatus, type, pageable);
        return new ResponseEntity<>(myExchangeDTOs, HttpStatus.OK);
    }

    @GetMapping("/item-swaps")
    public ResponseEntity<List<ItemSwapDto>> getExchangesByProductId(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) ExchangeType type
    ) {
        System.out.println("Swap items by product Id");

        List<ItemSwapDto> myItemSwapDtoList = exchangeService.getExchangesByProductId(productId, type, pageable);

        return new ResponseEntity<>(myItemSwapDtoList, HttpStatus.OK);
    }

    @PutMapping("/start-chatting/{exchangerId}")
    public ResponseEntity<?> startChatting(@PathVariable(required = true) Long exchangerId) {
        exchangeService.startChatting(exchangerId);
        return new ResponseEntity(HttpStatus.OK);
    }


    //    Handle Exception
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", false, details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse("error validation", false, errors);
        return errorResponse;
    }

}
