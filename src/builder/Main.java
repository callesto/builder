/*
//Config file parameters
jwarnVersion=2.1.2.2.ER3.RC1
coreVersion=5.2.1.ER3.RC1
etcVersion=2.0.9.3.RC1
smVersion=1.0.6
sourcesLocation=C:\\JWARN-Web\\jwarn-2.1.2.2-sources
//Modules is a comma delimited list of a colon separated list of packages and their type.
modules=dependencies\\com.omi.atp45e.formatsupport.adatp3.baseline14.xml-1.0.9-src\com.omi.atp45e.formatsupport.adatp3.baseline14.xml-1.0.9:mvn,dependencies\\com.omi.atp45e.implementation-2.1.3-src\\com.omi.atp45e.implementation-2.1.3:mvn,dependencies\\com.omi.erg.core-3.0.3-src\\com.omi.erg.core-3.0.3:mvn,dependencies\\com.omi.ffe.core-3.0.2-src\\com.omi.ffe.core-3.0.2:mvn,dependencies\\com.omi.moppstatus.core-2.1.1-src\\com.omi.moppstatus.core-2.1.1:mvn,dependencies\\com.omi.util.coordinates-4.0.2-src\\com.omi.util.coordinates-4.0.2:mvn,dependencies\\com.omi.util.core-5.1.1-src\\com.omi.util.core-5.1.1:mvn,dependencies\\com.omi.util.geometry-4.0.2-src\\com.omi.util.geometry-4.0.2:mvn,dependencies\\com.omi.util.geometry-api-4.0.2-src\\com.omi.util.geometry-api-4.0.2:mvn,dependencies\\com.omi.util.geometry-java2d-4.0.2-src\\com.omi.util.geometry-java2d-4.0.2:mvn,dependencies\\com.omi.util.kmlexport-4.0.2-src\\com.omi.util.kmlexport-4.0.2:mvn,dependencies\\com.omi.web.jsonparser.echo-1.0.11-src\\com.omi.web.jsonparser.echo-1.0.11:mvn,dependencies\\com.omi.web.tools.erg-1.0.15-src\\com.omi.web.tools.erg-1.0.15:mvn,dependencies\\com.omi.web.tools.quickattack.echo-1.0.15-src\\com.omi.web.tools.quickattack.echo-1.0.15:mvn,dependencies\\com.omi.web.unifiedwebgui-3.0.13-src\\com.omi.web.unifiedwebgui-3.0.13:mvn,dependencies\\omi.md.jwarn.atp45c.core-1.0.29-src\\omi.md.jwarn.atp45c.core-1.0.29:mvn,dependencies\\omi.md.jwarn.atp45c.kml-1.0.1-src\\omi.md.jwarn.atp45c.kml-1.0.1:mvn,dependencies\\omi.md.jwarn.atp45c.util-1.0.3-src\\omi.md.jwarn.atp45c.util-1.0.3:mvn,cas:mvn,core:mvn,etc:mvn,sensor:gradle,jwarn\\installer:gradlemavenParameters=clean install -npu -nsu -c
repoLocation=C:\\JWARN-Web\\repository
mavenParameters=clean install -nsu -npu -c
 */
package builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jeremy Taylor
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Usage: Builder.jar [full path to properties file]");
            System.exit(0);
        }
        long startTime = System.nanoTime();
        InputStream input = null;
        try {
            String propFileName = args[0];
            Properties prop = new Properties();
            input = new FileInputStream(propFileName);
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            System.out.println("Jwarn Version: " + prop.getProperty("jwarnVersion"));
            System.out.println("jwaccs Version: " + prop.getProperty("jwaccsVersion"));
            System.out.println("Core Version: " + prop.getProperty("coreVersion"));
            System.out.println("ETC Version: " + prop.getProperty("etcVersion"));
            System.out.println("SM Version: " + prop.getProperty("smVersion"));
            System.out.println("Sources Location: " + prop.getProperty("sourcesLocation"));
            System.out.println("Modules: " + prop.getProperty("modules"));
            System.out.println("Maven Parameters: " + prop.getProperty("mavenParameters"));
            System.out.println("Repository Location: " + prop.getProperty("repoLocation"));

            String location = prop.getProperty("sourcesLocation");
            String jwarnVersion = prop.getProperty("jwarnVersion");
            String jwaccsVersion = prop.getProperty("jwaccsVersion");
            String coreVersion = prop.getProperty("coreVersion");
            String etcVersion = prop.getProperty("etcVersion");
            String smVersion = prop.getProperty("smVersion");
            String modules = prop.getProperty("modules");
            String mavenParameters = prop.getProperty("mavenParameters");
            String repoLocation = prop.getProperty("repoLocation");

            if (location.length() == 0
                    || jwarnVersion.length() == 0
                    || coreVersion.length() == 0
                    || etcVersion.length() == 0
                    || smVersion.length() == 0
                    || modules.length() == 0
                    || mavenParameters.length() == 0
                    || repoLocation.length() == 0) {
                System.out.println("Missing parameters. Please check your properties file and try again.");

            } else {
                //File[] files = new File(location + File.separator + "dependencies").listFiles();
                //buildOMI(files, mavenParameters);
                buildJwarn(location, jwarnVersion, coreVersion, etcVersion, smVersion, modules, mavenParameters, repoLocation, jwaccsVersion);
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
        long minutes = (duration / 1000)  / 60;
        int seconds = (int) ((duration / 1000) % 60);
        System.out.println("Total Build Time: " + minutes + " minutes, " + seconds + " seconds");
    }

    private static void buildJwarn(String location, String jwarnVersion, String coreVersion, String etcVersion, String smVersion, String modules, String mavenParameters, String repoLocation, String jwaccsVersion) throws InterruptedException {
        try {

            String[] tokens = modules.split(",");
            int tokenCount = tokens.length;
            for (int j = 0; j < tokenCount; j++) {
                System.out.println("Token: " + tokens[j]);
                String element = tokens[j];
                String[] elements = element.split(":");
                int elementCount = elements.length;
                for (int k = 0; k < elementCount; k++) {
                    System.out.println("" + elements[k]);
                }
                if ("mvn".equals(elements[1])) {
                    System.out.println("\nCommand: mvn " + mavenParameters + " -f " + location + File.separator + elements[0] + File.separator + "pom.xml");
                    executeCommand("mvn " + mavenParameters + " -f " + location + File.separator + elements[0] + File.separator + "pom.xml");

                } else if ("sensor".equals(elements[0])) {
                    //Build Sensor
                    System.out.println("\nCommand: gradle clean install -PjwarnVersion=" + coreVersion
                            + " -Pversion=" + smVersion
                            + " -PenterpriseMavenRepo=" + repoLocation
                            + " -PorlandoUser=jwarn -PorlandoPassword=jwarn -b " + location + File.separator + elements[0] + File.separator + "build.gradle");
                    executeCommand("gradle clean install -PjwarnVersion=" + coreVersion
                            + " -Pversion=" + smVersion
                            + " -PenterpriseMavenRepo=" + repoLocation
                            + " -PorlandoUser=jwarn -PorlandoPassword=jwarn -b " + location + File.separator + elements[0] + File.separator + "build.gradle");

                } else if (elements[0].contains("installer")
                        || "jwarn\\installer".equals(elements[0])
                        || "jwarn/installer".equals(elements[0])) {
                    //Build installer
                    System.out.println("\nCommand: gradle clean dist"
                            + " -PenterpriseMavenRepo=" + repoLocation
                            + " -PjwarnVersion=" + jwarnVersion
                            + " -PcoreVersion=" + coreVersion
                            + " -PjwarnEtcVersion=" + etcVersion
                            + " -PsensorManagementVersion=" + smVersion
                            + " -PmavenUser=jwarn -PmavenPassword=jwarn -b " + location + File.separator + elements[0] + File.separator + "build.gradle");
                    executeCommand("gradle clean dist"
                            + " -PenterpriseMavenRepo=" + repoLocation
                            + " -PjwarnVersion=" + jwarnVersion
                            + " -PcoreVersion=" + coreVersion
                            + " -PjwarnEtcVersion=" + etcVersion
                            + " -PsensorManagementVersion=" + smVersion
                            + " -PmavenUser=jwarn -PmavenPassword=jwarn -b " + location + File.separator + elements[0] + File.separator + "build.gradle");

                } else {
                    System.out.println("\nCommand: gradle clean install -b " + location + File.separator + elements[0] + File.separator + "build.gradle");
                    executeCommand("gradle clean install -b " + location + File.separator + elements[0] + File.separator + "build.gradle");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void executeCommand(String command) throws InterruptedException, IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        final InputStream stdOut = process.getInputStream();

        new Thread(new Runnable() {
            public void run() {
                try {
                    byte[] buffer = new byte[8192];
                    int len = -1;
                    while ((len = stdOut.read(buffer)) > 0) {
                        System.out.write(buffer, 0, len);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        process.waitFor();
    }
}
