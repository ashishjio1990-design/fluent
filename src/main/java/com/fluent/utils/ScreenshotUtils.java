package com.fluent.utils;

import com.fluent.driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    private static final String SCREENSHOT_DIR = "screenshots";

    private ScreenshotUtils() { /* utility class */ }

    public static void capture(String testName) {
        if (!DriverFactory.isDriverActive()) {
            log.warn("No active driver — skipping screenshot for: {}", testName);
            return;
        }

        try {
            byte[] imageBytes = ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BYTES);

            String fileName = testName + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png";
            File dest = new File(SCREENSHOT_DIR, fileName);
            FileUtils.writeByteArrayToFile(dest, imageBytes);
            log.info("Screenshot saved: {}", dest.getAbsolutePath());

        } catch (IOException e) {
            log.error("Failed to save screenshot for '{}': {}", testName, e.getMessage());
        } catch (Exception e) {
            log.error("Failed to capture screenshot for '{}': {}", testName, e.getMessage());
        }
    }

}
