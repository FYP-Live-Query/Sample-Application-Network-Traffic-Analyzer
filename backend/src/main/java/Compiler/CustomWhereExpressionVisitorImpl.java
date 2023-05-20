package Compiler;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SubSelect;

public class CustomWhereExpressionVisitorImpl implements ExpressionVisitor {

    @Override
    public void visit(BitwiseRightShift bitwiseRightShift) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(bitwiseRightShift.getStringExpression());
    }

    @Override
    public void visit(BitwiseLeftShift bitwiseLeftShift) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(bitwiseLeftShift.getStringExpression());
    }

    @Override
    public void visit(NullValue nullValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(nullValue.toString());
    }

    @Override
    public void visit(Function function) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(function.getName());
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(signedExpression.getExpression());
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jdbcParameter.toString());
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jdbcNamedParameter.getName());
    }

    @Override
    public void visit(DoubleValue doubleValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(doubleValue.getValue());
    }

    @Override
    public void visit(LongValue longValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(longValue.getValue());
    }

    @Override
    public void visit(HexValue hexValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(hexValue.getValue());
    }

    @Override
    public void visit(DateValue dateValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(dateValue.getValue());
    }

    @Override
    public void visit(TimeValue timeValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(timeValue.getValue());
    }

    @Override
    public void visit(TimestampValue timestampValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(timestampValue.getValue());
    }

    @Override
    public void visit(Parenthesis parenthesis) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(parenthesis.getExpression());
    }

    @Override
    public void visit(StringValue stringValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(stringValue.getPrefix());
    }

    @Override
    public void visit(Addition addition) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(addition.getStringExpression());
    }

    @Override
    public void visit(Division division) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(division.getStringExpression());
    }

    @Override
    public void visit(IntegerDivision integerDivision) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(integerDivision.getStringExpression());
    }

    @Override
    public void visit(Multiplication multiplication) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(multiplication.getStringExpression());
    }

    @Override
    public void visit(Subtraction subtraction) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(subtraction.getStringExpression());
    }

    @Override
    public void visit(AndExpression andExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(andExpression.getStringExpression());
    }

    @Override
    public void visit(OrExpression orExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(orExpression.getStringExpression());
    }

    @Override
    public void visit(XorExpression xorExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(xorExpression.getStringExpression());
    }

    @Override
    public void visit(Between between) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(between.toString());
    }

    @Override
    public void visit(EqualsTo equalsTo) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(equalsTo.getStringExpression());
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(greaterThan.getStringExpression());
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(greaterThanEquals.getStringExpression());
    }

    @Override
    public void visit(InExpression inExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(inExpression.toString());
    }

    @Override
    public void visit(FullTextSearch fullTextSearch) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(fullTextSearch.toString());
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(isNullExpression.toString());
    }

    @Override
    public void visit(IsBooleanExpression isBooleanExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(isBooleanExpression.toString());
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(likeExpression.getStringExpression());
    }

    @Override
    public void visit(MinorThan minorThan) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(minorThan.getStringExpression());
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(minorThanEquals.getStringExpression());
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(notEqualsTo.getStringExpression());
    }

    @Override
    public void visit(Column column) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(column.toString());
    }

    @Override
    public void visit(SubSelect subSelect) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(subSelect.toString());
    }

    @Override
    public void visit(CaseExpression caseExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(caseExpression.toString());
    }

    @Override
    public void visit(WhenClause whenClause) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(whenClause.toString());
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(existsExpression.toString());
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(anyComparisonExpression.toString());
    }

    @Override
    public void visit(Concat concat) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(concat.getStringExpression());
    }

    @Override
    public void visit(Matches matches) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(matches.getStringExpression());
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(bitwiseAnd.getStringExpression());
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(bitwiseOr.getStringExpression());
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(bitwiseXor.getStringExpression());
    }

    @Override
    public void visit(CastExpression castExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(castExpression.toString());
    }

    @Override
    public void visit(TryCastExpression tryCastExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(tryCastExpression.toString());
    }

    @Override
    public void visit(Modulo modulo) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(modulo.getStringExpression());
    }

    @Override
    public void visit(AnalyticExpression analyticExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(analyticExpression.getExpression());
    }

    @Override
    public void visit(ExtractExpression extractExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(extractExpression.getExpression());
    }

    @Override
    public void visit(IntervalExpression intervalExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(intervalExpression.getExpression());
    }

    @Override
    public void visit(OracleHierarchicalExpression oracleHierarchicalExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(oracleHierarchicalExpression.toString());
    }

    @Override
    public void visit(RegExpMatchOperator regExpMatchOperator) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(regExpMatchOperator.getStringExpression());
    }

    @Override
    public void visit(JsonExpression jsonExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jsonExpression.getExpression());
    }

    @Override
    public void visit(JsonOperator jsonOperator) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jsonOperator.getStringExpression());
    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(regExpMySQLOperator.getStringExpression());
    }

    @Override
    public void visit(UserVariable userVariable) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(userVariable.getName());
    }

    @Override
    public void visit(NumericBind numericBind) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(numericBind.toString());
    }

    @Override
    public void visit(KeepExpression keepExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(keepExpression.getName());
    }

    @Override
    public void visit(MySQLGroupConcat mySQLGroupConcat) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(mySQLGroupConcat.toString());
    }

    @Override
    public void visit(ValueListExpression valueListExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(valueListExpression.toString());
    }

    @Override
    public void visit(RowConstructor rowConstructor) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(rowConstructor.getName());
    }

    @Override
    public void visit(RowGetExpression rowGetExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(rowGetExpression.getExpression());
    }

    @Override
    public void visit(OracleHint oracleHint) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(oracleHint.getValue());
    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(timeKeyExpression.toString());
    }

    @Override
    public void visit(DateTimeLiteralExpression dateTimeLiteralExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(dateTimeLiteralExpression.getValue());
    }

    @Override
    public void visit(NotExpression notExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(notExpression.getExpression());
    }

    @Override
    public void visit(NextValExpression nextValExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(nextValExpression.getName());
    }

    @Override
    public void visit(CollateExpression collateExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(collateExpression.toString());
    }

    @Override
    public void visit(SimilarToExpression similarToExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(similarToExpression.getStringExpression());
    }

    @Override
    public void visit(ArrayExpression arrayExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(arrayExpression.toString());
    }

    @Override
    public void visit(ArrayConstructor arrayConstructor) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(arrayConstructor.getExpressions());
    }

    @Override
    public void visit(VariableAssignment variableAssignment) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(variableAssignment.getExpression());
    }

    @Override
    public void visit(XMLSerializeExpr xmlSerializeExpr) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(xmlSerializeExpr.getExpression());
    }

    @Override
    public void visit(TimezoneExpression timezoneExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(timezoneExpression.toString());
    }

    @Override
    public void visit(JsonAggregateFunction jsonAggregateFunction) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jsonAggregateFunction.getExpression());
    }

    @Override
    public void visit(JsonFunction jsonFunction) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(jsonFunction.getExpressions());
    }

    @Override
    public void visit(ConnectByRootOperator connectByRootOperator) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(connectByRootOperator.toString());
    }

    @Override
    public void visit(OracleNamedFunctionParameter oracleNamedFunctionParameter) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(oracleNamedFunctionParameter.getExpression());
    }

    @Override
    public void visit(AllColumns allColumns) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(allColumns.toString());
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(allTableColumns.toString());
    }

    @Override
    public void visit(AllValue allValue) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(allValue.toString());
    }

    @Override
    public void visit(IsDistinctExpression isDistinctExpression) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(isDistinctExpression.getStringExpression());
    }

    @Override
    public void visit(GeometryDistance geometryDistance) {
        System.out.println("in " + CustomExpressionVisitorAdaptor.class);
        System.out.println(geometryDistance.getStringExpression());
    }
}
