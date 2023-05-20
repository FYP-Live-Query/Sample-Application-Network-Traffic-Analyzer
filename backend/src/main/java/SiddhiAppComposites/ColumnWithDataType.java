package SiddhiAppComposites;

import SiddhiAppComposites.utilities.visitors.IAttributeVisitor;

import java.util.Objects;

public class ColumnWithDataType implements IAttribute{

    private String dataType;
    private Column column;

    public ColumnWithDataType(Column column, String dataType) {
        this.column = column;
        if(SupportedDataTypes.isDataTypeSupported(dataType)) {
            this.dataType = dataType;
        }
        else{
            throw new TypeNotPresentException(dataType,new Throwable("data type not supported by siddhiQL : " + "[" + dataType + "]"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnWithDataType)) return false;
        ColumnWithDataType that = (ColumnWithDataType) o;
        return Objects.equals(dataType, that.dataType) && Objects.equals(column.getName(), that.column.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataType, column);
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        return column.getName() + " " + dataType;
    }

    public boolean isSameColumnAndDifferentDataType(Object o) {
        if (this == o || !(o instanceof ColumnWithDataType)) return false;
        ColumnWithDataType that = (ColumnWithDataType) o;
        return Objects.equals(column.getName(), that.column.getName()) && !Objects.equals(dataType, that.dataType);
    }

    public void setDataType(String dataType){
        this.dataType = dataType;
    }

    public String getAlias(){
        return column.getAlias();
    }

    public String getColumnName(){
        return this.column.getName();
    }

    public String getDataType(){
        return this.dataType;
    }

    public void setColumn(Column column){
        this.column = column;
    }

    @Override
    public void accept(IAttributeVisitor iAttributeVisitor) {
        iAttributeVisitor.visit(this);
    }
}
