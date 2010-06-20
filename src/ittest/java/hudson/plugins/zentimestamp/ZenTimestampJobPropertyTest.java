package hudson.plugins.zentimestamp;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.tasks.Shell;
import org.jvnet.hudson.test.HudsonTestCase;

import java.text.SimpleDateFormat;


public class ZenTimestampJobPropertyTest extends HudsonTestCase {


    public void testChangeBuildID() throws Exception {
         
        final String BUILD_ID = "BUILD_ID";

        String pattern = "yyyyMMddHHmmss";

        FreeStyleProject project = createFreeStyleProject();
        project.getBuildersList().add(new Shell("echo ${" + BUILD_ID + "}"));
        project.addProperty(new ZenTimestampJobProperty(true, pattern));
                
        FreeStyleBuild build = project.scheduleBuild2(0).get();

        //Build status
        assertBuildStatus(Result.SUCCESS, build);

        //Build log
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        StringBuffer expectedLog = new StringBuffer().append("echo ").append(dateFormat.format(build.getTime()));
        assertLogContains(expectedLog.toString(), build);
    }


}