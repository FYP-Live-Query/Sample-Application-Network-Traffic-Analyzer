package SiddhiAppComposites;

public class AttributeList implements ISiddhiAppComposite{

    @Override
    public String getSiddhiAppCompositeAsString() {
        return null;
    }

    public static class AttributeDataTypePair<T,N>{
        private T attribute;
        private N dataType;

        AttributeDataTypePair(T attribute, N dataType){
            this.attribute = attribute;
            this.dataType = dataType;
        }

        public void setAttribute(T attribute) {
            this.attribute = attribute;
        }

        public void setDataType(N dataType) {
            this.dataType = dataType;
        }

        public T getAttribute() {
            return attribute;
        }

        public N getDataType() {
            return dataType;
        }
    }

    private IAttributeList attributeListBehavior;

    public void setAttributeListBehavior(IAttributeList attributeListBehavior){
        this.attributeListBehavior = attributeListBehavior;
    }

    @Override
    public String toString() {
        return attributeListBehavior.toString();
    }
}
