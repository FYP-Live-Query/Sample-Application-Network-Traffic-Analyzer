package SiddhiAppComposites.Statement.JoinStatement;

import SiddhiAppComposites.ISiddhiAppComposite;
import net.sf.jsqlparser.statement.select.FromItem;

public interface IJoinStatement extends ISiddhiAppComposite {
    String toString();
    void addJoins(FromItem fromItem);
}
