package com.ssestudy.schat.controller;

import com.ssestudy.schat.comp.ChatMessage;
import com.ssestudy.schat.rsdata.RsData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record WriteMessageResponse(long id){}

    public record WriteMessageRequest(String authrName,String content){}


    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest req){
        ChatMessage message = new ChatMessage(req.authrName(),req.content());
        chatMessages.add(message);
        return new RsData<>(
                "S-1",
                "메세지가 작성되었습니다.",
                new WriteMessageResponse(message.getId())
        );
    }

    @GetMapping("/messages")
    @ResponseBody
    public RsData<List<ChatMessage>> messages(){
        return RsData<>(
                    "S-1",
                    "성공",
                    chatMessages
                );
    }
}
