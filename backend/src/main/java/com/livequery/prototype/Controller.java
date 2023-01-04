package com.livequery.prototype;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

//import com.c8db.C8DB;
//import com.c8db.http.HTTPEndPoint;
//import com.c8db.http.HTTPMethod;
//import com.c8db.http.HTTPRequest;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.util.persistence.InMemoryPersistenceStore;
import io.siddhi.core.util.persistence.PersistenceStore;
import io.siddhi.core.event.Event;
import io.siddhi.extension.map.json.sourcemapper.JsonSourceMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.auth.login.CredentialException;
import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import SiddhiAppComposites.Annotation.Attributes.JsonMapAttributes;
import SiddhiAppComposites.Annotation.Common.KeyValue;
import SiddhiAppComposites.Annotation.Info.QueryInfo;
import SiddhiAppComposites.Annotation.Map.JsonMap;
import SiddhiAppComposites.Annotation.Sink.LogSink;
import SiddhiAppComposites.Annotation.Source.LiveSource;
import SiddhiAppComposites.SiddhiApp;
import Compiler.SiddhiAppGenerator;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private SiddhiManager siddhiManager;
    private MeterRegistry meterRegistry;
    private HashMap<String, UserInfo> trafficUsers;
    private HashMap<String, UserInfo> browserUsers;

    private HashMap<String, UserInfo> anyQueryUsers;
    String TIME_SERVER = "time-a.nist.gov";
    NTPUDPClient timeClient = new NTPUDPClient();
    private final InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);

    public Controller(MeterRegistry meterRegistry) throws UnknownHostException {
        PersistenceStore persistenceStore = new InMemoryPersistenceStore();
        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setPersistenceStore(persistenceStore);
        this.siddhiManager.setExtension("live", io.siddhi.extension.io.live.source.LiveSource.class);
        this.siddhiManager.setExtension("map-json", JsonSourceMapper.class);
        this.meterRegistry = meterRegistry;
        this.trafficUsers = new HashMap<>();
        this.browserUsers = new HashMap<>();
        this.anyQueryUsers = new HashMap<>();
    }

    private static SseEmitter getSseEmitter() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));

        sseEmitter.onError((ex) -> LOGGER.info("SseEmitter got error:", ex));
        return sseEmitter;
    }

    private SiddhiAppRuntime getSiddhiAppRuntime(LinkedBlockingQueue<Event[]> linkedBlockingQueue, String query) {
        SiddhiApp siddhiApp = SiddhiAppGenerator.generateSiddhiApp(
                "SiddhiApp-dev-test",
                query,
                new SiddhiAppComposites.Annotation.Source.LiveSource()
                        .addSourceComposite(new KeyValue<>("host.name","api-peamouth-0b57f3c7.paas.macrometa.io"))
                        .addSourceComposite(new KeyValue<>("api.key","Tu_TZ0W2cR92-sr1j-l7ACA.newone.9pej9tihskpx2vYZaxubGW3sFCJLzxe55NRh7T0uk1JMYiRmHdiQsWh5JhRXXT6c418385")),
                new JsonMap()
                        .addMapComposite(new KeyValue<>("fail.on.missing.attribute","false"))
                        .addMapComposite(new KeyValue<>("enclosing.element","$.properties")),
                new JsonMapAttributes(),
                new LogSink(),
                new QueryInfo().setQueryName("SQL-SiddhiQL-dev-test")
        );

        String siddhiAppString = siddhiApp.getSiddhiAppStringRepresentation();
        System.out.println(siddhiAppString);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiAppString);
        siddhiAppRuntime.addCallback("SQL-SiddhiQL-dev-test", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                linkedBlockingQueue.add(inEvents);
            }
        });
        return siddhiAppRuntime;
    }

    private void calculateLatency(Event[] event, String initial, long[] time) throws IOException {
        if(Objects.equals(initial, "false")) {
            if (System.currentTimeMillis() > time[0] + 3.6e+6) {
                TimeInfo timeInfo = timeClient.getTime(inetAddress);
                long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
//                                long updatedTime=json1.getLong("eventTimestamp");
                long updatedTime = (long) event[0].getData()[event[0].getData().length - 2];
                long traffic_latency = returnTime - updatedTime;
                System.out.println("current: " + System.currentTimeMillis() + " sync: " + returnTime + " updated_time: " + updatedTime + " traffic_latency: " + traffic_latency);
                meterRegistry.timer("query1.latency").record(Duration.ofMillis(traffic_latency));
                time[0] = System.currentTimeMillis();
            }
            else {
                long updatedTime = (long) event[0].getData()[event[0].getData().length - 2];
                long traffic_latency = System.currentTimeMillis() - updatedTime;
                System.out.println("current: " + System.currentTimeMillis() + " updated_time: " + updatedTime + " traffic_latency: " + traffic_latency);
                meterRegistry.timer("query1.latency").record(Duration.ofMillis(traffic_latency));
            }
        }
    }

    @PostMapping("/publish")
    @CrossOrigin
    public UserInfo publishQuery(@RequestBody UserInfo userInfo) {
        System.out.println("Query: "+ userInfo.getQuery());
        System.out.println("API: "+ userInfo.getApiKey());
        System.out.println("ID: "+ userInfo.getId());
        this.trafficUsers.put(userInfo.getId(), userInfo);
        return userInfo;
    }

    @PostMapping("/browserInfo")
    @CrossOrigin
    public UserInfo publishBrowserQuery(@RequestBody UserInfo userInfo) {
        System.out.println("Query: "+ userInfo.getQuery());
        System.out.println("API: "+ userInfo.getApiKey());
        System.out.println("ID: "+ userInfo.getId());
        this.browserUsers.put(userInfo.getId(), userInfo);
        return userInfo;
    }

    @PostMapping("/setQuery")
    @CrossOrigin
    public UserInfo setQuery(@RequestBody UserInfo userInfo) {
        System.out.println("Query: "+ userInfo.getQuery());
        System.out.println("API: "+ userInfo.getApiKey());
        System.out.println("ID: "+ userInfo.getId());
        this.anyQueryUsers.put(userInfo.getId(), userInfo);
        return userInfo;
    }


    @GetMapping("/traffic")
    @CrossOrigin
    public SseEmitter trafficData(String userId) throws CredentialException, IOException, InterruptedException {
        final long[] time = {System.currentTimeMillis()};
        LinkedBlockingQueue<Event[]> linkedBlockingQueue = new LinkedBlockingQueue<>();

        Runnable siddhiAppRunner = new Runnable() {

            @Override
            public void run() {
                while (!trafficUsers.containsKey(userId)) {
//                    Thread.onSpinWait();
                }
                String userQuery = trafficUsers.get(userId).getQuery();
                SiddhiAppRuntime siddhiAppRuntime = getSiddhiAppRuntime(linkedBlockingQueue, userQuery);
                trafficUsers.get(userId).setSiddhiAppRuntime(siddhiAppRuntime);
                siddhiAppRuntime.start();
            }
        };
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        Runnable emitterRunner = new Runnable() {
            @Override
            public void run() {

                executor.execute(() -> {
                    try {
                        List<Object> responses = new ArrayList<>(5);
                        while(true) {
                            Event[] event = linkedBlockingQueue.take();
                            responses.add(event[0].getData());
                            String initial = event[0].getData()[event[0].getData().length-1].toString();
                            calculateLatency(event, initial, time);
                            if (responses.size() == 5) {
                                sseEmitter.send(responses);
                                responses.clear();
//                                emitter.complete();
                            }
                        }
                    } catch (Exception ex) {
                        sseEmitter.completeWithError(ex);
                    }
                });

            }
        };

        if (this.trafficUsers.containsKey(userId) && this.trafficUsers.get(userId).getSiddhiAppThread() != null) {
            this.trafficUsers.get(userId).getSiddhiAppThread().stop();
        }

        Thread siddhiAppThread = new Thread(siddhiAppRunner);
        if (this.trafficUsers.containsKey(userId)) {
            this.trafficUsers.get(userId).setSiddhiAppThread(siddhiAppThread);
        }
        siddhiAppThread.start();
        Thread emitterThread = new Thread(emitterRunner);
        emitterThread.start();
        return sseEmitter;
    }

    @GetMapping("/browsers")
    @CrossOrigin
    public SseEmitter browserData(String userId) throws CredentialException, IOException, InterruptedException {
        final long[] time = {System.currentTimeMillis()};
        LinkedBlockingQueue<Event[]> linkedBlockingQueue = new LinkedBlockingQueue<>();
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        Runnable siddhiAppRunner = new Runnable() {

            @Override
            public void run() {
                while (!browserUsers.containsKey(userId)) {
//                    Thread.onSpinWait();
                }
                String userQuery = browserUsers.get(userId).getQuery();
                SiddhiAppRuntime siddhiAppRuntime = getSiddhiAppRuntime(linkedBlockingQueue, userQuery);
                siddhiAppRuntime.start();
            }
        };

        Runnable emitterRunner = new Runnable() {
            @Override
            public void run() {

                executor.execute(() -> {
                    try {
                        List<Object> responses = new ArrayList<>(4);
                        while(true) {
                            Event[] event = linkedBlockingQueue.take();
                            System.out.println("Browser Data: " + event[0].getData());
                            responses.add(event[0].getData());
                            String initial = event[0].getData()[event[0].getData().length-1].toString();
                            System.out.println("Browser Latencies");
                            calculateLatency(event, initial, time);
                            System.out.println("...............");
                            if (responses.size() == 4) {
                                sseEmitter.send(responses);
                                responses.clear();
//                                emitter.complete();
                            }
                        }


                    } catch (Exception ex) {
                        sseEmitter.completeWithError(ex);
                    }
//            }
                });

            }
        };

        if (this.browserUsers.containsKey(userId) && this.browserUsers.get(userId).getSiddhiAppThread() != null) {
            this.browserUsers.get(userId).getSiddhiAppThread().stop();
        }
        Thread siddhiAppThread = new Thread(siddhiAppRunner);
        if (this.browserUsers.containsKey(userId)) {
            this.browserUsers.get(userId).setSiddhiAppThread(siddhiAppThread);
        }
        Thread emitterThread = new Thread(emitterRunner);
        siddhiAppThread.start();
        emitterThread.start();

        return sseEmitter;
    }

    @GetMapping("/query")
    @CrossOrigin
    public SseEmitter anyQueryData(String userId) throws CredentialException, IOException, InterruptedException {

        final long[] time = {System.currentTimeMillis()};
        LinkedBlockingQueue<Event[]> linkedBlockingQueue = new LinkedBlockingQueue<>();
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        Runnable siddhiAppRunner = new Runnable() {

            @Override
            public void run() {
                while (!anyQueryUsers.containsKey(userId)) {
//                    Thread.onSpinWait();
                }
                String userQuery = anyQueryUsers.get(userId).getQuery();
                SiddhiAppRuntime siddhiAppRuntime = getSiddhiAppRuntime(linkedBlockingQueue, userQuery);
                siddhiAppRuntime.start();
            }
        };

        Runnable emitterRunner = new Runnable() {
            @Override
            public void run() {

                executor.execute(() -> {
                    try {
                        while(true) {
                            Event[] event = linkedBlockingQueue.take();
                            String initial = event[0].getData()[event[0].getData().length-1].toString();
                            calculateLatency(event, initial, time);
                            sseEmitter.send(event[0].getData());
//                                emitter.complete();
                        }
                    } catch (Exception ex) {
                        sseEmitter.completeWithError(ex);
                    }
//            }
                });

            }
        };

        if (this.anyQueryUsers.containsKey(userId) && this.anyQueryUsers.get(userId).getSiddhiAppThread() != null) {
            this.anyQueryUsers.get(userId).getSiddhiAppThread().stop();
        }
        Thread siddhiAppThread = new Thread(siddhiAppRunner);
        if (this.anyQueryUsers.containsKey(userId)) {
            this.anyQueryUsers.get(userId).setSiddhiAppThread(siddhiAppThread);
        }
        Thread emitterThread = new Thread(emitterRunner);
        siddhiAppThread.start();
        emitterThread.start();
        return sseEmitter;
    }
}
