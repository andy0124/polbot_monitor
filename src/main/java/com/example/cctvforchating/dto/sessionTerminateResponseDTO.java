package com.example.cctvforchating.dto;

import lombok.Data;

@Data
public class sessionTerminateResponseDTO {
    private String errorMessage;
    private Integer errorCode;
    private String api;
    private Boolean isSuccess;
    private String status;
}
