package SiddhiAppComposites.Annotation.Source;

import SiddhiAppComposites.Annotation.AnnotationType;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.Iterator;

public class LiveSource extends ISource {
    private final SourceType liveSource = SourceType.LIVE;
    private final StringBuilder sourceAnnotation = new StringBuilder("");
    private final AnnotationType sourceSignature = AnnotationType.SOURCE;

    public LiveSource() {

    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        sourceAnnotation
                .append(sourceSignature.getAnnotationTypeSignature())
                .append("(").append("type = \"").append(liveSource.getSourceTypeSignature()).append("\","); // @source(type = "live",
        Iterator<ISiddhiAppComposite> sourceAnnotationCompositesIterator = annotationComposites.iterator();

       while(sourceAnnotationCompositesIterator.hasNext()){
            sourceAnnotation.append(sourceAnnotationCompositesIterator.next().getSiddhiAppCompositeAsString());
            if(sourceAnnotationCompositesIterator.hasNext()){
                sourceAnnotation.append(",");
            }
        }

        sourceAnnotation
                .append(")\n"); // @source(type = 'live', .....)

        return sourceAnnotation.toString();
    }
}
