package com.ssestudy.schat.controller;


import com.ssestudy.schat.comp.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

import java.io.IOException;

@Controller
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;

    @GetMapping(value="/connect",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitters> connect(){
        SseEmitters emitter = new SseEmitters();
        sseEmitters.add(emitter);
        try{
            emitter.send(SseEmitters.event()
                    .name("connect")
                    .data("connected!"));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }
}
