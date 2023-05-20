package live.source.Thread;


import live.source.Thread.AbstractThread;
import live.source.Thread.ThreadState;

public class StoppedThreadState extends ThreadState {
    public StoppedThreadState(AbstractThread streamThread) {
        super(streamThread);
    }

    @Override
    public void stop() {
        System.out.println("Thread has already stopped."); // TODO :  throw an exception
    }

    @Override
    public void pause() {
        System.out.println("Thread has already stopped."); // TODO :  throw an exception
    }

    @Override
    public void resume() {
        System.out.println("Thread has stopped."); // TODO :  throw an exception
    }
}
