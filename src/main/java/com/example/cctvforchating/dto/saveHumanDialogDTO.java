package com.example.cctvforchating.dto;

import lombok.Data;

@Data
public class saveHumanDialogDTO {

    private Integer whoIsTalking; // 1은 상담관, 2는 사용자
    private String dialog;
    private String sessionKey;
}
