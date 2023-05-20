package SiddhiAppComposites;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class SelectStatementAttributeList implements IAttributeList{

    private final List<ISiddhiAppComposite> attributesWithOrWithoutAliasesList;

    public SelectStatementAttributeList() {
        this.attributesWithOrWithoutAliasesList = new ArrayList<>(10);
    }

    public List<ISiddhiAppComposite> getAttributesWithOrWithoutAliases() {
        return attributesWithOrWithoutAliasesList;
    }

    public void addAttribute(ISiddhiAppComposite attribute){
        attributesWithOrWithoutAliasesList.add(attribute);
    }

    @Override
    public String toString() {
        StringBuilder attributeSetWithAliasesWithOutDataType = new StringBuilder(); // eg. - SUM( -->
        Iterator<ISiddhiAppComposite> attributesWithOrWithoutAliasesIterator = attributesWithOrWithoutAliasesList.iterator();

        boolean thereIsNextComponent = attributesWithOrWithoutAliasesIterator.hasNext();

        while(thereIsNextComponent){
            ISiddhiAppComposite selectItemComposite = attributesWithOrWithoutAliasesIterator.next();
            thereIsNextComponent = attributesWithOrWithoutAliasesIterator.hasNext();

            if(thereIsNextComponent){
                attributeSetWithAliasesWithOutDataType
                        .append(
                                selectItemComposite
                                        .getSiddhiAppCompositeAsString()
                        )
                        .append(","); // --> col ,
            }else{
                attributeSetWithAliasesWithOutDataType
                        .append(
                                selectItemComposite.
                                        getSiddhiAppCompositeAsString()
                        ); // --> col , SUM(col + col) AS alias -->
            }
        }

        return attributeSetWithAliasesWithOutDataType
                .toString(); // col , SUM(col + col) AS alias
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        return toString();
    }
}
