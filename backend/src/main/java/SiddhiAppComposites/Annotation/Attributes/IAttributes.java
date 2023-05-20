package SiddhiAppComposites.Annotation.Attributes;

import SiddhiAppComposites.Annotation.Common.ICommonAnnotationComposite;
import SiddhiAppComposites.Annotation.IAnnotation;
import SiddhiAppComposites.Annotation.Map.IMapComposite;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.HashSet;

public abstract class IAttributes implements IAnnotation, IMapComposite {
    HashSet<ISiddhiAppComposite> annotationComposites = new HashSet<>(10);;

    public IAttributes addAttributeComposite(ICommonAnnotationComposite iCommonAnnotationComposite){
        this.annotationComposites.add(iCommonAnnotationComposite);
        return this;
    }
}
