package SiddhiAppComposites.Annotation.Map;

import SiddhiAppComposites.Annotation.AnnotationType;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.Iterator;

public class JsonMap extends IMap {
    private final MapType jsonMap = MapType.JSON;
    private final StringBuilder jsonMapAnnotation = new StringBuilder("");
    private final AnnotationType jsonMapSignature = AnnotationType.MAP;

    public JsonMap() {

    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        jsonMapAnnotation
                .append(jsonMapSignature.getAnnotationTypeSignature())
                .append("(").append("type = \"").append(jsonMap.getMapTypeSignature()).append("\""); // @map(type = "json",
        Iterator<ISiddhiAppComposite> sourceAnnotationCompositesIterator = annotationComposites.iterator();

        while(sourceAnnotationCompositesIterator.hasNext()){
            if(sourceAnnotationCompositesIterator.hasNext()){
                jsonMapAnnotation.append(",");
            }
            jsonMapAnnotation.append(sourceAnnotationCompositesIterator.next().getSiddhiAppCompositeAsString());
        }

        jsonMapAnnotation
                .append(")"); // @map(type = "json", .....)

        return jsonMapAnnotation.toString();
    }
}
