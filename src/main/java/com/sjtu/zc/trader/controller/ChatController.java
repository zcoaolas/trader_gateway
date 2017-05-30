package com.sjtu.zc.trader.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class ChatController {

    public SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /*@MessageMapping("/chatMessage")
    @SendTo("/topic/newmessage")
    public JSONObject goldMessage(JSONObject actualOrderAndDepth) {
        return actualOrderAndDepth;
    }*/

    public void sendMessage(JSONObject obj) {
        JSONObject mdObj = obj.getJSONObject("market_depth");
        Integer cId = mdObj.getInt("c_id");
        switch (cId) {
            case 1:
                //goldMessage(obj);
                template.convertAndSend("/topic/newmessage1", obj);
                break;

            case 2:
                //cornMessage(obj);
                template.convertAndSend("/topic/newmessage2", obj);
                break;

            case 3:
                //crudeMessage(obj);
                template.convertAndSend("/topic/newmessage3", obj);
                break;
        }
    }
}
