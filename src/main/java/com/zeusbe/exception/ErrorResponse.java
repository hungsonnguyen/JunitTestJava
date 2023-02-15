package com.zeusbe.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    //General error message about nature of error
    private String message;

//    Specific errors in API request processing
    private Map<String,String> details;
    private boolean status;

    public ErrorResponse(String message,  boolean status,Map<String, String> details) {
        super();
        this.message = message;
        this.details = details;
        this.status = status;
    }
}
