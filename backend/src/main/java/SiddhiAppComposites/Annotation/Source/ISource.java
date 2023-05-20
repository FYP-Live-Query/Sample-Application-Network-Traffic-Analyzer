package SiddhiAppComposites.Annotation.Source;


import SiddhiAppComposites.Annotation.Common.ICommonAnnotationComposite;
import SiddhiAppComposites.Annotation.IAnnotation;
import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.ArrayList;
import java.util.List;

public abstract class ISource implements IAnnotation {
    List<ISiddhiAppComposite> annotationComposites = new ArrayList<>(10);;

    public ISource addSourceComposite(ISourceComposite iSourceComposite){
        this.annotationComposites.add(iSourceComposite);
        return this;
    }

    public ISource addSourceComposite(ICommonAnnotationComposite iCommonAnnotationComposite){
        this.annotationComposites.add(iCommonAnnotationComposite);
        return this;
    }

}
