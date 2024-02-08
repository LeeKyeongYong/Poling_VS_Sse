package com.ssestudy.schat.controller;



import com.ssestudy.schat.comp.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;

    @GetMapping(value="/connect/{groupKey}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@PathVariable String groupKey){
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(groupKey,emitter);
        try{
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }
}
