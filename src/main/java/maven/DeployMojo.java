package maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This goal will say a message.
 *
 * @goal deployment
 */
@Mojo(name = "deploy", defaultPhase = LifecyclePhase.DEPLOY)
public class DeployMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException {

        getLog().info("before");
        String command4 = "Deploy";

        getLog().info("--------------------------Deploy--------------------------");
        deploy(command4);

    }

    public void deploy(String command){
        try {
            Process process = Runtime.getRuntime().exec("./deploy");
            getLog().info("after");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the "+ command +":\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the "+ command+" (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            int exitcode = process.waitFor();

            if (exitcode != 0){
                getLog().info("Exception");
            }
            getLog().info("Updated");
        } catch (IOException | InterruptedException ioException) {
            getLog().info("Failed");
            ioException.printStackTrace();
        }
    }
}

