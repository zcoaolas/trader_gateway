package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.ActualOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    //@Autowired
    //private ChatRoom chatRoom;

    /**
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
     */
    /*@MessageMapping("/chatRoom") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/newuser")
    public ChatMessage newChatter(@Header("message") String topic, @Headers Map<String, Object> headers) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logService.logInfo(ChatRoomController.class, "connected successfully....");
        logService.logInfo(ChatRoomController.class, headers.toString());
        logService.logInfo(ChatRoomController.class, topic);
        chatRoom.addUser(auth == null? "anonymous":auth.getName());
        String from = auth == null? "anonymous":auth.getName();
        return new ChatMessage("system",String.format("Welcome %s join the chatroom!",from),
                new Timestamp(new Date().getTime()).toString());

        String username = headers.containsKey("") ?
    }

    @MessageMapping("/chatMessage") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/newmessage")
    public ChatMessage chat(@Header("message") String message, @Headers Map<String, Object> headers) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String from = auth == null? "anonymous":auth.getName();
        return new ChatMessage(from,message,new Timestamp(new Date().getTime()).toString());
    }*/

    @MessageMapping("/chatMessage") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/newmessage")
    public ActualOrder actualOrderMessage(ActualOrder actualOrder) {
        return actualOrder;
    }
}
