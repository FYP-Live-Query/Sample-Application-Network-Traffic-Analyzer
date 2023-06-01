package SiddhiAppComposites;

import SiddhiAppComposites.Annotation.App.App;
import SiddhiAppComposites.Annotation.Attributes.IAttributes;
import SiddhiAppComposites.Annotation.Common.KeyValue;
import SiddhiAppComposites.Annotation.Info.IInfo;
import SiddhiAppComposites.Annotation.Map.IMap;
import SiddhiAppComposites.Annotation.Sink.ISink;
import SiddhiAppComposites.Annotation.Source.ISource;
import SiddhiAppComposites.Statement.FilterExpressionStatement.FilterExpression;
import SiddhiAppComposites.Statement.FilterExpressionStatement.IFilterExpression;
import SiddhiAppComposites.Statement.From.FromStatement;
import SiddhiAppComposites.Statement.From.IFromStatement;
import SiddhiAppComposites.Statement.Insert.IInsertStatement;
import SiddhiAppComposites.Statement.Insert.InsertStatement;
import SiddhiAppComposites.Statement.Select.SelectStatement;
import SiddhiAppComposites.utilities.visitors.ColumnNameExtractor.ColumnNameExtractorVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SiddhiApp {

    private final DefineStreamStatement defineInputStreamStatement = new DefineStreamStatement(); // create define input stream
    private final DefineStreamStatement defineOutputStreamStatement = new DefineStreamStatement(); // create define output stream

    private final DefineStreamStatement defineJoinStreamStatement = new DefineStreamStatement(); // create define input stream
    private final SelectStatement selectStatement = new SelectStatement(); // select statement
    private final IFromStatement fromStatement = new FromStatement(); // from statement

    private final IFromStatement joinStatement = new FromStatement(); // from statement

    private final IFilterExpression filterExpression = new FilterExpression(); // filter statement
    private final IInsertStatement insertStatement = new InsertStatement(); // insert into statement
    private final App annotationApp; // Annotation @app
    private final ISource annotationSource; // Annotation @source
    private final IAttributes annotationAttributes; // Annotation @attributes
    private final IMap annotationMap; // Annotation @map
    private final ISink annotationSink; // Annotation @sink
    private final IInfo annotationInfo; // Annotation @info

    private String inputOutputStreamNamePrefix = null; // this is the table name (full qualified or just table name)

    private String rightJoinTable = null;

    private ArrayList<String> onExpressions = new ArrayList<String>();
    private final StringBuilder stringSiddhiApp = new StringBuilder();

    private SiddhiApp(SiddhiAppBuilder siddhiAppBuilder) {

        this.annotationSource = siddhiAppBuilder.annotationSource;
        this.annotationAttributes = siddhiAppBuilder.annotationAttributes;
        this.annotationMap = siddhiAppBuilder.annotationMap;
        this.annotationSink = siddhiAppBuilder.annotationSink;
        this.annotationInfo = siddhiAppBuilder.annotationInfo;
        this.annotationApp = siddhiAppBuilder.annotationApp;
    }

    public void addSymbolToFilterExpression(String symbol){
        ((FilterExpression) this.filterExpression).addSymbol(symbol);
    }

    public void addSelectItem(ISiddhiAppComposite selectItem){
        selectStatement.addSelectItem(selectItem);
    }

    private void addToSourceAnnotationAttributes(ISiddhiAppComposite columnWithDataType){
        String columnName = ((ColumnWithDataType) columnWithDataType).getColumnName();
        annotationAttributes.addAttributeComposite(new KeyValue<>(columnName,columnName)); // add to attribute annotation
    }

    public void addColumnWithDataTypeToOutputStreamDefinition(ISiddhiAppComposite columnWithDataType){
        defineOutputStreamStatement.addAttributeWithDataType(columnWithDataType);
        addToSourceAnnotationAttributes(columnWithDataType);
    }

    public void addColumnWithDataTypeToInputStreamDefinition(ISiddhiAppComposite columnWithDataType){
        defineInputStreamStatement.addAttributeWithDataType(columnWithDataType);
        addToSourceAnnotationAttributes(columnWithDataType);
    }

    public void addColumnWithDataTypeToJoinStreamDefinition(ISiddhiAppComposite columnWithDataType){
        defineJoinStreamStatement.addAttributeWithDataType(columnWithDataType);
        addToSourceAnnotationAttributes(columnWithDataType);
    }

    public void setStreamNamePrefix(String inputOutputStreamNamePrefix) {
        this.inputOutputStreamNamePrefix = inputOutputStreamNamePrefix;
    }

    public void setRightJoin(String rightJoinTable) {
        this.rightJoinTable = rightJoinTable;
    }

    public void addOnExpressions(String column) {

        this.onExpressions.add(column);
    }
    public String getTableName() {
        return inputOutputStreamNamePrefix;
    }

    public String getRightJoinTable() { return rightJoinTable; }
    public HashSet<String> getColumnNames() {
        ColumnNameExtractorVisitor iAttributeVisitor = new ColumnNameExtractorVisitor();
        defineInputStreamStatement.extractColumnNames(iAttributeVisitor); // has all column names in select statement including where clause
        return iAttributeVisitor.getColumnNames();
    }

    public String getSiddhiAppStringRepresentation(){
        // Annotations
            // app name
        stringSiddhiApp.append(annotationApp.getSiddhiAppCompositeAsString());
            // source
         annotationMap.addMapComposite(annotationAttributes);
         if(rightJoinTable != null) {
             annotationSource.setTableName(inputOutputStreamNamePrefix);
         }
//        annotationSource.addSourceComposite(new KeyValue<>("table.name", inputOutputStreamNamePrefix));
        annotationSource.addSourceComposite(annotationMap);
         String sourceString = annotationSource.getSiddhiAppCompositeAsString();
        stringSiddhiApp.append(sourceString);
        defineInputStreamStatement.setStreamName(inputOutputStreamNamePrefix + "InputStream"); // IPStream set I/P Stream Name
        stringSiddhiApp.append(defineInputStreamStatement.getSiddhiAppCompositeAsString());
        if (rightJoinTable != null) {
//            annotationSource.addSourceComposite(annotationMap);
//            annotationSource.setTableName(rightJoinTable);
            String currentValue = "table.name = \"" + getTableName() + "\"";
            sourceString = sourceString.replaceAll(currentValue, String.format("table.name = \"%s\"", rightJoinTable));
            stringSiddhiApp.append(sourceString);

            defineJoinStreamStatement.setStreamName(rightJoinTable + "InputStream");
            stringSiddhiApp.append(defineJoinStreamStatement.getSiddhiAppCompositeAsString());
        }

        // Annotations
            // sink
        stringSiddhiApp.append(annotationSink.getSiddhiAppCompositeAsString());

        defineOutputStreamStatement.setStreamName(inputOutputStreamNamePrefix + "OutputStream"); // OPStream set O/P Stream Name
        stringSiddhiApp.append(defineOutputStreamStatement.getSiddhiAppCompositeAsString());
        // Annotations
            // info
        stringSiddhiApp.append(annotationInfo.getSiddhiAppCompositeAsString());

        if (rightJoinTable != null) {
            fromStatement.setStreamName(inputOutputStreamNamePrefix + "InputStream#window.length(3) as " + inputOutputStreamNamePrefix );
            fromStatement.setFromStatementComposite(filterExpression); // From statement filter expression

            joinStatement.setStreamName(rightJoinTable + "InputStream#window.length(3) as " + rightJoinTable );
            joinStatement.setFromStatementComposite(filterExpression);

            stringSiddhiApp.append(fromStatement.getSiddhiAppCompositeAsString());
            String joinInputStream = joinStatement.getSiddhiAppCompositeAsString().replaceAll("from", "join");
            stringSiddhiApp.append(joinInputStream);
            stringSiddhiApp.append("on ").append(onExpressions.get(0)).append(" == ").append(onExpressions.get(1)).append("\n");


            List<ISiddhiAppComposite> selectFields = ((SelectStatementAttributeList) selectStatement.getSelectItems())
                    .getAttributesWithOrWithoutAliases();
            ArrayList<String> fieldsInColumns = new ArrayList<>();

            for (int j = 0; j < selectFields.size(); j++) {
                String select = selectFields.get(j)
                        .getSiddhiAppCompositeAsString().replaceAll("\\s+", "");

                List<ISiddhiAppComposite> columnsInInputStream = ((StreamStatementAttributeList) defineInputStreamStatement
                        .getAttributes()).getAttributeListWithoutAliasesWithDataType();
                for (int i = 0; i < columnsInInputStream.size(); i++) {
                    String column = ((ColumnWithDataType) columnsInInputStream.get(i)).getColumnName();
                    if (select.equals(column)) {
                        fieldsInColumns.add(inputOutputStreamNamePrefix + "." + select);
                    }
                }

                List<ISiddhiAppComposite> columnsInJoinStream = ((StreamStatementAttributeList) defineJoinStreamStatement
                        .getAttributes()).getAttributeListWithoutAliasesWithDataType();
                for (int i = 0; i < columnsInJoinStream.size(); i++) {
                    String column = ((ColumnWithDataType) columnsInJoinStream.get(i)).getColumnName();
                    if (select.equals(column)) {
                        fieldsInColumns.add(rightJoinTable + "." + select);
                    }
                }
            }
            StringBuilder allFields = new StringBuilder("select ");

            for (String str : fieldsInColumns) {
                allFields.append(str).append(", ");
            }
            allFields.delete(allFields.length() - 2, allFields.length());
            allFields.append("\n");
            stringSiddhiApp.append(allFields);

        } else {
            fromStatement.setStreamName(inputOutputStreamNamePrefix + "InputStream"); // From Statement set I/P Stream Name
            fromStatement.setFromStatementComposite(filterExpression); // From statement filter expression
            stringSiddhiApp.append(fromStatement.getSiddhiAppCompositeAsString());
            stringSiddhiApp.append(selectStatement.getSiddhiAppCompositeAsString());
        }




        insertStatement.setOutputStreamName(inputOutputStreamNamePrefix + "OutputStream"); // insert statement set O/P Stream name
        stringSiddhiApp.append(insertStatement.getSiddhiAppCompositeAsString());
        return stringSiddhiApp.toString();
    }

    // Bloch’s Builder pattern
    public static class SiddhiAppBuilder{
        private ISource annotationSource;
        private IAttributes annotationAttributes;
        private IMap annotationMap;
        private ISink annotationSink;
        private IInfo annotationInfo;
        private final App annotationApp;

        public SiddhiAppBuilder(String siddhiAppName) {
            annotationApp = new App();
            annotationApp.setSiddhiApplicationName(siddhiAppName);
        }

        public SiddhiAppBuilder setAnnotationSource(ISource annotationSource) {
            this.annotationSource = annotationSource;
            return this;
        }

        public SiddhiAppBuilder setAnnotationAttributes(IAttributes annotationAttributes) {
            this.annotationAttributes = annotationAttributes;
            return this;
        }

        public SiddhiAppBuilder setAnnotationMap(IMap annotationMap) {
            this.annotationMap = annotationMap;
            return this;
        }

        public SiddhiAppBuilder setAnnotationSink(ISink annotationSink) {
            this.annotationSink = annotationSink;
            return this;
        }

        public SiddhiAppBuilder setAnnotationInfo(IInfo annotationInfo) {
            this.annotationInfo = annotationInfo;
            return this;
        }

        public SiddhiApp build(){
            return new SiddhiApp(this);
        }

    }
}