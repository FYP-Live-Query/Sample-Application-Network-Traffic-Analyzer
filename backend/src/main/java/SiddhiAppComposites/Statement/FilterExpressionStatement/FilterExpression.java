package SiddhiAppComposites.Statement.FilterExpressionStatement;

public class FilterExpression implements IFilterExpression {

    private final StringBuilder filterStatement = new StringBuilder("");
    // TODO : this is a quick implementation. Do create a bin tree
    public void addSymbol(String symbol){
        this.filterStatement.append(symbol).append(" ");
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        if(filterStatement.toString().equals("")){
            return filterStatement.toString();
        }
        return "[" + filterStatement.toString() + "]";
    }
}