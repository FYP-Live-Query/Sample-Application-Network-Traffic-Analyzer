package Compiler;

import Engine.IEngine;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;

public class CustomSelectStatementVisitor implements StatementVisitor {
    private IEngine middleEngine;

    public CustomSelectStatementVisitor(IEngine middleEngine) {
        this.middleEngine = middleEngine;
    }


    @Override
    public void visit(Analyze analyze) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(SavepointStatement savepointStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(RollbackStatement rollbackStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Comment comment) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Commit commit) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Delete delete) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Update update) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Insert insert) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Replace replace) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Drop drop) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Truncate truncate) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateIndex createIndex) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateSchema createSchema) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateTable createTable) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateView createView) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(AlterView alterView) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Alter alter) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Statements statements) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Execute execute) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(SetStatement setStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ResetStatement resetStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ShowColumnsStatement showColumnsStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ShowTablesStatement showTablesStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Merge merge) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Select select) {
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(new CustomSelectBodyVisitor(middleEngine));
    }

    @Override
    public void visit(Upsert upsert) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(UseStatement useStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Block block) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ValuesStatement valuesStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(DescribeStatement describeStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ExplainStatement explainStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(ShowStatement showStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(DeclareStatement declareStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(Grant grant) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateSequence createSequence) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(AlterSequence alterSequence) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateFunctionalStatement createFunctionalStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(CreateSynonym createSynonym) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(AlterSession alterSession) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(IfElseStatement ifElseStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(RenameTableStatement renameTableStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(PurgeStatement purgeStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(AlterSystemStatement alterSystemStatement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void visit(UnsupportedStatement unsupportedStatement) {

    }
}
