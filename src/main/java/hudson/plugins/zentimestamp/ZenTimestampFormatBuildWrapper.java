package hudson.plugins.zentimestamp;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;

import hudson.tasks.BuildWrapperDescriptor;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;


/**
 * ZenTimestampFormatBuildWrapper {@link Builder}.
 *
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link ZenTimestampFormatBuildWrapper} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #name})
 * to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform(AbstractBuild, Launcher, BuildListener)} method
 * will be invoked. 
 *
 * @author Gregory BOISSINOT - Zenika
 */
public class ZenTimestampFormatBuildWrapper extends BuildWrapper {

	private String pattern;
	
	ZenTimestampFormatBuildWrapper(String pattern){
		this.pattern = pattern;
	}
	
	
	public String getPattern() {
		return pattern;
	}


	@Override
	public hudson.tasks.BuildWrapper.Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws java.io.IOException, java.lang.InterruptedException{	

		final PrintStream logger = listener.getLogger();				
		Calendar buildTimestamp = build.getTimestamp();
		logger.println("Formating the BUILD_ID variable with'"+  pattern + "' pattern.");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        final String newBUILDIDStr = sdf.format(buildTimestamp.getTime());      

        return new Environment() {
            
            @Override
            public void buildEnvVars(Map<String, String> env) {
                env.put("BUILD_ID", newBUILDIDStr);
            }
        };
    }


    /**
     * Descriptor for {@link ZenTimestampFormatBuildWrapper}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>views/hudson/plugins/zentimestamp/ZenTimestampFormatBuildWrapper/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension
    public static final class DescriptorImpl extends BuildWrapperDescriptor {

        public DescriptorImpl() {
            super(ZenTimestampFormatBuildWrapper.class);
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Change BUILD_ID format";
        }

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        /**
         * Creates a new instance of {@link ZenTimestampFormatBuildWrapper} from a submitted form.
         */
        @Override
        public ZenTimestampFormatBuildWrapper newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new ZenTimestampFormatBuildWrapper(req.getParameter("zentimestamp.pattern"));
        }
    }	

}
