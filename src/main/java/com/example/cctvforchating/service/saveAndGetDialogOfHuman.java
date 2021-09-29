package com.example.cctvforchating.service;

import com.example.cctvforchating.dto.saveHumanDialogDTO;
import com.example.cctvforchating.dto.ChatLog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class saveAndGetDialogOfHuman {

    private String currentPath;
    public saveAndGetDialogOfHuman(){
        this.currentPath = System.getProperty("user.dir") + "/chatHistory";
        File file = new File(this.currentPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public boolean saveDialog(saveHumanDialogDTO saveHumanDialogDTO){
        if(saveHumanDialogDTO.getSessionKey() == null || saveHumanDialogDTO.getWhoIsTalking() == null || saveHumanDialogDTO.getDialog() == null) return false;

        String sessionPath = this.currentPath + "/" + saveHumanDialogDTO.getSessionKey();
        File file = new File(sessionPath);
        //세션 디렉토리 존재여부 확인
        //세션 디렉토리 없으면 생성
        if(!file.exists()){
            file.mkdirs();
        }

        sessionPath = sessionPath + "/temp.json";

        //대화 내용 저장

        // Read
        List<ChatLog> logsToWrite = readExistedChatLogs(saveHumanDialogDTO.getSessionKey(),null); // 여기 읽어 오는 부분을 각 세션의 tmp파일을 가져오게 하기
        // Writer
        JsonWriter writer = null;
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Gson gson = new Gson();
            writer = new JsonWriter(new FileWriter(sessionPath, false));
            writer.setIndent("    ");

            String dialog = saveHumanDialogDTO.getDialog();
            String time = fm.format(new Date());
            Integer whoIsTalking = saveHumanDialogDTO.getWhoIsTalking();

            ChatLog cl = new ChatLog(dialog, time, whoIsTalking);

            logsToWrite.add(cl);

            // String chatLogs = gson.toJson(this.chatLogs);
            String chatLogs = gson.toJson(logsToWrite);
            gson.toJson(gson.fromJson(chatLogs, JsonArray.class), writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return true;

    }


    public List<ChatLog> readExistedChatLogs(String session, String time) {
        List<ChatLog> logs = new ArrayList<>();
        String SessionPath = this.currentPath + "/" +session + "/temp.json";
        File f = new File(SessionPath);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fm.setLenient(false);
        Date nominatedDate;

        if(time != null){
            try {
                nominatedDate = fm.parse(time);
            } catch (ParseException e){
                e.printStackTrace();
                nominatedDate = new Date();
                nominatedDate.setTime(0);
            }

        }else{
            nominatedDate = new Date();
            nominatedDate.setTime(0);
        }

        if (f.isFile() && f.length() > 0 ) {
            JsonReader reader = null;
            Gson gson = new Gson();
            try {
                reader = new JsonReader(new FileReader(f));
                ChatLog[] chatLogs = gson.fromJson(reader, ChatLog[].class);
                if (chatLogs != null)
                    if (chatLogs.length > 0){
                        for(ChatLog chatLog : chatLogs){
                            try{
                                boolean isChatDateBiggerThanInputDate = fm.parse(chatLog.getTime()).compareTo(nominatedDate) > 0;
                                if(isChatDateBiggerThanInputDate) logs.add(chatLog);
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                        }
//                        logs.addAll(Arrays.asList(chatLogs));
                    }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return logs;
    }
}
