package hudson.plugins.zentimestamp;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.tasks.BuildWrapper;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 * When a build is performed, the {@link #perform(Build, Launcher, BuildListener)} method
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


	public hudson.tasks.BuildWrapper.Environment setUp(hudson.model.AbstractBuild build, hudson.Launcher launcher, hudson.model.BuildListener listener) throws java.io.IOException, java.lang.InterruptedException{	

		final PrintStream logger = listener.getLogger();				
		Calendar buildTimestamp = build.getTimestamp();
		logger.println("Formating the BUILD_ID variable with'"+  pattern + "'");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        final String newBUILDIDStr = sdf.format(buildTimestamp.getTime());      

        return new Environment() {
            
        	@Override
            public void buildEnvVars(Map<String, String> env) {
            	env.put("BUILD_ID", newBUILDIDStr);
            }
            
            public boolean tearDown(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
                return true;
            }
        };
    }
	
	
	
	public Descriptor<BuildWrapper> getDescriptor() {
		return DESCRIPTOR;
	}

	/**
     * Descriptor should be singleton.
     */
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    /**
     * Descriptor for {@link ZenTimestampFormatBuildWrapper}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>views/hudson/plugins/zentimestamp/ZenTimestampFormatBuildWrapper/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    public static final class DescriptorImpl extends Descriptor<BuildWrapper> {

        DescriptorImpl() {
            super(ZenTimestampFormatBuildWrapper.class);
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Change BUILD_ID format";
        }

        public boolean configure(HttpServletRequest req) throws FormException {
            return super.configure(req);
        }

        /**
         * Creates a new instance of {@link ZenTimestampFormatBuildWrapper} from a submitted form.
         */
        public ZenTimestampFormatBuildWrapper newInstance(StaplerRequest req) throws FormException {
            return new ZenTimestampFormatBuildWrapper(req.getParameter("zentimestamp.pattern"));
        }
    }	

}
