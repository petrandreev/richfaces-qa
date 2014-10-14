package org.richfaces.tests.metamer.ftest;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.testng.container.TestListener;
import org.testng.ITestContext;

public class MetamerScreenshotLoggingListener extends TestListener{

    @Override
    public void onStart(ITestContext testContext) {
        try {
            FileUtils.forceMkdir(MetamerScreenshotLoggingUtils.getInstance().infoOutputDir);
            // FIXME it should clean directory only if it is the first test suite
            // FileUtils.cleanDirectory(failuresOutputDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
