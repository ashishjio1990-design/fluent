package com.fluent.utils;

import com.fluent.driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reusable utility actions that are not tied to any specific screen.
 */
public final class CommonFunctions {

    private static final Logger log = LoggerFactory.getLogger(CommonFunctions.class);

    private static final String APP_PACKAGE =
        ConfigReader.forPlatform("android").get("app.package");

    private CommonFunctions() { /* utility class */ }

    /**
     * Resets the app by terminating and re-launching it.
     * Preserves the Appium session — no driver restart required.
     */
    public static void resetApp() {
        AndroidDriver driver = (AndroidDriver) DriverFactory.getDriver();
        log.info("Resetting app: terminating '{}'", APP_PACKAGE);
        driver.terminateApp(APP_PACKAGE);
        log.info("Resetting app: activating '{}'", APP_PACKAGE);
        driver.activateApp(APP_PACKAGE);
        log.info("App reset complete");
    }
}
