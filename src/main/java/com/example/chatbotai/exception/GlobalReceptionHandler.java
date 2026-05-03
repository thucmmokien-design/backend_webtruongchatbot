package com.example.chatbotai.exception;

import com.example.chatbotai.dto.requests.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalReceptionHandler {
    @ExceptionHandler(value = AppExcepsion.class)
    ResponseEntity<ApiResponse> handleException(AppExcepsion ex) {
        ErrorCode errorResponse = ex.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorResponse.getCode());
        apiResponse.setMessage(ex.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }
}
