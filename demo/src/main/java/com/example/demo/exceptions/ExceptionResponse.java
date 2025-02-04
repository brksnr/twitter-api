package com.example.demo.exceptions;


import java.time.LocalDateTime;


public class ExceptionResponse {

    private String message;
    private int status;
    private LocalDateTime dateTime;

    public ExceptionResponse(String message, int status, LocalDateTime dateTime) {
        this.message = message;
        this.status = status;
        this.dateTime = dateTime;
    }

    public ExceptionResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}