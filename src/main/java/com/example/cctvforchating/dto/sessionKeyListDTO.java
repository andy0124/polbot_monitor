package com.example.cctvforchating.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class sessionKeyListDTO {
    private String errorMessage;
    private List<String> SessionKeyList;
    private Integer errorCode;
    private String api;
    private boolean isSuccess;
    private String status;

}
