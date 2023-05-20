package SiddhiAppComposites;

public class Symbol implements ISiddhiAppComposite{
    // symbol - % ,/, -, +, (, ), =, ==, etc...
    private String symbol;

    public Symbol(String symbol){
        if(Symbols.isSymbolSupported(symbol)){
            this.symbol = symbol;
        }else{
            this.symbol = null;
            throw new UnsupportedOperationException(String.format("%s symbol not supported by SQLtoSiddhiQL",symbol));
        }
    }

    public enum Symbols{
        ADDITION("+"),
        SUBTRACTION("-"),
        DIVISION("/"),
        MULTIPLICATION("*"),
        AND("AND"),
        OR("OR"),
        XOR("XOR"),
        GREATER_THAN(">"),
        MINOR_THAN("<"),
        GREATER_THAN_OR_EQUALS(">="),
        MINOR_THAN_OR_EQUALS("<="),
        EQUAL("="),
        NOT_EQUALS("+");

        private String symbol;

        Symbols(String symbol){
            this.symbol = symbol;
        }

        public static boolean isSymbolSupported(String symbolSignature){
            for(Symbols symbol : Symbols.values()){
                if(symbol.getSignature().equals(symbolSignature)){
                    return true;
                }
            }
            return false;
        }

        public String getSignature(){
            return symbol;
        }
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        return " " + symbol + " ";
    }
}
