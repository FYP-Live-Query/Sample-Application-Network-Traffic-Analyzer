package SiddhiAppComposites;

import Compiler.CustomSelectStatementVisitor;
import Engine.IEngine;
import Engine.MiddleEngine;
import SiddhiAppComposites.Annotation.Attributes.IAttributes;
import SiddhiAppComposites.Annotation.Attributes.JsonMapAttributes;
import SiddhiAppComposites.Annotation.Common.KeyValue;
import SiddhiAppComposites.Annotation.Info.IInfo;
import SiddhiAppComposites.Annotation.Info.QueryInfo;
import SiddhiAppComposites.Annotation.Map.IMap;
import SiddhiAppComposites.Annotation.Map.JsonMap;
import SiddhiAppComposites.Annotation.Sink.ISink;
import SiddhiAppComposites.Annotation.Sink.LogSink;
import SiddhiAppComposites.Annotation.Source.ISource;
import SiddhiAppComposites.Annotation.Source.LiveSource;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.Node;
import net.sf.jsqlparser.statement.Statement;

public class SiddhiAppGenerator {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String[] colors = {ANSI_RESET, ANSI_BLACK ,ANSI_RED , ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE,
            ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE
    };

    public static Node parseAST(String SQLStatement) throws JSQLParserException {
        return CCJSqlParserUtil.parseAST(SQLStatement);
    }

    public static void traverseAST(Node head, int level, String statement) throws JSQLParserException {
        traverseAST(head,level,CCJSqlParserUtil.parse(statement));
    }

    private static void traverseAST(Node rootNode, int level, Statement statement){
        if(rootNode == null){
            return;
        }
        printNode(rootNode,level);
        for(int i = 0; i < rootNode.jjtGetNumChildren(); i++){
            traverseAST(rootNode.jjtGetChild(i),level+1, statement);
        }
    }

    private static void printNode(Node head, int level){
        System.out.print("|");
        for( int s = 0 ; s < level ; s++){
            System.out.print("--");
        }
        System.out.println(colors[level % 9] + head + colors[0]);
    }

    public static SiddhiApp generateSiddhiApp(String siddhiAppName,String StringSQLSelectStatement,
                                              ISource sourceAnnotation, IMap mapAnnotation, IAttributes attributesAnnotation, ISink sinkAnnotation, IInfo infoAnnotation){
        SiddhiApp siddhiApp = new SiddhiApp.SiddhiAppBuilder(siddhiAppName)
                .setAnnotationSource(sourceAnnotation
                        .addSourceComposite(new KeyValue<>("sql.query", SanitizeSQL(StringSQLSelectStatement))))
                .setAnnotationMap(mapAnnotation)
                .setAnnotationAttributes(attributesAnnotation)
                .setAnnotationSink(sinkAnnotation)
                .setAnnotationInfo(infoAnnotation).build();

        IEngine middleEngine  = new MiddleEngine().setSiddhiApp(siddhiApp);
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(StringSQLSelectStatement);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        assert statement != null;
        statement.accept(new CustomSelectStatementVisitor(middleEngine));

        return siddhiApp;
    }


    public static SiddhiApp generateSiddhiApp(String StringSQLSelectStatement){
        SiddhiApp siddhiApp = generateSiddhiApp("SiddhiAppName-dev",
                                                    StringSQLSelectStatement,
                                                    new LiveSource(),
                                                    new JsonMap(),
                                                    new JsonMapAttributes(),
                                                    new LogSink(),
                                                    new QueryInfo());
        return siddhiApp;
    }


    private static String SanitizeSQL(String SQL){
        // this is naive. do add better algo
        SQL = SQL.replace("@int","")
                .replace("@bool","")
                .replace("@double","")
                .replace("@float","")
                .replace("@string","")
                .replace("@long","");
        return SQL;
    }

}
