package com.example.cctvforchating.service;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class checkSessionTimerService{
    checkSessionListService checkSessionListService = new checkSessionListService();
    private Logger logger = LoggerFactory.getLogger(checkSessionTimerService.class);

    public checkSessionTimerService(){

    }
    public void startTimer(){
        this.checkSessionListService.initSessionLIst();
//        System.out.println("init SessionList");
        logger.info("init SessionList");
        ScheduledJob job = new ScheduledJob();
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 3000);
    }



}


class ScheduledJob extends TimerTask {
    checkSessionListService checkSessionListService = new checkSessionListService();
    private Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
    public void run() {
//        System.out.println("check SessionList");
        logger.info("check SessionList");
        this.checkSessionListService.checkSessionList();
    }
}