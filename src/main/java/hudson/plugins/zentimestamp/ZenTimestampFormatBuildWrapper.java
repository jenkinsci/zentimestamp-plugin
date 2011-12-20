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


/**
 * This wrapper is used for Hudson configuration set before zentimestamp 2.0
 */
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
        /**
         *  When set to false, the dedicated UI is not display and the build wrapper is not marshaled
         * (The method is called before the save() method)
         *  When set to true, the dedicated UI is display and the build wrapper is marshaled
         */
        public boolean isApplicable(AbstractProject<?, ?> item) {
            //When there is an old config.xml with a build wrapper, the backwardCompatibility is set to true at load time
            //The value is set by the readResolve() method called when there is an old config.xml configured with this object
            return backwardCompatibility;
        }

        @SuppressWarnings("unused")
        public FormValidation doCheckPattern(@QueryParameter String value) {

            if (value == null || value.trim().length() == 0) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_emptyPattern());
            }

            try {
                new SimpleDateFormat(value);
            } catch (NullPointerException npe) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_invalidInput(npe.getMessage()));
            } catch (IllegalArgumentException iae) {
                return FormValidation.error(Messages.ZenTimestampFormatBuildWrapper_invalidInput(iae.getMessage()));
            }

            return FormValidation.ok();
        }
    }

    /*package*/ static transient boolean backwardCompatibility = false;

    /**
     * Called at each object access
     *
     * @return the current wrapper without changes
     */
    @SuppressWarnings("unused")
    private Object readResolve() {
        backwardCompatibility = true;
        return this;
    }

    /*package*/
    static boolean isConfigXMLWithPreviousVersion() {
        return backwardCompatibility;
    }

}
