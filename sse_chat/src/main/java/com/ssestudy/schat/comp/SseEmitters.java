package com.ssestudy.schat.comp;

import com.ssestudy.schat.util.Utzip;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SseEmitters {

    private final List<SseEmitters> emitters =new CopyOnWriteArrayList<>();

    public SseEmitters add(SseEmitter emitter){

        this.emitters.add(emitter);
        emitter.onCompletion(()->{
            this.emitters.remove(emitter);
        });

        emitter.onTimeout(()->{
            emitter.complete();
        });

        return emitter;
    }

    public void noti(String eventName){
        noti(eventName, Utzip.mapOf());
    }

    public void noti(String eventName, Map<String,Object> data){
        emitters.forEach(emitter->{
            try{
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)
                                .data(data)
                );
            }catch(ClientAbortException e){}
            catch(IOException e){
                throw new RuntimeException(e);
            }
        });
    }
}
