package com.ssestudy.schat.comp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class ChatMessages {

    private final HashMap<Long, List<ChatMessage>> messages = new HashMap<>();

    public void add(Long roomId,ChatMessage message){
        if(!messages.containsKey(roomId)){
            messages.put(roomId,new CopyOnWriteArrayList<>());
        }

        messages.get(roomId).add(message);
    }
    public List<ChatMessage> from(Long roomId,Long fromId){
        if(!messages.containsKey(roomId)){
            return List.of();
        }

        List<ChatMessage> messagesInRoom = messages.get(roomId);

        if(fromId == null || fromId == 0){
            return messagesInRoom;
        }

        for(int i=0; i<messagesInRoom.size(); i++){
            ChatMessage message = messagesInRoom.get(i);

            if(message.getId() == fromId){
                return messagesInRoom.subList(i+1,messagesInRoom.size());
            }
        }
        return List.of();
    }
}
