package SiddhiAppComposites;

public class Alias implements ISiddhiAppComposite{
    private String alias;

    public Alias(String alias){
        this.alias = alias;
    }

    public Alias(){
        this.alias = null;
    }

    public String getAlias(){
        return this.alias;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        return " as " + alias + " ";
    }
}
