package live.utils;

public class Monitor {
    public boolean signalled;

    public Monitor() {
        this.signalled = false;
    }

    public boolean isSignalled() {
        return signalled;
    }

    public void setSignalled(boolean signalled) {
        this.signalled = signalled;
    }
}
