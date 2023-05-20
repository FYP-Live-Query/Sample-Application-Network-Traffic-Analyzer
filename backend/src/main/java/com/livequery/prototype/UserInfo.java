package com.livequery.prototype;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.query.api.SiddhiApp;

public class UserInfo {
    String query;
    String id;
    String apiKey;

    SiddhiAppRuntime siddhiAppRuntime;
    Thread siddhiAppThread;

    public UserInfo(@JsonProperty("query") String query, @JsonProperty("id") String id, @JsonProperty("apiKey") String apiKey) {
        this.query = query;
        this.id = id;
        this.apiKey = apiKey;
        this.siddhiAppRuntime = null;
        this.siddhiAppThread = null;
    }

    public String getQuery() {
        return query;
    }

    public String getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public SiddhiAppRuntime getSiddhiAppRuntime() {
        return siddhiAppRuntime;
    }

    public void setSiddhiAppRuntime(SiddhiAppRuntime siddhiAppRuntime) {
        this.siddhiAppRuntime = siddhiAppRuntime;
    }

    public Thread getSiddhiAppThread() {
        return siddhiAppThread;
    }

    public void setSiddhiAppThread(Thread siddhiAppThread) {
        this.siddhiAppThread = siddhiAppThread;
    }
}