package SiddhiAppComposites.Annotation.Sink;

import SiddhiAppComposites.Annotation.AnnotationType;
import SiddhiAppComposites.ISiddhiAppComposite;

public class LiveSink extends ISink{
    private final SinkType logSink= SinkType.LIVE;
    private final StringBuilder sinkAnnotation = new StringBuilder("");
    private final AnnotationType sinkSignature = AnnotationType.SINK;

    public LiveSink(){}

    @Override
    public String getSiddhiAppCompositeAsString() {
        sinkAnnotation
                .append(sinkSignature.getAnnotationTypeSignature())
                .append("(")
                .append("type = \"")
                .append(logSink.getSinkTypeSignature() + "\""); // @sink(type = "live",

        for (ISiddhiAppComposite annotationComposite : annotationComposites) {
            sinkAnnotation
                    .append(",")
                    .append(annotationComposite.getSiddhiAppCompositeAsString());
        }

        sinkAnnotation
                .append(")\n"); // @sink(type = "live", .....)

        return sinkAnnotation
                .toString();
    }
}
