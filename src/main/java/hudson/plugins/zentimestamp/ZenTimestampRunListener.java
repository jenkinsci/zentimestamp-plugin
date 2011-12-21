package hudson.plugins.zentimestamp;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import hudson.slaves.NodeProperty;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
@Extension
public class ZenTimestampRunListener extends RunListener<Run> implements Serializable {

    @Override
    public Environment setUpEnvironment(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {

        String pattern = null;

        //Global Node Properties
        for (NodeProperty<?> nodeProperty : Hudson.getInstance().getGlobalNodeProperties()) {
            if (nodeProperty instanceof ZenTimestampNodeProperty) {
                ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                pattern = envInjectNodeProperty.getPattern();
            }
        }

        //Node
        Node node = build.getBuiltOn();
        for (NodeProperty<?> nodeProperty : node.getNodeProperties()) {
            if (nodeProperty instanceof ZenTimestampNodeProperty) {
                ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                pattern = envInjectNodeProperty.getPattern();
            }
        }

        //Override job pattern if any
        if (isZenTimestampJobProperty(build.getParent())) {
            pattern = getZenTimestampJobProperty(build.getProject()).getPattern();
        }

        if (pattern == null) {
            return super.setUpEnvironment(build, launcher, listener);
        }

        final PrintStream logger = listener.getLogger();
        Calendar buildTimestamp = build.getTimestamp();
        logger.println("Formatting the BUILD_ID variable with'" + pattern + "' pattern.");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        final String formattedBuildValue = sdf.format(buildTimestamp.getTime());

        return new Environment() {
            @Override
            public void buildEnvVars(Map<String, String> env) {
                env.put("BUILD_ID", formattedBuildValue);
            }
        };
    }

    private boolean isZenTimestampJobProperty(Job job) {
        ZenTimestampJobProperty zenTimestampJobProperty = (ZenTimestampJobProperty) job.getProperty(ZenTimestampJobProperty.class);
        if (zenTimestampJobProperty != null) {
            return zenTimestampJobProperty.isChangeBUILDID();
        }
        return false;
    }

    private ZenTimestampJobProperty getZenTimestampJobProperty(Job project) {
        return (ZenTimestampJobProperty) project.getProperty(ZenTimestampJobProperty.class);
    }

}
