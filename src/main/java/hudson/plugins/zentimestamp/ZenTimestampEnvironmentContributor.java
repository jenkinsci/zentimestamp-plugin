package hudson.plugins.zentimestamp;

import hudson.EnvVars;
import hudson.Extension;
import hudson.matrix.MatrixRun;
import hudson.model.*;
import hudson.slaves.NodeProperty;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Gregory Boissinot
 */
@Extension
public class ZenTimestampEnvironmentContributor extends EnvironmentContributor {

    @Override
    public void buildEnvironmentFor(Run run, EnvVars envs, TaskListener listener) throws IOException, InterruptedException {

        String pattern = null;

        //Get pattern by global node properties
        for (NodeProperty<?> nodeProperty : Hudson.getInstance().getGlobalNodeProperties()) {
            if (nodeProperty instanceof ZenTimestampNodeProperty) {
                ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                pattern = envInjectNodeProperty.getPattern();
            }
        }

        if (run instanceof AbstractBuild) {
            AbstractBuild build = (AbstractBuild) run;

            //Get local node pattern and override it if any
            Node node = build.getBuiltOn();

            //Check if the node is already up
            // --> Build can be a previous build (case of polling) and the node can be no more exists
            if (node != null) {
                for (NodeProperty<?> nodeProperty : node.getNodeProperties()) {
                    if (nodeProperty instanceof ZenTimestampNodeProperty) {
                        ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                        pattern = envInjectNodeProperty.getPattern();
                    }
                }
            }
        }

        //Get job pattern and override it if any
        Job job = getJob(run);
        ZenTimestampJobProperty zenTimestampJobProperty = (ZenTimestampJobProperty) job.getProperty(ZenTimestampJobProperty.class);
        if (zenTimestampJobProperty != null) {
            pattern = zenTimestampJobProperty.getPattern();
        }

        //Process pattern
        if (pattern != null) {
            Calendar buildTimestamp = run.getTimestamp();
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            final String formattedBuildTimestampValue = sdf.format(buildTimestamp.getTime());
            envs.put(ZenTimestampAction.BUILD_TIMESTAMP_VARIABLE, formattedBuildTimestampValue);
        }
    }

    private Job getJob(Run run) {
        if (run instanceof MatrixRun) {
            return ((MatrixRun) run).getParentBuild().getProject();
        } else {
            return run.getParent();
        }
    }
}
