package SiddhiAppComposites;

import SiddhiAppComposites.utilities.visitors.IAttributeVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectItem implements IAttribute{
    private final List<ISiddhiAppComposite> selectItemComposites;
    private final Alias selectItemAlias;

    public SelectItem(){
        selectItemComposites = new ArrayList<>();
        selectItemAlias = new Alias();
    }

    public List<ISiddhiAppComposite> getSelectItemComposites() {
        return selectItemComposites;
    }

    public void setSelectItemAlias(ISiddhiAppComposite selectItemAlias){
        this.selectItemAlias.setAlias(selectItemAlias.getSiddhiAppCompositeAsString());
    }

    public void addSelectItemComposite(ISiddhiAppComposite selectItemComposite){
        selectItemComposites.add(selectItemComposite);
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        Iterator<ISiddhiAppComposite> selectItemIterator = selectItemComposites.iterator();
        StringBuilder selectItem = new StringBuilder("");
        boolean thereIsNextComponent = selectItemIterator.hasNext();

        while(thereIsNextComponent){
            ISiddhiAppComposite selectItemComposite = selectItemIterator.next();
            thereIsNextComponent = selectItemIterator.hasNext();

            if(selectItemAlias.getAlias() == null) {
                selectItem
                        .append(selectItemComposite.getSiddhiAppCompositeAsString())
                        .append(" ");
            }else{
                selectItem
                        .append(selectItemComposite.getSiddhiAppCompositeAsString())
                        .append(" ")
                        .append(selectItemAlias.getAlias());
            }
        }
        return selectItem.toString();
    }

    @Override
    public void accept(IAttributeVisitor iAttributeVisitor) {
        iAttributeVisitor.visit(this);
    }
}
