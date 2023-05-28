package com.livequery.prototype;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.persistence.InMemoryPersistenceStore;
import io.siddhi.core.util.persistence.PersistenceStore;
import io.siddhi.core.event.Event;
import io.siddhi.extension.map.json.sourcemapper.JsonSourceMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.auth.login.CredentialException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import SiddhiAppComposites.Annotation.Attributes.JsonMapAttributes;
import SiddhiAppComposites.Annotation.Common.KeyValue;
import SiddhiAppComposites.Annotation.Info.QueryInfo;
import SiddhiAppComposites.Annotation.Map.JsonMap;
import SiddhiAppComposites.Annotation.Sink.LogSink;
import SiddhiAppComposites.SiddhiApp;
import SiddhiAppComposites.SiddhiAppGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

@SpringBootApplication
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

    private final PersistenceStore persistenceStore;
    private final InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
    private final List<Long> latencyValues = new CopyOnWriteArrayList<>();

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final AtomicInteger iterateID = new AtomicInteger(0);

    public Controller(MeterRegistry meterRegistry) throws UnknownHostException {
        this.persistenceStore = new InMemoryPersistenceStore();
        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setPersistenceStore(persistenceStore);
        this.siddhiManager.setExtension("live", live.source.LiveSource.class);
        this.siddhiManager.setExtension("map-json", JsonSourceMapper.class);
        this.meterRegistry = meterRegistry;
        this.trafficUsers = new HashMap<>();
        this.browserUsers = new HashMap<>();
        this.anyQueryUsers = new HashMap<>();
        executorService.scheduleAtFixedRate(this::writeLatencyValuesToCsv, 1, 1, TimeUnit.MINUTES);
    }

    private static SseEmitter getSseEmitter() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));

        sseEmitter.onError((ex) -> LOGGER.info("SseEmitter got error:", ex));
        return sseEmitter;
    }

    private SiddhiAppRuntime getSiddhiAppRuntime(LinkedBlockingQueue<Event[]> linkedBlockingQueue, String query) {
        String siddhiAppName ="SiddhiApp-dev-test";
        SiddhiApp siddhiApp = SiddhiAppGenerator.generateSiddhiApp(
                siddhiAppName,
                query,
                new SiddhiAppComposites.Annotation.Source.LiveSource()
                        .addSourceComposite(new KeyValue<>("host.name","20.38.38.218:9092"))
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
        persistenceStore.save(siddhiAppName,"table.name",siddhiApp.getTableName().getBytes());
        persistenceStore.save(siddhiAppName,"database.name","inventory".getBytes());
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiAppString);

        String userId = "ZXCVB";
        StringBuilder str1 = new StringBuilder("id-");
        long start = System.currentTimeMillis();
        str1.append(iterateID.incrementAndGet());
        String uniqueId = str1.toString();
        final long[] time = {System.currentTimeMillis()};
        siddhiAppRuntime.addCallback("SQL-SiddhiQL-dev-test", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                String initial = "event[0].getData()[event[0].getData().length-1].toString()";
                try {
                    calculateLatency(inEvents, initial, time,uniqueId,start);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return siddhiAppRuntime;
    }

    private void calculateLatency(Event[] event, String initial, long[] time, String prometheus_query,long start) throws IOException {
        long current = System.currentTimeMillis();
        long updatedTime = Long.parseLong(event[0].getData()[event[0].getData().length - 1].toString());
        long traffic_latency = current - updatedTime;
        latencyValues.add(traffic_latency);
        meterRegistry.timer(prometheus_query).record(Duration.ofMillis(traffic_latency));
    }

    private synchronized void writeLatencyValuesToCsv() {
        try {
            // Calculate average and 90th percentile of latency values
            double averageLatency = latencyValues.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(Double.NaN);
            double percentile95Latency = latencyValues.stream()
                    .sorted()
                    .skip((long) (latencyValues.size() * 0.95))
                    .findFirst()
                    .orElse(0L);
            double percentile99Latency = latencyValues.stream()
                    .sorted()
                    .skip((long) (latencyValues.size() * 0.99))
                    .findFirst()
                    .orElse(0L);
            // Write average and 90th percentile of latency values to CSV file
            FileWriter csvWriter = new FileWriter("latency_values.csv", true);
            csvWriter.append(Double.toString(averageLatency));
            csvWriter.append(",");
            csvWriter.append(Double.toString(percentile95Latency));
            csvWriter.append(",");
            csvWriter.append(Double.toString(percentile99Latency));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
            LOGGER.info("averageLatency" + averageLatency + "," + "percentile95Latency" + percentile95Latency + "," +"percentile99Latency" + percentile99Latency);
            latencyValues.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void shutdown() {
        // Shutdown the executor service when the program is exiting
        executorService.shutdown();
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
    public SseEmitter trafficData() throws CredentialException, IOException, InterruptedException {
        String userId = "ZXCVB";
        StringBuilder str1 = new StringBuilder("id-");
        long start =System.currentTimeMillis();
        str1.append(iterateID.incrementAndGet());
        String uniqueId = str1.toString();

        final long[] time = {System.currentTimeMillis()};
        LinkedBlockingQueue<Event[]> linkedBlockingQueue = new LinkedBlockingQueue<>();

        Runnable siddhiAppRunner = () -> {
            while (!trafficUsers.containsKey(userId)) {
//                    Thread.onSpinWait();
            }
            String userQuery = trafficUsers.get(userId).getQuery();
            SiddhiAppRuntime siddhiAppRuntime = getSiddhiAppRuntime(linkedBlockingQueue, userQuery);
            trafficUsers.get(userId).setSiddhiAppRuntime(siddhiAppRuntime);
            siddhiAppRuntime.start();
        };
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
//        Runnable emitterRunner = () -> {
//            try {
//                List<Object> responses = new ArrayList<>(5);
//                while(true) {
//                    Event[] event = linkedBlockingQueue.take();
//                    String initial = "event[0].getData()[event[0].getData().length-1].toString()";
//                    calculateLatency(event, initial, time,uniqueId,start);
////                            System.out.println("Event in Backend: " + event[0].getData());
////                            if (responses.size() == 5) {
////                                sseEmitter.send(event);
////                                responses.clear();
////                                emitter.complete();
////                            }
//                }
//            } catch (Exception ex) {
//                sseEmitter.completeWithError(ex);
//            }
//        };

        if (this.trafficUsers.containsKey(userId) && this.trafficUsers.get(userId).getSiddhiAppThread() != null) {
            this.trafficUsers.get(userId).getSiddhiAppThread().stop();
        }

        Thread siddhiAppThread = new Thread(siddhiAppRunner, "siddhiAppRunner");
        if (this.trafficUsers.containsKey(userId)) {
            this.trafficUsers.get(userId).setSiddhiAppThread(siddhiAppThread);
        }
        siddhiAppThread.start();
        return sseEmitter;
    }

    @GetMapping("/browsers")
    @CrossOrigin
    public SseEmitter browserData(String userId) throws CredentialException, IOException, InterruptedException {
        final long[] time = {System.currentTimeMillis()};
        long start =System.currentTimeMillis();
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
                            calculateLatency(event, initial, time,userId,start);
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
        long start =System.currentTimeMillis();
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
                System.out.println("queue1: "+linkedBlockingQueue.size());
                siddhiAppRuntime.start();
            }
        };

        Runnable emitterRunner = new Runnable() {
            @Override
            public void run() {

                executor.execute(() -> {
                    try {
                        while(true) {
                            System.out.println("queue"+linkedBlockingQueue.size());
                            Event[] event = linkedBlockingQueue.take();
                            System.out.println("event arrived");
//                            String initial = event[0].getData()[event[0].getData().length-1].toString();
                            calculateLatency(event, "", time,"query.latency",start);

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

    @GetMapping("/time")
    @CrossOrigin
    public SseEmitter streamDateTime() {

        SseEmitter sseEmitter = getSseEmitter();

        executor.execute(() -> {
            for (int i = 0; i < 15; i++) {
                try {
                    sseEmitter.send(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
                    sleep(1, sseEmitter);
                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
            sseEmitter.complete();
        });

        LOGGER.info("Controller exits");
        return sseEmitter;
    }

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }
}

