package SiddhiAppComposites.utilities.visitors.ColumnNameExtractor;

import SiddhiAppComposites.*;
import SiddhiAppComposites.utilities.visitors.IAttributeVisitor;

import java.util.HashSet;
import java.util.function.Consumer;

public class ColumnNameExtractorVisitor implements IAttributeVisitor {

    private final HashSet<String> columnNames = new HashSet<>();
    private final Consumer<ISiddhiAppComposite> iSiddhiAppCompositeConsumer = siddhiAppComposite -> {
                Class<? extends ISiddhiAppComposite> siddhiAppCompositeClass = siddhiAppComposite.getClass();
                if(siddhiAppCompositeClass.equals(SelectItem.class)){
                    this.visit((SelectItem) siddhiAppComposite);
                }else if(siddhiAppCompositeClass.equals(Column.class)){
                    this.visit((Column) siddhiAppComposite);
                }else if(siddhiAppCompositeClass.equals(ColumnWithDataType.class)){
                    this.visit((ColumnWithDataType) siddhiAppComposite);
                }else if(siddhiAppCompositeClass.equals(AggregateFunction.class)){
                    this.visit((AggregateFunction) siddhiAppComposite);
                }
            };

    @Override
    public void visit(AggregateFunction aggregateFunction) {
        aggregateFunction.getAttributeList().forEach(iSiddhiAppCompositeConsumer);
    }

    @Override
    public void visit(Column column) {
        columnNames.add(column.getName());
    }

    @Override
    public void visit(SelectItem selectItem) {
        selectItem.getSelectItemComposites().forEach(iSiddhiAppCompositeConsumer);
    }

    @Override
    public void visit(ColumnWithDataType columnWithDataType) {
        columnNames.add(columnWithDataType.getColumnName());
    }

    public void visit(ISiddhiAppComposite iSiddhiAppComposite) {
        iSiddhiAppCompositeConsumer.accept(iSiddhiAppComposite);
    }

    public HashSet<String> getColumnNames() {
        return columnNames;
    }
}
