package hudson.plugins.zentimestamp;

import java.text.SimpleDateFormat;

import hudson.Plugin;
import hudson.util.FormValidation;
import org.kohsuke.stapler.QueryParameter;

/**
 * @author Gregory BOISSINOT - Zenika
 */
public class PluginImpl extends Plugin {

	public FormValidation doDateTimePatternCheck(@QueryParameter("value") String pattern) {
		if (pattern==null || pattern.trim().length()==0){
			return FormValidation.error("You must provide a pattern value");
		}
		
		try {
			new SimpleDateFormat(pattern);
		}
		catch (NullPointerException npe){
			return FormValidation.error("Invalid input: " + npe.getMessage());
		}				
		catch (IllegalArgumentException iae){
			return FormValidation.error("Invalid input: " + iae.getMessage());
		}
		
		return FormValidation.ok();
	}

}
