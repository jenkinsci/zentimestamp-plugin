package hudson.plugins.zentimestamp;

import hudson.EnvVars;
import hudson.Extension;
import hudson.matrix.MatrixRun;
import hudson.model.*;
import hudson.slaves.NodeProperty;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Gregory Boissinot
 */
@Extension
public class ZenTimestampEnvironmentContributor extends EnvironmentContributor {

    @Override
    public void buildEnvironmentFor(Run r, EnvVars envs, TaskListener listener) throws IOException, InterruptedException {

        AbstractBuild build = (AbstractBuild) r;

        String pattern = null;

        //Get pattern by global node properties
        for (NodeProperty<?> nodeProperty : Hudson.getInstance().getGlobalNodeProperties()) {
            if (nodeProperty instanceof ZenTimestampNodeProperty) {
                ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                pattern = envInjectNodeProperty.getPattern();
            }
        }

        //Get local node pattern and override it if any
        Node node = build.getBuiltOn();
        for (NodeProperty<?> nodeProperty : node.getNodeProperties()) {
            if (nodeProperty instanceof ZenTimestampNodeProperty) {
                ZenTimestampNodeProperty envInjectNodeProperty = (ZenTimestampNodeProperty) nodeProperty;
                pattern = envInjectNodeProperty.getPattern();
            }
        }

        //Get job pattern and override it if any
        Job job = getJob(build);
        ZenTimestampJobProperty zenTimestampJobProperty = (ZenTimestampJobProperty) job.getProperty(ZenTimestampJobProperty.class);
        if (zenTimestampJobProperty != null) {
            pattern = zenTimestampJobProperty.getPattern();
        }

        //Process pattern
        if (pattern != null) {
            final PrintStream logger = listener.getLogger();
            Calendar buildTimestamp = build.getTimestamp();
            logger.println(String.format("Changing BUILD_ID variable (job build time) with the date pattern %s.", pattern));
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            final String formattedBuildValue = sdf.format(buildTimestamp.getTime());
            envs.put("BUILD_ID", formattedBuildValue);
        }
    }

    private Job getJob(AbstractBuild build) {
        if (build instanceof MatrixRun) {
            return ((MatrixRun) build).getParentBuild().getProject();
        } else {
            return build.getProject();
        }
    }
}
