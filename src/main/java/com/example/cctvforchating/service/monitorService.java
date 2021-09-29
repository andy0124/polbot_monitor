package com.example.cctvforchating.service;

import java.util.*;

import com.example.cctvforchating.dto.sessionStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class monitorService {
    //싱글턴 패턴
    private static monitorService monitorServiceInstance = new monitorService();
    private Map<String,sessionStatus> monitorBox = new HashMap();

    private Logger logger = LoggerFactory.getLogger(monitorService.class);


    private monitorService(){

    }

    public static monitorService getInstance(){
        return monitorServiceInstance;
    }

    //모니터링 세션 목록 받아온 세션리스트로 초기화
    public void initializeMonitorBox(List<String> sessionList){
        this.monitorBox.clear();
        for(String session : sessionList){
            sessionStatus sessionStatus = new sessionStatus(session);
            this.monitorBox.put(session,sessionStatus);
        }
//        System.out.println(this.monitorBox.size());
        logger.info("Current session list size : " + this.monitorBox.size());
    }

    public void refreshMonitorBox(List<String> sessionList){
        //사라진 세션들을 제거해주기
        ArrayList<String> deathList = new ArrayList<>();
        for(String session : this.monitorBox.keySet()){
            if (!sessionList.contains(session)){
                deathList.add(session);
            }
        }
        for(String session : deathList){
            this.monitorBox.remove(session);
        }
//        System.out.println(this.monitorBox.size());
        logger.info("Current session list size : " + this.monitorBox.size());
    }

    public boolean changeStatus(String session, Integer status){
        if(!this.monitorBox.containsKey(session)) return false;
        sessionStatus sessionStatus = this.monitorBox.get(session);
        sessionStatus.setStatus(status);
        sessionStatus.setNumOfDialog(sessionStatus.getNumOfDialog()+1);
        sessionStatus.setLastTime(System.currentTimeMillis());
        this.monitorBox.put(session,sessionStatus);
//        System.out.println(session + " session status changed to " + status);
//        System.out.println(session + " status is "+this.monitorBox.get(session));
        logger.info(session + " session status changed to " + status);
        logger.info(session + " status is "+this.monitorBox.get(session));
        return true;
    }

    public boolean sessionCreated(String session, String phoneNum){
        if(this.monitorBox.containsKey(session)) return false;

        sessionStatus sessionStatus = new sessionStatus(session, phoneNum);
        this.monitorBox.put(session, sessionStatus);
//        System.out.println(session + " session created");
        logger.info(session + " session created");
        return true;
    }

    public void sessionDeleted(String session){
        this.monitorBox.remove(session);//이거의 성공 여부도 넣어야 겄다
//        System.out.println(session + " session deleted");
        logger.info(session + " session deleted");
    }


    public ArrayList<sessionStatus> getMonitor(){
        ArrayList<sessionStatus> monitorList = new ArrayList<>();

        for(String session : this.monitorBox.keySet()){
            monitorList.add(this.monitorBox.get(session));
        }
        return monitorList;
    }

    public boolean increaseDialogNum(String session){
        if(!this.monitorBox.containsKey(session)) return false;
        sessionStatus sessionStatus = this.monitorBox.get(session);
        sessionStatus.setNumOfDialog(sessionStatus.getNumOfDialog() + 1);
        sessionStatus.setLastTime(System.currentTimeMillis());
        this.monitorBox.put(session, sessionStatus);
//        System.out.println(session + " session increase its num to " + sessionStatus.getNumOfDialog());
        logger.info(session + " session increase its num to " + sessionStatus.getNumOfDialog());
        return true;
    }



}
