package SiddhiAppComposites.Statement.Insert;

import SiddhiAppComposites.ISiddhiAppComposite;
public interface IInsertStatement extends ISiddhiAppComposite {
    void setOutputStreamName(String outputStreamName);

    void setStreamName(String inputStreamName);
}