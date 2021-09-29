package com.example.cctvforchating.dto;

import com.example.cctvforchating.contant.constant;
import lombok.Data;


@Data
public class sessionStatus {

    private String channelId = "";
    private Integer status = constant.normal;
    private Integer numOfDialog = 0;
    private Long lastTime = System.currentTimeMillis();
    private String phoneNum = "";

    public sessionStatus(String sessionId){
        this.channelId = sessionId;
    }
    public sessionStatus(String sessionId,String phoneNum) {
        this.channelId = sessionId;
        this.phoneNum = phoneNum;
    }
}
