package SiddhiAppComposites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamStatementAttributeList implements IAttributeList{

    private final List<ISiddhiAppComposite> attributeListWithoutAliasesWithDataType;

    public StreamStatementAttributeList() {
        this.attributeListWithoutAliasesWithDataType = new ArrayList<>(10);
    }

    public List<ISiddhiAppComposite> getAttributeListWithoutAliasesWithDataType() {
        return attributeListWithoutAliasesWithDataType;
    }

    public void addAttribute(ISiddhiAppComposite attributeWithDataType){
        // TODO : create a hashmap and avoid O(n) comparisons
        for(int indexOfAttributeWithDataType = 0; indexOfAttributeWithDataType < attributeListWithoutAliasesWithDataType.size() ; indexOfAttributeWithDataType++){
            ColumnWithDataType PersistedAttributeWIthDataType = (ColumnWithDataType) attributeListWithoutAliasesWithDataType.get(indexOfAttributeWithDataType);

            if(PersistedAttributeWIthDataType.equals(attributeWithDataType)){ // equals data type and column name ?
                return;
            }else if(PersistedAttributeWIthDataType.isSameColumnAndDifferentDataType(attributeWithDataType)){ // column and diff data type conflict resolving
                System.out.println("data type conflict on column " + PersistedAttributeWIthDataType.getColumnName());
                // TODO : existing data type overwriting strategy should re-evaluate

                PersistedAttributeWIthDataType.setDataType(
                        ((ColumnWithDataType) attributeWithDataType).getDataType()
                ); //  replaces data type with most recent data type given

                attributeListWithoutAliasesWithDataType.set(indexOfAttributeWithDataType,PersistedAttributeWIthDataType); // replacing
                return;
            }

        }
        this.attributeListWithoutAliasesWithDataType
                .add(attributeWithDataType);
    }

    @Override
    public String getSiddhiAppCompositeAsString() {

        StringBuilder attributeSetWithoutAliasesWithDataType = new StringBuilder(""); // eg. "" -->
        Iterator<ISiddhiAppComposite> attributeListWithoutAliasesWithDataTypeIterator = this.attributeListWithoutAliasesWithDataType.iterator();

        boolean thereIsNextComponent = attributeListWithoutAliasesWithDataTypeIterator.hasNext();

        while(thereIsNextComponent){

            ISiddhiAppComposite selectItemComposite = attributeListWithoutAliasesWithDataTypeIterator.next();
            thereIsNextComponent = attributeListWithoutAliasesWithDataTypeIterator.hasNext();

            if(thereIsNextComponent){
                attributeSetWithoutAliasesWithDataType
                        .append(
                                selectItemComposite
                                        .getSiddhiAppCompositeAsString()
                        )
                        .append(","); // --> "col String, col String,"
            }else{
                attributeSetWithoutAliasesWithDataType
                        .append(
                                selectItemComposite.
                                        getSiddhiAppCompositeAsString()
                        ); // --> "col String, col String, col double"
            }
        }

        return attributeSetWithoutAliasesWithDataType
                .toString(); // --> "col String, col String, col double"
    }
}
