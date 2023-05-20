package SiddhiAppComposites;

public enum SupportedAggregationFunctions {
    SUM("sum",SupportedDataTypes.LONG),
    COUNT("count",SupportedDataTypes.LONG), // here string mean could be any type of data.
    DISTINCT_COUNT("distinctCount",SupportedDataTypes.LONG), // here String mean any type of data
    AVG("avg",SupportedDataTypes.DOUBLE),
    MAX("max",SupportedDataTypes.LONG),
    MIN("min",SupportedDataTypes.LONG),
    MAXFOREVER("maxForever",SupportedDataTypes.DOUBLE),
    MINFOREVER("minForever",SupportedDataTypes.DOUBLE),
    STDDEV("stdDev",SupportedDataTypes.DOUBLE);

    private final String functionSignature;
    private final SupportedDataTypes defaultDataType;

    SupportedAggregationFunctions(String functionSignature, SupportedDataTypes defaultDataType){
        this.functionSignature = functionSignature;
        this.defaultDataType = defaultDataType;
    }

    public SupportedDataTypes getDefaultDataType() {
        return defaultDataType;
    }

    public String getFunctionSignature() {
        return functionSignature;
    }

    public static boolean isFunctionSupported(String functionSignature){
        for(SupportedAggregationFunctions supportedAggregationFunction : SupportedAggregationFunctions.values()){
            if(supportedAggregationFunction.getFunctionSignature().equalsIgnoreCase(functionSignature)){
                return true;
            }
        }
        return false; // throw an exception
    }

}
