package hudson.plugins.zentimestamp;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.Plugin;
import hudson.tasks.BuildWrappers;
import hudson.util.FormFieldValidator;

/**
 * @author Gregory BOISSINOT - Zenika
 */
public class PluginImpl extends Plugin {
	public void start() throws Exception {
		BuildWrappers.WRAPPERS.add(ZenTimestampFormatBuildWrapper.DESCRIPTOR);
	}

	public void doDateTimePatternCheck(final StaplerRequest req, StaplerResponse rsp)
			throws IOException, ServletException {
		(new FormFieldValidator(req, rsp, true) {

			public void check() throws IOException, ServletException {
				
				String pattern = req.getParameter("value");
				try{
					new SimpleDateFormat(pattern);
				}
				catch (NullPointerException npe){
					error((new StringBuilder()).append("Wrong pattern. ").append(npe.getMessage()).toString());
					return;
				}				
				catch (IllegalArgumentException iae){
					error((new StringBuilder()).append("Wrong pattern. ").append(iae.getMessage()).toString());
					return;
				}
				
				return;
			
			}
		}).process();
	}

}
