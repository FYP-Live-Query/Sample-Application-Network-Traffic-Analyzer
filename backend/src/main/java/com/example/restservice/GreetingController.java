package com.example.restservice;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import com.arangodb.velocypack.VPackSlice;
//import com.c8db.C8DB;
//import com.c8db.http.HTTPEndPoint;
//import com.c8db.http.HTTPMethod;
//import com.c8db.http.HTTPRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.CredentialException;

@Controller
public class GreetingController {
    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @GetMapping("/sse")
    @CrossOrigin
    public SseEmitter handleSse() throws CredentialException, IOException {



//        C8DB db = (new C8DB.Builder()).hostName("api-varden-4f0f3c4f.paas.macrometa.io").port(443).apiKey("madu140_gmail.com.AccessPortal.2PL8EeyIAMn2sx7YHKWMM58tmJLES4NyIWq6Cnsj0BTMjygJyF3b14zb2sidcauXccccb8").build();
//        HTTPEndPoint endPoint = new HTTPEndPoint("/_fabric/_system/_api/collection/network_traffic/count");
//        HTTPRequest request = (new HTTPRequest.Builder()).RequestType(HTTPMethod.GET).EndPoint(endPoint).build();
//        db.execute(request);
//
//
//        VPackSlice r = db.execute(request);
//        System.out.println(r.toString());

        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
//            for (int i = 0; i < 10; i++) {
                try {
//                    emitter.send("Add Data" + " - " + new Date());
                    // we could send more events
                    emitter.send(new Date());
//                emitter.complete();
                } catch (Exception ex) {
                    emitter.completeWithError(ex);
                }
//            }
            emitter.complete();
        });
        return emitter;
    }
}