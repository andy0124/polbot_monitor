package com.example.cctvforchating.web;

import org.springframework.web.bind.annotation.*;
import com.example.cctvforchating.service.monitorService;
import com.example.cctvforchating.dto.monitorListDTO;
import org.springframework.web.servlet.ModelAndView;
import com.example.cctvforchating.service.terminateSessionService;
import com.example.cctvforchating.dto.saveHumanDialogDTO;
import com.example.cctvforchating.service.saveAndGetDialogOfHuman;
import com.example.cctvforchating.dto.chatLogListDTO;
import com.example.cctvforchating.dto.getHumanDialogDTO;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
    monitorService monitor = monitorService.getInstance();
    terminateSessionService terminateSessionService = new terminateSessionService();
    saveAndGetDialogOfHuman saveAndGetDialogOfHuman = new saveAndGetDialogOfHuman();

    //Todo Response에 요청한 api 그리고 성공 여부 제대로 넣기

    @RequestMapping(value = "/changeStatus", method = RequestMethod.PUT)
    public String changeStatus (@RequestParam(name = "session") String session, @RequestParam(name = "status") Integer status){
        if(this.monitor.changeStatus(session,status)) return "success";
        else return "failed";
    }

    @RequestMapping(value = "/increaseDialogNum", method = RequestMethod.PUT)
    public String increaseDialogNum (@RequestParam(name = "session") String session){
        if(this.monitor.increaseDialogNum(session)) return "success";
        else return "failed";
    }

    @RequestMapping(value = "/sessionCreated", method = RequestMethod.POST)
    public String sessionCreated (@RequestParam(name = "session") String session,@RequestParam(name = "phoneNum",defaultValue = "") String phoneNum){
        if(this.monitor.sessionCreated(session,phoneNum)) return "success";
        return "duplicate session";
    }

    @RequestMapping(value = "/sessionTermination", method = RequestMethod.POST)
    public String sessionTermination (@RequestParam(name = "session") String session){
        if(this.terminateSessionService.terminateSession(session)) return "success";
        else return "failed";
    }

    @RequestMapping(value = "/getMonitorList", method = RequestMethod.GET)
    public @ResponseBody monitorListDTO getMonitorList (){
        monitorListDTO mtDTO = new monitorListDTO();
        mtDTO.setMonitorList(monitor.getMonitor());
        return mtDTO;
    }

    @RequestMapping(value = "/sessionMonitor")
    public ModelAndView getMonitorForView(){
        ModelAndView mav = new ModelAndView("sessionMonitor");

        try{
            mav.addObject("monitorBox",this.monitor.getMonitor());
        } catch (Exception e){
            System.out.println(e);
        }

        return mav;
    }

    @RequestMapping(value = "/saveHumanDialog", method = RequestMethod.POST)
    public String saveHumanDialog(@RequestBody saveHumanDialogDTO saveHumanDialogDTO ){
        boolean check = this.saveAndGetDialogOfHuman.saveDialog(saveHumanDialogDTO);
        return check ? "Success": "Failed";
    }

    @RequestMapping(value = "/getHumanDialog", method = RequestMethod.GET)
    public @ResponseBody chatLogListDTO getHumanDialog(@RequestBody getHumanDialogDTO getHumanDialogDTO){
        chatLogListDTO chatLogListDTO = new chatLogListDTO();
        chatLogListDTO.setChatLogList(this.saveAndGetDialogOfHuman.readExistedChatLogs(getHumanDialogDTO.getSessionKey(), getHumanDialogDTO.getTime()));

        return chatLogListDTO;
    }

}
