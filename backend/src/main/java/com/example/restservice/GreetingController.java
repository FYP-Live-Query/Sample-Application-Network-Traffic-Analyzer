package com.example.restservice;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
import io.siddhi.extension.io.live.source.LiveSource;
import io.siddhi.extension.map.json.sourcemapper.JsonSourceMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.tapestry5.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import io.micrometer.core.instrument.MeterRegistry;
import javax.security.auth.login.CredentialException;




@RestController
public class GreetingController {
    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    private final ExecutorService nonBlockingService2 = Executors.newCachedThreadPool();
    private MeterRegistry meterRegistry;
    Boolean appCreated = false;
    SiddhiManager siddhiManager;
    SiddhiManager siddhiManager1;
    String query;
    String browserQuery;
    String dynamicQuery;
    String apiKey;
    public GreetingController(MeterRegistry meterRegistry) {
        PersistenceStore persistenceStore = new InMemoryPersistenceStore();
        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setPersistenceStore(persistenceStore);
        this.siddhiManager.setExtension("live", LiveSource.class);
        this.siddhiManager.setExtension("map-json", JsonSourceMapper.class);
        this.siddhiManager1 = new SiddhiManager();
        this.meterRegistry = meterRegistry;
        this.siddhiManager1.setPersistenceStore(persistenceStore);
        this.siddhiManager1.setExtension("live", LiveSource.class);
        this.siddhiManager1.setExtension("map-json", JsonSourceMapper.class);

    }
    @PostMapping("/publish")
    @CrossOrigin
    public Body publishQuery(@RequestBody Body query) {
        this.query = query.getQuery();
        this.apiKey = "Tu_TZ0W2cR92-sr1j-l7ACA.newone.9pej9tihskpx2vYZaxubGW3sFCJLzxe55NRh7T0uk1JMYiRmHdiQsWh5JhRXXT6c418385";
//        System.out.println("Data: "+query);
        System.out.println("Query: "+ this.query);
        System.out.println("API: "+ this.apiKey);
        return query;
    }

    @PostMapping("/browserInfo")
    @CrossOrigin
    public Body publishBrowserInfo(@RequestBody Body query) {
        this.browserQuery = query.getQuery();
        this.apiKey = "Tu_TZ0W2cR92-sr1j-l7ACA.newone.9pej9tihskpx2vYZaxubGW3sFCJLzxe55NRh7T0uk1JMYiRmHdiQsWh5JhRXXT6c418385";
//        System.out.println("Data: "+query);
        System.out.println("Query: "+ this.query);
        System.out.println("API: "+ this.apiKey);
        return query;
    }

    @PostMapping("/setQuery")
    @CrossOrigin
    public Body setQuery(@RequestBody Body query) {
        this.dynamicQuery = query.getQuery();
        this.apiKey = "Tu_TZ0W2cR92-sr1j-l7ACA.newone.9pej9tihskpx2vYZaxubGW3sFCJLzxe55NRh7T0uk1JMYiRmHdiQsWh5JhRXXT6c418385";
//        System.out.println("Data: "+query);
        System.out.println("Query: "+ this.dynamicQuery);
        System.out.println("API: "+ this.apiKey);
        return query;
    }


