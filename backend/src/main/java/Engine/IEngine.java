package Engine;

import SiddhiAppComposites.SiddhiApp;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SubJoin;

public abstract class IEngine {
    protected IEngineExpressionHandleBehavior engineBehavior;
    protected SiddhiApp siddhiApp;

    public IEngine(){
    }

    public void setExpressionHandlingBehavior(IEngineExpressionHandleBehavior engineBehavior){
        engineBehavior.setSiddhiApp(this.siddhiApp);
        this.engineBehavior = engineBehavior; }

    public IEngine setSiddhiApp(SiddhiApp siddhiApp) {
        this.siddhiApp = siddhiApp;
        return this;
    }

    public abstract void handleTable(Table table);

    public abstract void handleJoin(SubJoin subJoin);

    public abstract void handleColumn(Column columnName);

    public abstract void handleFunctionExit(Function function);

    public abstract void handleFunctionBegin(Function function);

    public abstract void handleSignedExpression(SignedExpression signedExpression);

    public abstract void handleDoubleValue(DoubleValue doubleValue);

    public abstract void handleLongValue(LongValue longValue);

    public abstract void handleParenthesis(Parenthesis parenthesis);

    public abstract void handleStringValue(StringValue stringValue);

    public abstract void handleAddition(Addition addition);

    public abstract void handleDivision(Division division);

    public abstract void handleIntegerDivision(IntegerDivision integerDivision);

    public abstract void handleMultiplication(Multiplication multiplication);

    public abstract void handleSubtraction(Subtraction subtraction);

    public abstract void handleAndExpression(AndExpression andExpression);

    public abstract void handleOrExpression(OrExpression orExpression);

    public abstract void handleXorExpression(XorExpression xorExpression);

    // TODO : temp methods for handling brackets until create bin tree
    public abstract void handleOpenBracket();
    // TODO : temp methods for handling brackets until create bin tree
    public abstract void handleCloseBracket();

    public abstract void handleEqualsTo(EqualsTo equalsTo);

    public abstract void handleGreaterThan(GreaterThan greaterThan);

    public abstract void handleGreaterThanEquals(GreaterThanEquals greaterThanEquals);

    public abstract void handleMinorThan(MinorThan minorThan);

    public abstract void handleMinorThanEquals(MinorThanEquals minorThanEquals);

    public abstract void handleNotEqualsTo(NotEqualsTo notEqualsTo);

    public abstract void handleAlias(Alias alias);

    public abstract void addToSiddhiApp(String streamName);
}
