package hudson.plugins.zentimestamp;

import hudson.EnvVars;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.tasks.Shell;
import hudson.util.LogTaskListener;
import org.jvnet.hudson.test.HudsonTestCase;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;


public class ZenTimestampFormatBuildWrapperTest extends HudsonTestCase {


    public void testChangeBuildID() throws Exception {

        final String BUILD_ID = "BUILD_ID";

        String pattern = "yyyyMMddHHmmss";

        FreeStyleProject project = createFreeStyleProject();
        project.getBuildersList().add(new Shell("echo ${" + BUILD_ID + "}"));
        project.getBuildWrappersList().add(new ZenTimestampFormatBuildWrapper(pattern));

        FreeStyleBuild build = project.scheduleBuild2(0).get();

        //Build status
        assertBuildStatus(Result.SUCCESS, build);

        //Build log
        StringBuffer expectedLog = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        expectedLog.append("echo " + dateFormat.format(build.getTime()));
        assertLogContains(expectedLog.toString(), build);
    }


}