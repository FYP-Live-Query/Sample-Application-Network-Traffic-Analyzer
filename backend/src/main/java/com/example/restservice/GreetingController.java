package com.example.restservice;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.*;

//import com.c8db.C8DB;
//import com.c8db.http.HTTPEndPoint;
//import com.c8db.http.HTTPMethod;
//import com.c8db.http.HTTPRequest;
import com.google.gson.Gson;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.util.persistence.InMemoryPersistenceStore;
import io.siddhi.core.util.persistence.PersistenceStore;
import io.siddhi.core.event.Event;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.extension.io.live.source.LiveSource;
import io.siddhi.extension.map.json.sourcemapper.JsonSourceMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.auth.login.CredentialException;




@Controller
public class GreetingController {
    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    private final ExecutorService nonBlockingService2 = Executors.newCachedThreadPool();
    Boolean appCreated = false;
    SiddhiManager siddhiManager;
    SiddhiManager siddhiManager1;
    String query;
    String browserQuery;
    String apiKey;
    public GreetingController() {
        PersistenceStore persistenceStore = new InMemoryPersistenceStore();
        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setPersistenceStore(persistenceStore);
        this.siddhiManager.setExtension("live", LiveSource.class);
        this.siddhiManager.setExtension("map-json", JsonSourceMapper.class);

        this.siddhiManager1 = new SiddhiManager();
        this.siddhiManager1.setPersistenceStore(persistenceStore);
        this.siddhiManager1.setExtension("live", LiveSource.class);
        this.siddhiManager1.setExtension("map-json", JsonSourceMapper.class);

    }
    @CrossOrigin
    public SseEmitter handleSse() throws CredentialException, IOException, InterruptedException {

        BlockingDeque<Event[]> events = new LinkedBlockingDeque<>(10);
        System.out.println("sse req arrived");

        Runnable siddhi = new Runnable() {
            @Override
            public void run() {
                PersistenceStore persistenceStore = new InMemoryPersistenceStore();
                SiddhiManager siddhiManager = new SiddhiManager();
                siddhiManager.setPersistenceStore(persistenceStore);
                siddhiManager.setExtension("live", LiveSource.class);
                siddhiManager.setExtension("map-json", JsonSourceMapper.class);
                String inStreamDefinition0 = "@App:name('TestSiddhiApp0')" +
                        "@source(type='live',sql.query='FOR t IN network_traffic SORT t.traffic DESC LIMIT 5 RETURN t', " +
                        "host.name='api-varden-4f0f3c4f.paas.macrometa.io'," +
                        "api.key = 'madu140_gmail.com." +
                        "AccessPortal.2PL8EeyIAMn2sx7YHKWMM58tmJLES4NyIWq6Cnsj0BTMjygJyF3b14zb2sidcauXccccb8', " +
                        " @map(type='json', fail.on.missing.attribute='false') )" +
                        "define stream inputStream (id String,key String,revision String,properties String);";

                String query0 = ("@sink(type = 'log')" +
                        "define stream OutputStream (id String,key String,revision String,properties String);" +
                        "@info(name = 'query0') "
                        + "from inputStream "
                        + "select * "
                        + "insert into outputStream;"
                );
                SiddhiAppRuntime siddhiAppRuntime0 = siddhiManager
                        .createSiddhiAppRuntime(inStreamDefinition0 + query0);



                siddhiAppRuntime0.addCallback("query0", new QueryCallback() {
                    @Override
                    public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                        try {
                            events.put(inEvents);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                siddhiAppRuntime0.start();
            }
        };
        SseEmitter emitter = new SseEmitter();
        Runnable sse = new Runnable() {
            @Override
            public void run() {

                nonBlockingService.execute(() -> {
                    try {

                        // we could send more events
                        while(events.isEmpty()) {
                            Event[] edata = events.take();
                            emitter.send(edata[0].getData()[3]);
                            System.out.println(edata[0].getData()[3].toString());
                            emitter.complete();
//                            for (i = 0; i < edata.length; i++) {
//                                emitter.send(edata[i].getData()[3]);
//                            }
//                            emitter.complete();

                        }

//                emitter.complete();
                    } catch (Exception ex) {
                        emitter.completeWithError(ex);
                    }
//            }
                });

            }
        };
        Thread t = new Thread(siddhi);
        Thread tb = new Thread(sse);
        t.start();
        tb.start();
        return emitter;
    }

    @GetMapping("/browsers")
    @CrossOrigin
    public SseEmitter handleSse2() throws CredentialException, IOException, InterruptedException {

        BlockingDeque<Event[]> events = new LinkedBlockingDeque<>(10);

        Runnable siddhi = new Runnable() {
            @Override
            public void run() {
                while (query == null){
                    continue;
                }
                String inStreamDefinition0 = "@App:name('TestSiddhiApp1')" +
                        "@source(type='live',sql.query='"+browserQuery+"', " +
                        "host.name='api-varden-4f0f3c4f.paas.macrometa.io'," +
                        "api.key = '"+apiKey+"', " +
                        " @map(type='json', fail.on.missing.attribute='false') )" +
                        "define stream inputStream (id String,key String,revision String,properties String);";
//                System.out.println("SSS: "+inStreamDefinition0);
                String query0 = ("@sink(type = 'log')" +
                        "define stream OutputStream (id String,key String,revision String,properties String);" +
                        "@info(name = 'query0') "
                        + "from inputStream "
                        + "select * "
                        + "insert into outputStream;"
                );
                SiddhiAppRuntime siddhiAppRuntime0 = siddhiManager1
                        .createSiddhiAppRuntime(inStreamDefinition0 + query0);


                siddhiAppRuntime0.addCallback("query0", new QueryCallback() {
                    @Override
                    public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                        try {
                            events.put(inEvents);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                siddhiAppRuntime0.start();

            }
        };
        SseEmitter emitter = new SseEmitter();
        Runnable sse = new Runnable() {
            @Override
            public void run() {

                nonBlockingService.execute(() -> {
                    try {
                        List<Object> list = new ArrayList<>(5);
                        // we could send more events
                        while(events.isEmpty()) {
                            Event[] edata = events.take();
                            System.out.println(edata[0].getData()[3]);
                            list.add(edata[0].getData()[3]);
                            if(list.size() == 5) {
                                emitter.send(list);
                                list.clear();
                            }
                        }
                    } catch (Exception ex) {
                        emitter.completeWithError(ex);
                    }
                });

            }
        };
        Thread t = new Thread(siddhi);
        Thread tb = new Thread(sse);
        t.start();
        tb.start();
        return emitter;
    }



}