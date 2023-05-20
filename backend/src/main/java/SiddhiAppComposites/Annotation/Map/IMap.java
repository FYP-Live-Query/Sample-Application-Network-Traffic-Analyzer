package SiddhiAppComposites.Annotation.Map;

import SiddhiAppComposites.Annotation.Common.ICommonAnnotationComposite;
import SiddhiAppComposites.Annotation.IAnnotation;
import SiddhiAppComposites.Annotation.Source.ISourceComposite;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.ArrayList;
import java.util.List;

public abstract class IMap implements IAnnotation, ISourceComposite {
    List<ISiddhiAppComposite> annotationComposites = new ArrayList<>(10);;

    public IMap addMapComposite(IMapComposite iMapComposite){
        this.annotationComposites.add(iMapComposite);
        return this;
    }

    public IMap addMapComposite(ICommonAnnotationComposite iCommonAnnotationComposite){
        this.annotationComposites.add(iCommonAnnotationComposite);
        return this;
    }
}
