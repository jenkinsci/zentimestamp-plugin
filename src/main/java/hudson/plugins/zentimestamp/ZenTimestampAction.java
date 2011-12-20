package hudson.plugins.zentimestamp;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.EnvironmentContributingAction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ZenTimestampAction implements EnvironmentContributingAction {

    private String pattern;

    public ZenTimestampAction(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
        Calendar buildTimestamp = build.getTimestamp();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedBUILDID = sdf.format(buildTimestamp.getTime());
        env.put("BUILD_ID", formattedBUILDID);
    }

    public String getDisplayName() {
        return null;
    }

    public String getIconFileName() {
        return null;
    }

    public String getUrlName() {
        return null;
    }

}
