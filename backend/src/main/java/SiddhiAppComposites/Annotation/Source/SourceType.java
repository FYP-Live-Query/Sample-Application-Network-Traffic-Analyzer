package SiddhiAppComposites.Annotation.Source;

import SiddhiAppComposites.SupportedDataTypes;

public enum SourceType {
    LIVE("live"),
    HTTP("http"),
    KAFKA("kafka"),
    TCP("tcp"),
    EMAIL("email"),
    FILE("file");

    private final String sourceType;

    SourceType(String sourceType){
        this.sourceType = sourceType;
    }

    public String getSourceTypeSignature() {
        return sourceType;
    }

    public static boolean isSourceTypeSupported(String sourceType){
        for(SupportedDataTypes supportedDataTypes : SupportedDataTypes.values()){
            if(supportedDataTypes.getDataTypeSignature().equalsIgnoreCase(sourceType)){
                return true;
            }
        }
        return false; // throw an exception
    }
}
