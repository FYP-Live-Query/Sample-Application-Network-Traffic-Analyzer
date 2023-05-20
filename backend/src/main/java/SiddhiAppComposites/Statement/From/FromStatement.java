package SiddhiAppComposites.Statement.From;

import SiddhiAppComposites.ISiddhiAppComposite;

import java.util.ArrayList;
import java.util.List;

public class FromStatement implements IFromStatement {
    private String inputStreamOrNamedWindow;
    private List<ISiddhiAppComposite> fromStatementComposite; // filter statement,window,stream function,join, join with window...

    public FromStatement(){
        inputStreamOrNamedWindow = null;
        fromStatementComposite = new ArrayList<>(10);
    }

    public void setStreamName(String inputStreamOrNamedWindow){
        this.inputStreamOrNamedWindow = inputStreamOrNamedWindow;
    }

    public void setFromStatementComposite(ISiddhiAppComposite filterStatement) {
        fromStatementComposite.add(filterStatement);
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        String fromWithStreamName = "from " + inputStreamOrNamedWindow;
        if(fromStatementComposite == null) {
            return fromWithStreamName + "\n";
        }
        else{
            return fromWithStreamName +
                    fromStatementComposite.stream().iterator().next().getSiddhiAppCompositeAsString() + "\n";
        }
    }
}
