package SiddhiAppComposites.Annotation.Info;

import SiddhiAppComposites.Annotation.AnnotationType;

public class QueryInfo extends IInfo {
    private final AnnotationType queryInfo = AnnotationType.INFO;
    private String queryName = "default-name";
    private final StringBuilder infoAnnotation = new StringBuilder("");

    public QueryInfo setQueryName(String queryName){
        this.queryName = queryName;
        return this;
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        infoAnnotation.append(queryInfo.getAnnotationTypeSignature()).append("(name = \"").append(queryName).append("\")\n");
        return infoAnnotation.toString();
    }
}
