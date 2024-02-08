package com.ssestudy.schat.controller;

import com.ssestudy.schat.comp.ChatMessage;
import com.ssestudy.schat.rsdata.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record WriteMessageResponse(long id){}

    public record WriteMessageRequest(String authrName,String content){}

    public record MessageRequest(Long formId){}
    private record MessagesResponse(List<ChatMessage> messages,long count){}

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
    public RsData<MessagesResponse> messages(MessageRequest req){

        List<ChatMessage> messages = chatMessages;

        //번호가 입력되었다면
        if(req.formId != null){
            //해당 번호의 채팅 메세지가 전체 리스트에서의 배열인덱스 번호를 구한다.
            //없다면 -1
            int index = IntStream.range(0,messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.formId)
                    .findFirst()
                    .orElse(-1);

            if(index != -1) {
                //만약에 index가 있다면,0번 부터 index 번 까지 제거한 리스트를 만든다.
                messages = messages.subList(index + 1, messages.size());
            }
        }

        return new RsData<>(
                    "S-1",
                    "성공",
                    new MessagesResponse(chatMessages,chatMessages.size())
                );
    }
}
