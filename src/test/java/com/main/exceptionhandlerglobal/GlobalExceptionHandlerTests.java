package com.main.exceptionhandlerglobal;


import com.dto.responses.ErrorResponse;
import com.exceptions.AccountNotFoundException;
import com.exceptions.GlobalExceptionHandler;
import com.exceptions.InsufficientBalanceException;
import com.exceptions.InvalidAmountException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void testHandleAccountNotFound() {
        Mockito.when(request.getRequestURI()).thenReturn("/test/account");
        AccountNotFoundException ex = new AccountNotFoundException("Account missing");

        var response = handler.handleAccountNotFound(ex, request);

        assertEquals("Account missing", response.getBody().getMessage());
        assertEquals("/test/account", response.getBody().getPath());
    }

    @Test
    void testHandleInsufficientBalance() {
        Mockito.when(request.getRequestURI()).thenReturn("/test/withdraw");
        InsufficientBalanceException ex = new InsufficientBalanceException("Balance low");

        var response = handler.handleInsufficientBalance(ex, request);

        assertEquals("Balance low", response.getBody().getMessage());
        assertEquals("/test/withdraw", response.getBody().getPath());
    }

    @Test
    void testHandleInvalidAmount() {
        Mockito.when(request.getRequestURI()).thenReturn("/test/deposit");
        InvalidAmountException ex = new InvalidAmountException("Invalid amount");

        var response = handler.handleInvalidAmount(ex, request);

        assertEquals("Invalid amount", response.getBody().getMessage());
        assertEquals("/test/deposit", response.getBody().getPath());
    }

    @Test
    void testHandleGeneralException() {
        Mockito.when(request.getRequestURI()).thenReturn("/test/general");
        Exception ex = new Exception("Something went wrong");

        var response = handler.handleGeneral(ex, request);

        assertEquals("Something went wrong", response.getBody().getMessage());
        assertEquals("/test/general", response.getBody().getPath());
    }

    @Test
    void testHandleValidationErrors() {
        Mockito.when(request.getRequestURI()).thenReturn("/test/validation");

        FieldError fieldError = new FieldError("account", "holderName", "cannot be empty");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getFieldError()).thenReturn(fieldError);

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);

        var response = handler.handleValidationErrors(ex, request);

        assertEquals("cannot be empty", response.getBody().getMessage());
        assertEquals("/test/validation", response.getBody().getPath());
    }
}
