package SiddhiAppComposites;

public enum SupportedDataTypes {
    STRING("string"),
    BOOL("bool"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double");

    private final String dataTypeSignature;

    SupportedDataTypes(String dataTypeSignature){
        this.dataTypeSignature = dataTypeSignature;
    }

    public String getDataTypeSignature() {
        return dataTypeSignature;
    }

    public static boolean isDataTypeSupported(String dataTypeSignature){
        for(SupportedDataTypes supportedDataTypes : SupportedDataTypes.values()){
            if(supportedDataTypes.getDataTypeSignature().equalsIgnoreCase(dataTypeSignature)){
                return true;
            }
        }
        return false; // throw an exception
    }

}
