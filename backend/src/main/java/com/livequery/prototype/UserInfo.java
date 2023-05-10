package com.livequery.prototype;

import io.siddhi.core.SiddhiAppRuntime;

public class UserInfo {
    String query;
    String id;
    String apiKey;
    Boolean locationEnabled;

    SiddhiAppRuntime siddhiAppRuntime;
    Thread siddhiAppThread;
    public UserInfo(String query, String id, String apiKey, Boolean locationEnabled) {
        this.query = query;
        this.id = id;
        this.apiKey = apiKey;
        this.locationEnabled = locationEnabled;
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

    public Boolean getLocationIsEnabled() {
        return locationEnabled;
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
