package Compiler;

import Engine.IEngine;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;

public class CustomSelectItemVisitorImpl implements SelectItemVisitor {
    private final IEngine middleEngine;

    public CustomSelectItemVisitorImpl(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }

    @Override
    public void visit(AllColumns allColumns) {
        allColumns.accept(new CustomExpressionVisitorAdaptor(middleEngine));
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        allTableColumns.accept(new CustomExpressionVisitorAdaptor(middleEngine));
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        Expression itemExpression = selectExpressionItem.getExpression();
        // middle engine behavior is select item handling behavior

        itemExpression.accept(new CustomExpressionVisitorAdaptor(middleEngine));

        Alias aliasOfSelectExpressionItem = selectExpressionItem.getAlias();
        if(aliasOfSelectExpressionItem == null){

        }else {
            middleEngine.handleAlias(aliasOfSelectExpressionItem);
        }
    }
}
