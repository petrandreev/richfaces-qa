package org.richfaces.tests.metamer.ftest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * This is utility class which provides capture screenshots. It is singleton use getInstance to get instance of this class.
 *
 * @author Martin Tomasek (mtomasek@redhat.com)
 *
 * @since 4.5.0.Final
 *
 */
public class MetamerScreenShotLoggingUtils {

    protected File mavenProjectBuildDirectory = new File(System.getProperty("maven.project.build.directory", "./target/"));
    protected File infoOutputDir = new File(mavenProjectBuildDirectory, "info");

    private static MetamerScreenShotLoggingUtils instance;

    public static synchronized MetamerScreenShotLoggingUtils getInstance() {
        if (instance == null) {
            instance = new MetamerScreenShotLoggingUtils();
        }
        return instance;
    }

    /**
     * This method make screenshot to target location. It will be in info in target under testClassName directory and each
     * screenshot for test will be in testName directory. Test step determine number of screenshot. User have to increase it
     * manually.
     *
     * @param driver web driver which will provide screenshot.
     * @param testClassName default folder name in which will be particular tests screenshot. NOTE: use fully testClass name to
     *        avoid rewrite screenshots.
     * @param testName concrete name of test. If you used fully test class name, then testName could be name of method in which
     *        you capture screen ex: testSimple, testInit, etc.
     * @param testStep this number identify number of screenshot taken on this test. Please increase it, to avoid rewrite
     *        screenshot.
     */
    public void makeScreenShot(WebDriver driver, String testClassName, String testName, int testStep) {
        if (driver == null) {
            System.out.println("Can't take a screenshot and save HTML, because there is no driver available.");
            return;
        }
        File screenshot = null;
        if (((GrapheneProxyInstance) driver).unwrap() instanceof TakesScreenshot) {
            screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        }
        File imageOutputFile = new File(infoOutputDir, testClassName + "/" + testName + "/screenshot" + testStep + ".png");
        if (!HtmlUnitDriver.class.isInstance(driver)) {
            try {
                FileUtils.copyFile(screenshot, imageOutputFile);
            } catch (IOException e) {
                System.err.println("Can't take a screenshot/save HTML source: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }
    }

}
