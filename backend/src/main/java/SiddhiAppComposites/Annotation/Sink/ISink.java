package SiddhiAppComposites.Annotation.Sink;

import SiddhiAppComposites.Annotation.Common.ICommonAnnotationComposite;
import SiddhiAppComposites.Annotation.IAnnotation;
import SiddhiAppComposites.Annotation.Map.IMap;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.ArrayList;
import java.util.List;

public abstract class ISink implements IAnnotation {
    List<ISiddhiAppComposite> annotationComposites = new ArrayList<>(10);;

    public ISink addSourceComposite(ICommonAnnotationComposite iCommonAnnotationComposite){
        this.annotationComposites.add(iCommonAnnotationComposite);
        return this;
    }

    public ISink addMapComposite(IMap mapComposite){
        this.annotationComposites.add(mapComposite);
        return this;
    }
}
