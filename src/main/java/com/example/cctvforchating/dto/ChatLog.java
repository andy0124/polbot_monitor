package com.example.cctvforchating.dto;

import lombok.Data;

@Data
public class ChatLog {
    private String dialog;
    private String time;
    private Integer whoIsTalking;

    public ChatLog(String dialog, String time, Integer whoIsTalking){
        this.dialog = dialog;
        this.time = time;
        this.whoIsTalking = whoIsTalking;
    }
}
