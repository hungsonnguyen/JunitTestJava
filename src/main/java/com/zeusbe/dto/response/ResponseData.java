package com.zeusbe.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.HashMap;

@Getter
@Setter
public class ResponseData {
    private Boolean isSuccess;
    private ErrorMessage errorMessage;
    private HttpStatus statusCode;
    private HashMap<String, Object> data;

    public ResponseData() {
        this.data = new HashMap<>();
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

    public void copyData(Object data) {
        try {
            for (Field field : data.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(data);
                this.data.put(field.getName(), value);
            }
        } catch (Exception ex) {}
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
