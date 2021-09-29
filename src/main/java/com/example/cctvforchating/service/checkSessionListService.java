package com.example.cctvforchating.service;

import com.example.cctvforchating.dto.sessionKeyListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class checkSessionListService {
    monitorService monitor = monitorService.getInstance();

    private final String URI_FOR_SESSION_LIST = "/api/v1/common/sessionList";
    private String hostIP = "localhost";
    private String port = "17300";

    private static final String PROTOCOL = "http";

    ObjectMapper mapper = new ObjectMapper();



    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    public checkSessionListService(){
    }

    public checkSessionListService(String hostIP, String port){
        this.hostIP = hostIP;
        this.port = port;
    }

    private List<String> getSessionList(){
        sessionKeyListDTO temp = new sessionKeyListDTO();
        sessionKeyListDTO sessionKeyList = restTemplate.postForObject("http://"+this.hostIP + ":" +this.port + URI_FOR_SESSION_LIST, temp,sessionKeyListDTO.class);
        return sessionKeyList.getSessionKeyList();

    }

    public void checkSessionList(){
        this.monitor.refreshMonitorBox(getSessionList());
    }

    public void initSessionLIst(){
        this.monitor.initializeMonitorBox(getSessionList());
    }
}