    @GetMapping("/traffic")
    @CrossOrigin
    public SseEmitter handleSse() throws CredentialException, IOException, InterruptedException {

        BlockingDeque<Event[]> events = new LinkedBlockingDeque<>(10);
//        System.out.println("Query: "+query);

        Runnable siddhi = new Runnable() {
            @Override
            public void run() {
                while (query == null){
                    continue;
                }
                UUID appname = UUID.randomUUID();
                String inStreamDefinition0 = "@App:name('"+appname.toString()+"')" +
                        "@source(type='live',sql.query='"+query+"', " +
                        "host.name='api-peamouth-0b57f3c7.paas.macrometa.io'," +
                        "api.key = 'Tu_TZ0W2cR92-sr1j-l7ACA.newone.9pej9tihskpx2vYZaxubGW3sFCJLzxe55NRh7T0uk1JMYiRmHdiQsWh5JhRXXT6c418385', " +
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
        SseEmitter emitter = new SseEmitter(-99l);
        Runnable sse = new Runnable() {
            @Override
            public void run() {

                nonBlockingService.execute(() -> {
                    try {
                        List<Object> list = new ArrayList<>(5);
                        // we could send more events
                        while(true) {
                            Event[] edata = events.take();
                            list.add(edata[0].getData()[3]);
                            String json3 = edata[0].getData()[3].toString();
                            JSONObject json1=new JSONObject(json3);
                            long updatedTime=json1.getLong("eventTimestamp");
                            long traffic_latency = System.currentTimeMillis() - updatedTime;
                            System.out.println("current: "+System.currentTimeMillis()+"time: "+updatedTime+"traffic_latency: "+traffic_latency);
//                            meterRegistry.summary("query1.latency").record(traffic_latency);'
                            meterRegistry.timer("query1.latency").record(Duration.ofMillis(traffic_latency));
                            if(list.size() == 5) {
                                emitter.send(list);
                                System.out.println("Sent!");
//                                emitter.complete();
                                list.clear();
                            }
                        }


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
//        t.join();
//        tb.join();
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
                        "host.name='api-peamouth-0b57f3c7.paas.macrometa.io'," +
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
//                            String json_browser = edata[0].getData()[3].toString();
//                            JSONObject json2=new JSONObject(json_browser);
//                            Long updatedTime2=json2.getLong("eventTimeStamp");
//                            Long browser_latency = System.currentTimeMillis() - updatedTime2;
//                            System.out.println("browser_latency:"+browser_latency);
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

    @GetMapping("/query")
    @CrossOrigin
    public SseEmitter dynamicQuery() throws CredentialException, IOException, InterruptedException {

        BlockingDeque<Event[]> events = new LinkedBlockingDeque<>(10);
        SiddhiManager siddhiManager2 = new SiddhiManager();
        PersistenceStore persistenceStore = new InMemoryPersistenceStore();
        siddhiManager2.setPersistenceStore(persistenceStore);
        siddhiManager2.setExtension("live", LiveSource.class);
        siddhiManager2.setExtension("map-json", JsonSourceMapper.class);
        Runnable siddhi = new Runnable() {
            @Override
            public void run() {
                while (dynamicQuery == null){
                    continue;
                }
                String inStreamDefinition0 = "@App:name('TestSiddhiApp1')" +
                        "@source(type='live',sql.query='"+dynamicQuery+"', " +
                        "host.name='api-peamouth-0b57f3c7.paas.macrometa.io'," +
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
                SiddhiAppRuntime siddhiAppRuntime0 = siddhiManager2
                        .createSiddhiAppRuntime(inStreamDefinition0 + query0);


                siddhiAppRuntime0.addCallback("query0", new QueryCallback() {
                    @Override
                    public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                        try {
                            System.gc();
                            System.runFinalization();
                            Thread.sleep(1000);
                            long before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                            System.out.println("Memory Usage - Start: " + before);

                            events.put(inEvents);
                            System.gc();
                            System.runFinalization();
                            Thread.sleep(1000);
                            long after = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                            long objectSize = (after - before)%10^6;
                            meterRegistry.summary("events.summary").record(objectSize);
                            System.out.println("Memory Usage - Difference:" + objectSize);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                siddhiAppRuntime0.start();

            }
        };
        SseEmitter emitter = new SseEmitter(-99l);
        Runnable sse = new Runnable() {
            @Override
            public void run() {

                nonBlockingService.execute(() -> {
                    try {
                        List<Object> list = new ArrayList<>(5);
                        // we could send more events
                        while(events.isEmpty()) {
                            Event[] edata = events.take();
                            LocalTime currentTime = java.time.LocalTime.now();
                            System.out.println("Timestamp: "+currentTime);
                            System.out.println(edata[0].getData()[3]);
                            list.add(edata[0].getData()[3]);
                            if(list.size() == 5) {
                                list.add(currentTime);
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

class Body {
    String query;
    String apiKey;
    public String getQuery() {
        return query;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}