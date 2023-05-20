package Engine;

import SiddhiAppComposites.AggregateFunction;
import SiddhiAppComposites.ColumnWithDataType;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.Stack;

public class WhereExpressionHandlingBehaviorEngine extends IEngineExpressionHandleBehavior {
    private final int COLUMN_NAME_INDEX = 0;
    private final int DATA_TYPE_INDEX = 1;
    private String[] ColumnNameAndDataTypeArr; // length is 2 ["columnName","dataType"]

    private SiddhiAppComposites.Column siddhiColumn;
    private AggregateFunction aggregateFunction;
    private final Stack<AggregateFunction> aggregateFunctionsStack = new Stack<>();

    public WhereExpressionHandlingBehaviorEngine() {
    }

    @Override
    public void handleTable(Table table) {
        throw new UnsupportedOperationException("Table names in where statement not supported.");
    }

    private String getColumnName(){
        return ColumnNameAndDataTypeArr[COLUMN_NAME_INDEX];
    }

    private String getDataType(){
        return ColumnNameAndDataTypeArr[DATA_TYPE_INDEX];
    }

    private void tokenizeColumnName(String sqlColumnWithDataType){
        ColumnNameAndDataTypeArr = sqlColumnWithDataType.split("[@]", 0);
        if(ColumnNameAndDataTypeArr.length != 2){throw new IllegalArgumentException("provided column and data type format is should be \"ColumnName@DataType\". but provided : " + "[" + sqlColumnWithDataType + "] in Where Clause");}
    }

    @Override
    public void handleColumn(Column sqlColumnWithDataType) {
        tokenizeColumnName(sqlColumnWithDataType.getColumnName()); // eg - this tokenize "ColumnName@DataType" to ["ColumnName", "DataType"]
        SiddhiAppComposites.Column siddhiColumn = new SiddhiAppComposites.Column();
        siddhiColumn.setName(getColumnName());
        siddhiApp.addColumnWithDataTypeToInputStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType())); // add to stream definition
        // if still processing on function attributes add to function attribute list
        if(aggregateFunctionsStack.empty()) {
            siddhiApp.addSymbolToFilterExpression(siddhiColumn.getName());
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiColumn); // add to function (this is a function inside function) attribute
        }
    }

    @Override
    public void handleFunctionExit(Function function) {
        aggregateFunction = aggregateFunctionsStack.pop();
        if(aggregateFunctionsStack.empty()) {
            siddhiApp.addSymbolToFilterExpression(aggregateFunction.getSiddhiAppCompositeAsString()); // selectItem.addSelectItemComposite(aggregateFunction);
        }
        else{
            aggregateFunctionsStack.peek().addAttribute(aggregateFunction);
        }
    }

    @Override
    public void handleFunctionBegin(Function function) {
        aggregateFunction = new AggregateFunction(function.getName());
        aggregateFunctionsStack.push(aggregateFunction);
    }

    @Override
    public void handleSignedExpression(SignedExpression signedExpression) {
        throw new UnsupportedOperationException("Signed Expression " + signedExpression.getExpression() +" not supported.");
    }

    @Override
    public void handleDoubleValue(DoubleValue doubleValue) {
        siddhiApp.addSymbolToFilterExpression(doubleValue.toString());
    }

    @Override
    public void handleLongValue(LongValue longValue) {
        siddhiApp.addSymbolToFilterExpression(longValue.toString());
    }

    @Override
    public void handleParenthesis(Parenthesis parenthesis) {

    }

    @Override
    public void handleStringValue(StringValue stringValue) {
        siddhiApp.addSymbolToFilterExpression(stringValue.toString());
    }

    @Override
    public void handleAddition(Addition addition) {
        siddhiApp.addSymbolToFilterExpression(addition.getStringExpression());
    }

    @Override
    public void handleDivision(Division division) {
        siddhiApp.addSymbolToFilterExpression(division.getStringExpression());
    }

    @Override
    public void handleIntegerDivision(IntegerDivision integerDivision) {
        throw new UnsupportedOperationException("Integer division " + integerDivision.getStringExpression() + " not supported.");
    }

    @Override
    public void handleMultiplication(Multiplication multiplication) {
        siddhiApp.addSymbolToFilterExpression(multiplication.getStringExpression());
    }

    @Override
    public void handleSubtraction(Subtraction subtraction) {
        siddhiApp.addSymbolToFilterExpression(subtraction.getStringExpression());
    }

    @Override
    public void handleAndExpression(AndExpression andExpression) {
        siddhiApp.addSymbolToFilterExpression(andExpression.getStringExpression());
    }

    @Override
    public void handleOrExpression(OrExpression orExpression) {
        siddhiApp.addSymbolToFilterExpression(orExpression.getStringExpression());
    }

    @Override
    public void handleXorExpression(XorExpression xorExpression) {
        siddhiApp.addSymbolToFilterExpression(xorExpression.getStringExpression());
    }

    @Override
    public void handleEqualsTo(EqualsTo equalsTo) {
        siddhiApp.addSymbolToFilterExpression("==");
    }

    @Override
    public void handleGreaterThan(GreaterThan greaterThan) {
        siddhiApp.addSymbolToFilterExpression(greaterThan.getStringExpression());
    }

    @Override
    public void handleGreaterThanEquals(GreaterThanEquals greaterThanEquals) {
        siddhiApp.addSymbolToFilterExpression(greaterThanEquals.getStringExpression());
    }

    @Override
    public void handleMinorThan(MinorThan minorThan) {
        siddhiApp.addSymbolToFilterExpression(minorThan.getStringExpression());
    }

    @Override
    public void handleOpenBracket() {
        siddhiApp.addSymbolToFilterExpression("(");

    }

    @Override
    public void handleCloseBracket() {
        siddhiApp.addSymbolToFilterExpression(")");

    }



    @Override
    public void handleMinorThanEquals(MinorThanEquals minorThanEquals) {
        siddhiApp.addSymbolToFilterExpression(minorThanEquals.getStringExpression());
    }

    @Override
    public void handleNotEqualsTo(NotEqualsTo notEqualsTo) {
        siddhiApp.addSymbolToFilterExpression(notEqualsTo.getStringExpression());
    }

    @Override
    public void handleAlias(Alias alias) {
        siddhiApp.addSymbolToFilterExpression(alias.getName());
    }

    @Override
    public void addToSiddhiApp() {
        throw new UnsupportedOperationException();

    }
}
