package hudson.plugins.zentimestamp;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.tasks.Shell;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.Issue;
import org.jvnet.hudson.test.JenkinsRule;

import java.text.SimpleDateFormat;


public class ZenTimestampJobPropertyTest {

    @Rule
    public JenkinsRule r = new JenkinsRule();

    @Test
    public void exportBuildTimestampVar() throws Exception {

        final String BUILD_TIMESTAMP_VARIABLE = ZenTimestampAction.BUILD_TIMESTAMP_VARIABLE;

        String pattern = "yyyyMMddHHmmss";

        FreeStyleProject project = r.createFreeStyleProject();
        project.getBuildersList().add(new Shell("echo ${" + BUILD_TIMESTAMP_VARIABLE + "}"));
        project.addProperty(new ZenTimestampJobProperty(true, pattern));

        FreeStyleBuild build = project.scheduleBuild2(0).get();

        //Build status
        r.assertBuildStatus(Result.SUCCESS, build);

        //Build log
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        StringBuffer expectedLog = new StringBuffer().append("echo ").append(dateFormat.format(build.getTime()));
        r.assertLogContains(expectedLog.toString(), build);
    }

    @Test
    @Issue("JENKINS-26626")
    public void changeTimestampVariable() throws Exception {

        String pattern = "yyyyMMddHHmmss";
        FreeStyleProject p = r.createFreeStyleProject();
        p.getBuildersList().add(new Shell("echo $BUILD_TIMESTAMP"));
        p.addProperty(new ZenTimestampJobProperty(true, pattern));
        FreeStyleBuild build = p.scheduleBuild2(0).get();
        r.assertBuildStatus(Result.SUCCESS, build);

        // BUILD_TIMESTAMP now returns the correct build time.
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        r.assertLogContains("echo " + dateFormat.format(build.getTime()), build);
    }

}