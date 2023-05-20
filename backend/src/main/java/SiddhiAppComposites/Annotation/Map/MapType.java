package SiddhiAppComposites.Annotation.Map;

import SiddhiAppComposites.SupportedDataTypes;

public enum MapType {
    JSON("json"),
    XML("xml"),
    CSV("csv");
//    TCP("tcp"),
//    EMAIL("email"),
//    FILE("file");

    private final String mapType;
    MapType(String mapType){
        this.mapType = mapType;
    }

    public String getMapTypeSignature() {
        return mapType;
    }

    public static boolean isMapTypeSupported(String mapType){
        for(SupportedDataTypes supportedDataTypes : SupportedDataTypes.values()){
            if(supportedDataTypes.getDataTypeSignature().equalsIgnoreCase(mapType)){
                return true;
            }
        }
        return false; // throw an exception
    }
}