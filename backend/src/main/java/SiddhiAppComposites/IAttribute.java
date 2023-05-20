package SiddhiAppComposites;

import SiddhiAppComposites.utilities.visitors.IAttributeVisitor;

public interface IAttribute extends ISiddhiAppComposite {
    void accept(IAttributeVisitor iAttributeVisitor);
}
