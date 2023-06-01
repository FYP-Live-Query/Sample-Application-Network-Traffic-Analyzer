package Compiler;

import Engine.FromItemHandlingBehaviorEngine;
import Engine.IEngine;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

public class CustomJoinItemVisitorImpl implements FromItemVisitor{
    private IEngine middleEngine;

    public CustomJoinItemVisitorImpl(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }

    @Override
    public void visit(Table table) {
        middleEngine.setExpressionHandlingBehavior(new FromItemHandlingBehaviorEngine());
        middleEngine.handleTable(table);
    }

    @Override
    public void visit(SubSelect subSelect) {
        throw new UnsupportedOperationException("Sub selects not supported");
    }

    @Override
    public void visit(SubJoin subJoin) {
        throw new UnsupportedOperationException("sub joins not supported");
    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        throw new UnsupportedOperationException("lateral sub select not supported");
    }

    @Override
    public void visit(ValuesList valuesList) {
        throw new UnsupportedOperationException("value list not supported");
    }

    @Override
    public void visit(TableFunction tableFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ParenthesisFromItem parenthesisFromItem) {
        throw new UnsupportedOperationException();
    }
}
