package Compiler;

import Engine.IEngine;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

public class CustomExpressionVisitorAdaptor implements ExpressionVisitor {

    private final IEngine middleEngine;

    public CustomExpressionVisitorAdaptor(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }

    // siddhi do not support these
    @Override
    public void visit(BitwiseRightShift bitwiseRightShift) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BitwiseLeftShift bitwiseLeftShift) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(NullValue nullValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Function function) {
        middleEngine
                .handleFunctionBegin(function);

        ItemsListVisitor itemsListVisitor = new CustomItemListVisitor(middleEngine);

        NamedExpressionList namedExpressionList = function.getNamedParameters();
        if(namedExpressionList != null){
            namedExpressionList
                    .accept(itemsListVisitor);
        }

        ExpressionList expressionList = function.getParameters();
        if(expressionList != null){
            expressionList
                    .accept(itemsListVisitor);
        }

        List<OrderByElement> orderByElementList = function.getOrderByElements(); // deprecate support in sqltosiddhiql
        if(orderByElementList != null){
            for(OrderByElement orderByElement : orderByElementList){
                orderByElement
                        .accept(new CustomOrderByElementVisitor(middleEngine));
            }
        }

//        function.getKeep() - for Oracle KEEP keyword

        Expression attribute = function.getAttribute();
        if(attribute != null) {
            attribute
                    .accept(this);
        }
        middleEngine
                .handleFunctionExit(function);
    }

    @Override
    public void visit(SignedExpression signedExpression) {

        signedExpression.getSign();

        middleEngine.handleSignedExpression(signedExpression);

        Expression signExpression = signedExpression.getExpression();
        if(signExpression != null) {
            signExpression.accept(this);
        }
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(DoubleValue doubleValue) {

        middleEngine.handleDoubleValue(doubleValue);
    }

    @Override
    public void visit(LongValue longValue) {

        middleEngine.handleLongValue(longValue);
    }

    @Override
    public void visit(HexValue hexValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(DateValue dateValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(TimeValue timeValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(TimestampValue timestampValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Parenthesis parenthesis) {

        middleEngine.handleOpenBracket();
        Expression expression = parenthesis.getExpression();
        if(expression != null) {
            expression.accept(this);
        }
        middleEngine.handleCloseBracket();
    }

    @Override
    public void visit(StringValue stringValue) {

        middleEngine.handleStringValue(stringValue);
    }

    @Override
    public void visit(Addition addition) {
        Expression rightExpression = addition.getRightExpression();
        Expression leftExpression = addition.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleAddition(addition);
        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(Division division) {
        Expression rightExpression = division.getRightExpression();
        Expression leftExpression = division.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleDivision(division);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(IntegerDivision integerDivision) {
        Expression rightExpression = integerDivision.getRightExpression();
        Expression leftExpression = integerDivision.getLeftExpression();


        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleIntegerDivision(integerDivision);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(Multiplication multiplication) {
        Expression rightExpression = multiplication.getRightExpression();
        Expression leftExpression = multiplication.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleMultiplication(multiplication);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(Subtraction subtraction) {
        Expression rightExpression = subtraction.getRightExpression();
        Expression leftExpression = subtraction.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }
        middleEngine.handleSubtraction(subtraction);


        if(rightExpression != null){
            rightExpression.accept(this);
        }
    }

    @Override
    public void visit(AndExpression andExpression) {
        Expression rightExpression = andExpression.getRightExpression();
        Expression leftExpression = andExpression.getLeftExpression();


        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleAndExpression(andExpression);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(OrExpression orExpression) {
        Expression rightExpression = orExpression.getRightExpression();
        Expression leftExpression = orExpression.getLeftExpression();


        if(leftExpression != null){
            leftExpression.accept(this);
        }
        middleEngine.handleOrExpression(orExpression);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(XorExpression xorExpression) {
        Expression rightExpression = xorExpression.getRightExpression();
        Expression leftExpression = xorExpression.getLeftExpression();


        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleXorExpression(xorExpression);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(Between between) {

    }

    @Override
    public void visit(EqualsTo equalsTo) {
        Expression rightExpression = equalsTo.getRightExpression();
        Expression leftExpression = equalsTo.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleEqualsTo(equalsTo);

        if(rightExpression != null){
            rightExpression.accept(this);
        }
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        Expression rightExpression = greaterThan.getRightExpression();
        Expression leftExpression = greaterThan.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleGreaterThan(greaterThan);

        if(rightExpression != null){
            rightExpression.accept(this);
        }
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        Expression rightExpression = greaterThanEquals.getRightExpression();
        Expression leftExpression = greaterThanEquals.getLeftExpression();

        if(leftExpression != null){
            leftExpression.accept(this);
        }

        middleEngine.handleGreaterThanEquals(greaterThanEquals);

        if(rightExpression != null){
            rightExpression.accept(this);
        }

    }

    @Override
    public void visit(InExpression inExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(FullTextSearch fullTextSearch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(IsBooleanExpression isBooleanExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(MinorThan minorThan) {


        middleEngine.handleMinorThan(minorThan);
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {


        middleEngine.handleMinorThanEquals(minorThanEquals);
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {


        middleEngine.handleNotEqualsTo(notEqualsTo);
    }

    @Override
    public void visit(Column column) {
        middleEngine.handleColumn(column);
        // this function will hit by Function visitor - if function has
        // a column as an attribute or expression of multiple columns
        // then we have to extract those columns to put in "define stream" statement
        // in siddhi app (only this?) - so only to put in stream statement attribute list
        // also we have to find data type of the column. for all normal select items we provide string
        // if user doesn't specify the data type. for the function we can't do that. bcz some functions
        // don't accept strings. we can put float if user doesn't specify the data type since we only support aggregates. but how to
        // extend this? so we have to know from which function this column has extracted. then we can assume
        // it is a type that function accepts. eg. sum(column) only accept numeric values.
        // middleEngine.addColumn(column.getColumnName());

        // if we provide siddhi app as argument to this class constructor?
        // how to determine this column goes to where?

    }

    @Override
    public void visit(SubSelect subSelect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(CaseExpression caseExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(WhenClause whenClause) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Concat concat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Matches matches) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(CastExpression castExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(TryCastExpression tryCastExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Modulo modulo) {

    }

    @Override
    public void visit(AnalyticExpression analyticExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ExtractExpression extractExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(IntervalExpression intervalExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OracleHierarchicalExpression oracleHierarchicalExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(RegExpMatchOperator regExpMatchOperator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(JsonExpression jsonExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(JsonOperator jsonOperator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(UserVariable userVariable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(NumericBind numericBind) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(KeepExpression keepExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(MySQLGroupConcat mySQLGroupConcat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ValueListExpression valueListExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(RowConstructor rowConstructor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(RowGetExpression rowGetExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OracleHint oracleHint) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(DateTimeLiteralExpression dateTimeLiteralExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(NotExpression notExpression) {

    }

    @Override
    public void visit(NextValExpression nextValExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(CollateExpression collateExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(SimilarToExpression similarToExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ArrayExpression arrayExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ArrayConstructor arrayConstructor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(VariableAssignment variableAssignment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(XMLSerializeExpr xmlSerializeExpr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(TimezoneExpression timezoneExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(JsonAggregateFunction jsonAggregateFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(JsonFunction jsonFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(ConnectByRootOperator connectByRootOperator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OracleNamedFunctionParameter oracleNamedFunctionParameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(AllColumns allColumns) {

    }

    @Override
    public void visit(AllTableColumns allTableColumns) {

    }

    @Override
    public void visit(AllValue allValue) {

    }

    @Override
    public void visit(IsDistinctExpression isDistinctExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(GeometryDistance geometryDistance) {
        throw new UnsupportedOperationException();
    }
}
