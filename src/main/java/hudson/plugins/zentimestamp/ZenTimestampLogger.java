package hudson.plugins.zentimestamp;

import hudson.model.BuildListener;

import java.io.Serializable;


public class ZenTimestampLogger implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void log(BuildListener listener, final String message) {
        listener.getLogger().println("[zentimestamp] " + message);
    }

}
