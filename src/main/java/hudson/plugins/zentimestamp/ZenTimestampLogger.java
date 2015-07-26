package hudson.plugins.zentimestamp;

import hudson.model.BuildListener;

import java.io.Serializable;


public class ZenTimestampLogger implements Serializable {

    public static void log(BuildListener listener, final String message) {
        listener.getLogger().println("[zentimestamp] " + message);
    }

}
