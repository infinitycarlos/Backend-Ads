package com.bambu.backend.exceptions;


import lombok.Data;

@Data
public class ApiError {

    private String code;
    private int status;
    private String message;
}