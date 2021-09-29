package com.example.cctvforchating.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class startTimer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        checkSessionTimerService checkSessionTimerService = new checkSessionTimerService();
        checkSessionTimerService.startTimer();
    }
}
