package maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This goal will say a message.
 *
 * @goal howdy-world
 */
@Mojo(name = "archetypeproject", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateMojo extends AbstractMojo {

    @Parameter(property = "articatid", defaultValue = "test")
    private String articatid;

    @Parameter(property = "groupid", defaultValue = "com.aem.blueprint")
    private String groupid;

    @Parameter(property = "appTitle", defaultValue = "AEMBlueprint")
    private String appTitle;

    @Parameter(property = "appID", defaultValue = "AEMBlueprint")
    private String appID;

    @Parameter(property = "singlecountry", defaultValue = "y")
    private String singlecountry;

    @Parameter(property = "languagecountry", defaultValue = "en_us")
    private String languagecountry;


    public void execute() {


        getLog().info("before");
        String command1 = "mvn -T 10C clean install -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true";
        String command11 = "rm -R generate";
        String command2 = "mkdir generate";

        getLog().info("--------------------------mvn deploy--------------------------");
        generate(command1);
        generate(command11);
        getLog().info("--------------------------Create Folder Structure--------------------------");
        generate(command2);
        getLog().info("--------------------------Generate Archetype--------------------------");
        generatearecetype(articatid,groupid,appTitle,appID,singlecountry,languagecountry);
        getLog().info("--------------------------Deploy--------------------------");
        deploy(articatid);

    }

    public void generate(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            getLog().info("after");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the" + command + ":\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the " + command + " (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            int exitcode = process.waitFor();

            if (exitcode != 0) {
                getLog().info("Exception");
            }
            getLog().info("Updated");
        } catch (IOException | InterruptedException ioException) {
            getLog().info("Failed");
            ioException.printStackTrace();
        }
    }

    public void  generatearecetype(String articatid, String groupid, String appTitle, String appID, String singlecountry, String languagecountry) {
        try {
            File dir = new File("generate");
            Process process = Runtime.getRuntime().exec("mvn archetype:generate -DarchetypeGroupId=com.akqa.aem.blueprint -DarchetypeArtifactId=akqa-project-archetype -DarchetypeVersion=26-SNAPSHOT -DaemVersion=6.5.0 -DappTitle="+appTitle+" -DappId="+appID+" -DgroupId="+groupid+" -DfrontendModule=general -DincludeExamples=y -DincludeDispatcherConfig=n -DlanguageCountry=en_us -DsingleCountry=y -DincludeExamples=y -DartifactId="+articatid+" -DinteractiveMode=false", null, dir);
            getLog().info("after");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the Generate" + ":\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the Generate "  + " (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            int exitcode = process.waitFor();

            if (exitcode != 0) {
                getLog().info("Exception");
            }
            getLog().info("Updated Archetype");
        } catch (IOException | InterruptedException ioException) {
            getLog().info("Failed Updating Archetype");
            ioException.printStackTrace();
        }
    }

    public void deploy(String articatid) {
        try {
            File dir = new File("generate/"+articatid+"/");
            Process process = Runtime.getRuntime().exec("mvn -T 20C clean install -PautoInstallPackage -PautoInstallPackagePublish -DskipTests", null, dir);
            getLog().info("after");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the Deploy" + ":\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the Deploy " +  " (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            int exitcode = process.waitFor();

            if (exitcode != 0) {
                getLog().info("Exception");
            }
            getLog().info("Deployed");
        } catch (IOException | InterruptedException ioException) {
            getLog().info("Failed Deployment");
            ioException.printStackTrace();
        }
    }
}
