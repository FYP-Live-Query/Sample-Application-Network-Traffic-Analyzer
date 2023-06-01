package Engine;

import SiddhiAppComposites.*;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SubJoin;

import java.util.*;

public class SelectItemHandlingBehaviorEngine extends IEngineExpressionHandleBehavior {
    private final int COLUMN_NAME_INDEX = 0;
    private final int DATA_TYPE_INDEX = 1;
    private String[] ColumnNameAndDataTypeArr;
    private boolean isHandledFunction;
    private List<SupportedDataTypes> dataTypesOfAttributesOfFunction; // for function handling
    private SiddhiAppComposites.Column siddhiColumn;
    private final SelectItem selectItem;
    private final Stack<AggregateFunction> aggregateFunctionsStack = new Stack<>();
    private AggregateFunction aggregateFunction;

    public SelectItemHandlingBehaviorEngine() {
        selectItem  = new SelectItem();
    }

    @Override
    public void handleTable(Table table) {
    }

    private String getColumnName(){
        return ColumnNameAndDataTypeArr[COLUMN_NAME_INDEX];
    }

    private String getDataType(){
        return ColumnNameAndDataTypeArr[DATA_TYPE_INDEX];
    }
    @Override
    public void handleJoin(SubJoin subJoin) {
        throw new UnsupportedOperationException("Joins in select statement not supported.");
    }

    private void tokenizeColumnName(String sqlColumnWithDataType){
        ColumnNameAndDataTypeArr = sqlColumnWithDataType.split("[@]", 0);
        if(ColumnNameAndDataTypeArr.length != 2){
            throw new IllegalArgumentException("provided column and data type format is " +
                    "should be \"ColumnName@DataType\". but provided : " +
                    "[" + sqlColumnWithDataType + "] in Select items");
        }
    }

    @Override
    public void handleColumn(Column sqlColumnWithDataType) {

        // eg - this tokenize "ColumnName@DataType" to ["ColumnName", "DataType"]
        tokenizeColumnName(sqlColumnWithDataType.getColumnName());
        siddhiColumn = new SiddhiAppComposites.Column(getColumnName(),null);
        if(aggregateFunctionsStack.empty()) {
            // processing just a column
            selectItem.addSelectItemComposite(siddhiColumn); // add to select statement

        }else{
            // if still processing on function attributes add to function attribute list
            aggregateFunctionsStack.peek().addAttribute(siddhiColumn); // add to function attribute list (still processing a function)
            SupportedDataTypes dataType = SupportedDataTypes.valueOf(getDataType().toUpperCase(Locale.ROOT));
            dataTypesOfAttributesOfFunction.add(dataType);
            siddhiApp.addColumnWithDataTypeToInputStreamDefinition(new ColumnWithDataType(siddhiColumn, dataType.getDataTypeSignature()));
        }
    }


