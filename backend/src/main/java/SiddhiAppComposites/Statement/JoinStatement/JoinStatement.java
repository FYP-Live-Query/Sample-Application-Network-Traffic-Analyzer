package SiddhiAppComposites.Statement.JoinStatement;

import net.sf.jsqlparser.statement.select.FromItem;

import java.util.ArrayList;

public class JoinStatement implements IJoinStatement {

    private final ArrayList<FromItem> joinStatements;
    public JoinStatement() {
        this.joinStatements = new ArrayList<FromItem>();
    }

    public void addJoins(FromItem fromItem) {
        joinStatements.add(fromItem);
    }
    @Override
    public String getSiddhiAppCompositeAsString() {
        return null;
    }
}