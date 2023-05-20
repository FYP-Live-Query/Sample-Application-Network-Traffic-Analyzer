package Compiler;

import Engine.FromItemHandlingBehaviorEngine;
import Engine.IEngine;
import Engine.SelectItemHandlingBehaviorEngine;
import Engine.WhereExpressionHandlingBehaviorEngine;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.values.ValuesStatement;

import java.util.List;

public class CustomSelectBodyVisitor implements SelectVisitor {

    private final IEngine middleEngine;

    public CustomSelectBodyVisitor(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        List<SelectItem> selectItemList = plainSelect.getSelectItems();
        for(SelectItem selectItem : selectItemList){
            middleEngine.setExpressionHandlingBehavior(new SelectItemHandlingBehaviorEngine());
            selectItem.accept(new CustomSelectItemVisitorImpl(middleEngine));
            middleEngine.addToSiddhiApp();
        }
        // seem like distinct not supported by siddhiQL
        Distinct distinct = plainSelect.getDistinct();
        if(distinct != null) {
            distinct.getOnSelectItems();
        }

        FromItem fromItem = plainSelect.getFromItem();
        if(fromItem != null) {
            middleEngine.setExpressionHandlingBehavior(new FromItemHandlingBehaviorEngine());
            fromItem.accept(new CustomFromItemVisitorImpl(middleEngine));
        }

        Expression whereExpression = plainSelect.getWhere();
        if(whereExpression != null) {
            middleEngine.setExpressionHandlingBehavior(new WhereExpressionHandlingBehaviorEngine());
            whereExpression.accept(new CustomExpressionVisitorAdaptor(middleEngine));
        }

        List<Join> joins = plainSelect.getJoins(); // not handled yet
        if(joins != null){
            for ( Join join : joins){
                List<Expression> onExpressions = (List<Expression>) join.getOnExpressions();
                if(onExpressions != null){
                    for(Expression onExpression : onExpressions){
                        onExpression.accept(new CustomExpressionVisitorAdaptor(middleEngine));
                    }
                }
            }
        }

        GroupByElement groupByElement = plainSelect.getGroupBy(); // not handled yet
        if(groupByElement != null) {
            groupByElement.accept(new CustomGroupByElementVisitor());
        }

        List<OrderByElement> orderByElements = plainSelect.getOrderByElements(); // not handled yet
        if(orderByElements != null) {
            for(OrderByElement orderByElement: orderByElements) {
                orderByElement.accept(new CustomOrderByElementVisitor(middleEngine));
            }
        }

        Expression havingExpression = plainSelect.getHaving(); // not handled yet
        if(havingExpression != null) {
            havingExpression.accept(new CustomExpressionVisitorAdaptor(middleEngine));
        }
    }

    @Override
    public void visit(SetOperationList setOperationList) {
        throw new UnsupportedOperationException("Set ops not supported.");
    }

    @Override
    public void visit(WithItem withItem) {
        throw new UnsupportedOperationException("With item not supported");
    }

    @Override
    public void visit(ValuesStatement valuesStatement) {
        throw new UnsupportedOperationException("values statement not supported");
    }
}
