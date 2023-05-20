package SiddhiAppComposites.Annotation;

import SiddhiAppComposites.SupportedDataTypes;

public enum AnnotationType {
    SOURCE("@source"),
    SINK("@sink"),
    INFO("@info"),
    MAP("@map"),
    ATTRIBUTES("@attributes"),
    APP("@app");
//    FILE("file");

    private final String annotationType;

    AnnotationType(String annotationType){
        this.annotationType = annotationType;
    }

    public String getAnnotationTypeSignature() {
        return annotationType;
    }

    public static boolean isAnnotationTypeSupported(String annotationType){
        for(SupportedDataTypes supportedDataTypes : SupportedDataTypes.values()){
            if(supportedDataTypes.getDataTypeSignature().equalsIgnoreCase(annotationType)){
                return true;
            }
        }
        return false; // throw an exception
    }
}
