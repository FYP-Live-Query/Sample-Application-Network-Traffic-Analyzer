package Compiler;

import Engine.IEngine;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

public class CustomItemListVisitor implements ItemsListVisitor {
    private IEngine middleEngine;

    public CustomItemListVisitor(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }


    @Override
    public void visit(SubSelect subSelect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ExpressionList expressionList) {
        List<Expression> expressions = expressionList.getExpressions();
        for (Expression expression : expressions) expression.accept(new CustomExpressionVisitorAdaptor(middleEngine));
    }

    @Override
    public void visit(NamedExpressionList namedExpressionList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(MultiExpressionList multiExpressionList) {
        throw new UnsupportedOperationException();
    }
}
