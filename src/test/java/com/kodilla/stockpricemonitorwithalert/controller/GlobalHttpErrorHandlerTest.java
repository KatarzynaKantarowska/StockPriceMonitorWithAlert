package com.kodilla.stockpricemonitorwithalert.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalHttpErrorHandlerTest {

    private final GlobalHttpErrorHandler globalHttpErrorHandler = new GlobalHttpErrorHandler();

    @Test
    void shouldHandleException() {
        Exception exception = new Exception("Some error message");
        ResponseEntity<Object> response = globalHttpErrorHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Some error message", response.getBody());
    }
}