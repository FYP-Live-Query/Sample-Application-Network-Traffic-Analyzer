package Compiler;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.GroupByVisitor;

public class CustomGroupByElementVisitor implements GroupByVisitor {
    @Override
    public void visit(GroupByElement groupByElement) {

        ExpressionList groupByExpressionList = groupByElement.getGroupByExpressionList();

//        groupByExpressionList.accept(new CustomItemListVisitor(middleEngine));
    }
}
