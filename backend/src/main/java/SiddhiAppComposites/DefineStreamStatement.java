package SiddhiAppComposites;


import SiddhiAppComposites.utilities.visitors.IAttributeVisitor;


public class DefineStreamStatement implements IStream{
    private String streamName;
    private final IAttributeList attributeListWithoutAliasesWithDataType;

    public DefineStreamStatement(String streamName) {
        this.streamName = streamName;
        this.attributeListWithoutAliasesWithDataType = new StreamStatementAttributeList();
    }

    public DefineStreamStatement(){
        streamName = null;
        this.attributeListWithoutAliasesWithDataType = new StreamStatementAttributeList();
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public void addAttributeWithDataType(ISiddhiAppComposite attributeWithDatatype) {
        this.attributeListWithoutAliasesWithDataType.addAttribute(attributeWithDatatype);
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        if(streamName == null) { throw new NullPointerException("Stream name should provided."); }
        return "define stream " + streamName + "(" + attributeListWithoutAliasesWithDataType.getSiddhiAppCompositeAsString() + ");\n";
    }

    public void extractColumnNames(IAttributeVisitor visitor) {
        ((StreamStatementAttributeList) attributeListWithoutAliasesWithDataType)
                .getAttributeListWithoutAliasesWithDataType()
                .forEach(visitor::visit);
    }
}