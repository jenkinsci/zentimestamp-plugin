package hudson.plugins.zentimestamp;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Gregory Boissinot
 */
public class ZenTimestampNodeProperty extends NodeProperty<Node> {

    private String pattern;

    @DataBoundConstructor
    public ZenTimestampNodeProperty(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    @Extension
    public static class ZenTimestampNodePropertyDescriptor extends NodePropertyDescriptor {
        @Override
        public String getDisplayName() {
            return "Zentimestamp";
        }

        @Override
        public String getHelpFile() {
            return "/plugin/zentimestamp/help-pattern.html";
        }
    }


}
