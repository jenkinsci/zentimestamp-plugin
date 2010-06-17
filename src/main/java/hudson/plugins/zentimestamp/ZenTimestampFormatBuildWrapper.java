package hudson.plugins.zentimestamp;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;


public class ZenTimestampFormatBuildWrapper extends BuildWrapper {

    private String pattern;

    @DataBoundConstructor
    public ZenTimestampFormatBuildWrapper(String pattern) {
        this.pattern = pattern;
    }


    @SuppressWarnings("unused")
    public String getPattern() {
        return pattern;
    }


    @Override
    public hudson.tasks.BuildWrapper.Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws java.io.IOException, java.lang.InterruptedException {

        final PrintStream logger = listener.getLogger();
        Calendar buildTimestamp = build.getTimestamp();
        logger.println("Formating the BUILD_ID variable with'" + pattern + "' pattern.");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        final String newBUILDIDStr = sdf.format(buildTimestamp.getTime());

        return new Environment() {

            @Override
            public void buildEnvVars(Map<String, String> env) {
                env.put("BUILD_ID", newBUILDIDStr);
            }
        };
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends BuildWrapperDescriptor {

        public DescriptorImpl() {
            super(ZenTimestampFormatBuildWrapper.class);
        }

        public String getDisplayName() {
            return Messages.ZenTimestampFormatBuildWrapper_displayName();
        }

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            //Set to false in order to no display the feature
            return false;
        }

        @SuppressWarnings("unused")
        public FormValidation doCheckPattern(@QueryParameter String value) {

            if (value == null || value.trim().length() == 0) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_emptyPattern());
            }

            try {
                new SimpleDateFormat(value);
            }
            catch (NullPointerException npe) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_invalidInput(npe.getMessage()));
            }
            catch (IllegalArgumentException iae) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_invalidInput(iae.getMessage()));
            }

            return FormValidation.ok();
        }
    }

}
