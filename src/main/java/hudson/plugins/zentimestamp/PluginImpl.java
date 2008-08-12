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
				
				if (pattern==null || pattern.trim().length()==0){
					error((new StringBuilder()).append("You must provide a pattern value").toString());
				}
				
				try{
					new SimpleDateFormat(pattern);
				}
				catch (NullPointerException npe){
					error((new StringBuilder()).append("Invalid input: ").append(npe.getMessage()).toString());
					return;
				}				
				catch (IllegalArgumentException iae){
					error((new StringBuilder()).append("Invalid input: ").append(iae.getMessage()).toString());
					return;
				}
				
				return;
			
			}
		}).process();
	}

}
