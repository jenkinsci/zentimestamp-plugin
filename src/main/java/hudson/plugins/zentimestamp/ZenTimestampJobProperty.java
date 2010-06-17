package hudson.plugins.zentimestamp;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.text.SimpleDateFormat;


public class ZenTimestampJobProperty extends JobProperty<Job<?, ?>> {

    private boolean changeBUILDID;

    private String pattern;

    @DataBoundConstructor
    public ZenTimestampJobProperty(boolean changeBUILDID, String pattern) {
        this.changeBUILDID = changeBUILDID;
        this.pattern = pattern;
    }

    @SuppressWarnings("unused")
    public String getPattern() {
        return pattern;
    }

    @SuppressWarnings("unused")
    public boolean isChangeBUILDID() {
        return changeBUILDID;
    }

    @Override
    public boolean prebuild(hudson.model.AbstractBuild<?, ?> abstractBuild, hudson.model.BuildListener buildListener) {
        ZenTimestampLogger.log(buildListener, "Formating the BUILD_ID variable with '" + pattern + "' pattern.");
        abstractBuild.addAction(new ZenTimestampAction(pattern));
        return true;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        return true;
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends JobPropertyDescriptor {

        public DescriptorImpl() {
            super(ZenTimestampJobProperty.class);
            load();
        }

        public boolean isApplicable(Class<? extends Job> jobType) {
            return AbstractProject.class.isAssignableFrom(jobType);
        }

        public String getDisplayName() {
            return Messages.ZenTimestampFormatBuildWrapper_displayName();
        }

        public ZenTimestampJobProperty newInstance(org.kohsuke.stapler.StaplerRequest req, net.sf.json.JSONObject jsonObject) throws Descriptor.FormException {
            Object changeBUILDID = jsonObject.get("changeBUILDID");
            if (changeBUILDID != null) {
                String pattern = ((JSONObject) changeBUILDID).getString("pattern");
                if ((pattern != null) && (pattern.trim().length() != 0))
                    return new ZenTimestampJobProperty(true, pattern);
            }
            return null;
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
