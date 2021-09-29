package com.example.cctvforchating.service;

import com.example.cctvforchating.dto.terminateSessionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cctvforchating.dto.sessionTerminateResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class terminateSessionService {

    monitorService monitor = monitorService.getInstance();

    private final String URI_FOR_SESSION_TERMINATION = "/api/v1/common/sessionTermination";
    private String hostIP = "localhost";
    private String port = "17300";

    private static final String PROTOCOL = "http";

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(terminateSessionService.class);

    public terminateSessionService(){

    }

    public terminateSessionService(String hostIP, String port){
        this.hostIP = hostIP;
        this.port = port;
    }

    private boolean sendTerminateSession(String sessionId){
        terminateSessionDTO temp = new terminateSessionDTO();
        temp.setSessionKey(sessionId);
        sessionTerminateResponseDTO response = restTemplate.postForObject("http://"+this.hostIP + ":" +this.port + URI_FOR_SESSION_TERMINATION, temp,sessionTerminateResponseDTO.class);
        return response.getIsSuccess();
//        String response = restTemplate.postForObject("http://"+this.hostIP + ":" +this.port + URI_FOR_SESSION_TERMINATION, temp,String.class);
//        return true;
    }

    public boolean terminateSession(String sessionId){
        if(sendTerminateSession(sessionId)){
//            System.out.println("Terminate request for " + sessionId + " has succeed");
            logger.info("Terminate request for " + sessionId + " has succeed");
            this.monitor.sessionDeleted(sessionId);
            return true;
        } else{
            return false;
        }

    }


}
