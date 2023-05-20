package SiddhiAppComposites.Annotation.Sink;

import SiddhiAppComposites.SupportedDataTypes;

public enum SinkType {
    LOG("log"),LIVE("live");

    private final String sinkType;

    SinkType(String sinkType){
        this.sinkType = sinkType;
    }

    public String getSinkTypeSignature() {
        return sinkType;
    }

    public static boolean isSinkTypeSupported(String sinkType){
        for(SupportedDataTypes supportedDataTypes : SupportedDataTypes.values()){
            if(supportedDataTypes.getDataTypeSignature().equalsIgnoreCase(sinkType)){
                return true;
            }
        }
        return false; // throw an exception
    }
}
