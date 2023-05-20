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

import java.util.HashSet;

public class SiddhiApp {

    private final DefineStreamStatement defineInputStreamStatement = new DefineStreamStatement(); // create define input stream
    private final DefineStreamStatement defineOutputStreamStatement = new DefineStreamStatement(); // create define output stream
    private final SelectStatement selectStatement = new SelectStatement(); // select statement
    private final IFromStatement fromStatement = new FromStatement(); // from statement
    private final IFilterExpression filterExpression = new FilterExpression(); // filter statement
    private final IInsertStatement insertStatement = new InsertStatement(); // insert into statement
    private final App annotationApp; // Annotation @app
    private final ISource annotationSource; // Annotation @source
    private final IAttributes annotationAttributes; // Annotation @attributes
    private final IMap annotationMap; // Annotation @map
    private final ISink annotationSink; // Annotation @sink
    private final IInfo annotationInfo; // Annotation @info

    private String inputOutputStreamNamePrefix = null; // this is the table name (full qualified or just table name)
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

    public void setStreamNamePrefix(String inputOutputStreamNamePrefix) {
        this.inputOutputStreamNamePrefix = inputOutputStreamNamePrefix;
    }

    public String getTableName() {
        return inputOutputStreamNamePrefix;
    }
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
        annotationSource.addSourceComposite(annotationMap);
        stringSiddhiApp.append(annotationSource.getSiddhiAppCompositeAsString());
        defineInputStreamStatement.setStreamName(inputOutputStreamNamePrefix + "InputStream"); // IPStream set I/P Stream Name
        stringSiddhiApp.append(defineInputStreamStatement.getSiddhiAppCompositeAsString());

        // Annotations
            // sink
        stringSiddhiApp.append(annotationSink.getSiddhiAppCompositeAsString());

        defineOutputStreamStatement.setStreamName(inputOutputStreamNamePrefix + "OutputStream"); // OPStream set O/P Stream Name
        stringSiddhiApp.append(defineOutputStreamStatement.getSiddhiAppCompositeAsString());
        // Annotations
            // info
        stringSiddhiApp.append(annotationInfo.getSiddhiAppCompositeAsString());

        fromStatement.setStreamName(inputOutputStreamNamePrefix + "InputStream"); // From Statement set I/P Stream Name
        fromStatement.setFromStatementComposite(filterExpression); // From statement filter expression
        stringSiddhiApp.append(fromStatement.getSiddhiAppCompositeAsString());

        stringSiddhiApp.append(selectStatement.getSiddhiAppCompositeAsString()); // select statement

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