    @Override
    public void handleFunctionExit(Function function) {
        aggregateFunction = aggregateFunctionsStack.pop();
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(aggregateFunction);
        }
        else{
            aggregateFunctionsStack.peek().addAttribute(aggregateFunction);
        }
    }

    @Override
    public void handleFunctionBegin(Function function) {
        isHandledFunction = true;
        dataTypesOfAttributesOfFunction = new ArrayList<>();
        aggregateFunction = new AggregateFunction(function.getName());
        aggregateFunctionsStack.push(aggregateFunction);
    }

    @Override
    public void handleSignedExpression(SignedExpression signedExpression) {
        Symbol siddhiSignedExpression = new Symbol(String.valueOf(signedExpression.getSign()));
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiSignedExpression);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiSignedExpression);
        }
    }

    @Override
    public void handleDoubleValue(DoubleValue doubleValue) {
        Symbol siddhiDoubleValue = new Symbol(String.valueOf(doubleValue.getValue()));
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiDoubleValue);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiDoubleValue);
        }
    }

    @Override
    public void handleLongValue(LongValue longValue) {
        Symbol siddhiLongValue = new Symbol(String.valueOf(longValue.getValue()));
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiLongValue);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiLongValue);
        }
    }

    @Override
    public void handleParenthesis(Parenthesis parenthesis) {

    }

    @Override
    public void handleStringValue(StringValue stringValue) {
        Symbol siddhiStringValue = new Symbol(stringValue.getValue());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiStringValue);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiStringValue);
        }
    }

    @Override
    public void handleAddition(Addition addition) {
        Symbol siddhiAddition = new Symbol(addition.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiAddition);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiAddition);
        }
    }

    @Override
    public void handleDivision(Division division) {
        Symbol siddhiDivision = new Symbol(division.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiDivision);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiDivision);
        }
    }

    @Override
    public void handleIntegerDivision(IntegerDivision integerDivision) {
        Symbol siddhiIntegerDivision = new Symbol(integerDivision.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiIntegerDivision);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiIntegerDivision);
        }
    }

    @Override
    public void handleMultiplication(Multiplication multiplication) {
        Symbol siddhiMultiplication = new Symbol(multiplication.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiMultiplication);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiMultiplication);
        }
    }

    @Override
    public void handleSubtraction(Subtraction subtraction) {
        Symbol siddhiSubtraction = new Symbol(subtraction.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiSubtraction);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiSubtraction);
        }
    }

    @Override
    public void handleAndExpression(AndExpression andExpression) {
        Symbol siddhiAndExpression = new Symbol(andExpression.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiAndExpression);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiAndExpression);
        }
    }

    @Override
    public void handleOrExpression(OrExpression orExpression) {
        Symbol siddhiOrExpression = new Symbol(orExpression.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiOrExpression);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiOrExpression);
        }
    }

    @Override
    public void handleXorExpression(XorExpression xorExpression) {
        Symbol siddhiXorExpression = new Symbol(xorExpression.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiXorExpression);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiXorExpression);
        }
    }

    @Override
    public void handleOpenBracket() {
        Symbol openBracket = new Symbol("(");
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(openBracket);
        }else{
            aggregateFunctionsStack.peek().addAttribute(openBracket);
        }
    }

    @Override
    public void handleCloseBracket() {
        Symbol closeBracket = new Symbol(")");
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(closeBracket);
        }else{
            aggregateFunctionsStack.peek().addAttribute(closeBracket);
        }
    }

    @Override
    public void handleEqualsTo(EqualsTo equalsTo) {
        Symbol siddhiEqualsTo = new Symbol(equalsTo.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiEqualsTo);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiEqualsTo);
        }
    }

    @Override
    public void handleGreaterThan(GreaterThan greaterThan) {
        Symbol siddhiGreaterThan = new Symbol(greaterThan.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiGreaterThan);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiGreaterThan);
        }
    }

    @Override
    public void handleGreaterThanEquals(GreaterThanEquals greaterThanEquals) {
        Symbol siddhiGreaterThanEquals = new Symbol(greaterThanEquals.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiGreaterThanEquals);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiGreaterThanEquals);
        }
    }

    @Override
    public void handleMinorThan(MinorThan minorThan) {
        Symbol siddhiMinorThan = new Symbol(minorThan.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiMinorThan);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiMinorThan);
        }
    }

    @Override
    public void handleMinorThanEquals(MinorThanEquals minorThanEquals) {
        Symbol siddhiMinorThanEquals = new Symbol(minorThanEquals.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiMinorThanEquals);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiMinorThanEquals);
        }
    }

    @Override
    public void handleNotEqualsTo(NotEqualsTo notEqualsTo) {
        Symbol siddhiNotEqualsTo = new Symbol(notEqualsTo.getStringExpression());
        if(aggregateFunctionsStack.empty()) {
            selectItem.addSelectItemComposite(siddhiNotEqualsTo);
        }else{
            aggregateFunctionsStack.peek().addAttribute(siddhiNotEqualsTo);
        }
    }

    @Override
    public void handleAlias(Alias alias) {
        // TODO : add feature to combine alias with the select item (eg - aggregateFunction has a alias)
        SiddhiAppComposites.Alias siddhiAlias = new SiddhiAppComposites.Alias(alias.getName());
        if(isHandledFunction){
            siddhiColumn = new SiddhiAppComposites.Column(siddhiAlias.getAlias(),null);
            // what is the dominating data type of the attributes in the function
            dataType = dataTypesOfAttributesOfFunction
                    .stream().distinct().max(Comparator.comparing(Enum::ordinal));
            // what types of data is able to return by the function
            if(dataType.isPresent()){
                if(SupportedAggregationFunctions
                        .valueOf(aggregateFunction.getFunctionName().toUpperCase(Locale.ROOT))
                        .getDefaultDataType()
                        .ordinal() >= dataType.get().ordinal()){
                    dataType = Optional.of(SupportedAggregationFunctions
                            .valueOf(aggregateFunction.getFunctionName().toUpperCase(Locale.ROOT))
                            .getDefaultDataType());
                }

            }
            selectItem.setSelectItemAlias(siddhiAlias); // {sum(columnName)}:SelectItem AS {aliasName}:SiddhiAppComposites.Alias
            // are data types of attributes and data types able to return by the function, equal?
                // if so use that data type as alias column data type
                // else throw an exception?
        }else {
            // add alias to column ( if this is not a function add alias to column name inside function )
            siddhiColumn.setAlias(siddhiAlias.getAlias());
        }

    }


    Optional<SupportedDataTypes> dataType;
    @Override
    public void addToSiddhiApp(String streamName) {
        // add column with data type for stream definition
        if(isHandledFunction && dataType.isPresent()){
            siddhiApp.addColumnWithDataTypeToOutputStreamDefinition(new ColumnWithDataType(siddhiColumn, dataType.get().getDataTypeSignature()));
        } else if(Objects.equals(streamName, siddhiApp.getTableName())) {
            // you are here means this handled just a column (not a column name inside function) as select item
            // this is done at last because aliases handled last. we need to add that to the siddhiColumn too.
            siddhiApp.addColumnWithDataTypeToInputStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
            if(siddhiColumn.getAlias() == null){

            }else{
                siddhiColumn = new SiddhiAppComposites.Column(siddhiColumn.getAlias(),null);
            }
            siddhiApp.addColumnWithDataTypeToOutputStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
        } else {
            siddhiApp.addColumnWithDataTypeToJoinStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
            if (siddhiColumn.getAlias() == null){

            }else{
                siddhiColumn = new SiddhiAppComposites.Column(siddhiColumn.getAlias(),null);
            }
            siddhiApp.addColumnWithDataTypeToOutputStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
        }
        siddhiApp.addSelectItem(selectItem); // add column with data type to select statement
    }

    public void addToSiddhiApp(Boolean joinStream) {
        if (joinStream) {
            siddhiApp.addColumnWithDataTypeToJoinStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
        } else {
            siddhiApp.addColumnWithDataTypeToInputStreamDefinition(new ColumnWithDataType(siddhiColumn, getDataType()));
        }

    }
}
