package com.livequery.prototype;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.query.api.SiddhiApp;

public class UserInfo {
    String query;
    String id;
    String apiKey;

    SiddhiAppRuntime siddhiAppRuntime;
    Thread siddhiAppThread;
    public UserInfo(String query, String id, String apiKey) {
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
